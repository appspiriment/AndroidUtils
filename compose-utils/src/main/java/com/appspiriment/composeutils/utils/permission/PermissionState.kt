package com.appspiriment.composeutils.utils.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

// ── Public data types ─────────────────────────────────────────────────────────

/** The current status of a runtime permission. */
enum class PermissionStatus {
    /** The permission has been granted. */
    GRANTED,
    /** Denied but can be requested again — show a rationale. */
    DENIED,
    /** Denied and the user selected "Don't ask again" — direct to Settings. */
    DENIED_PERMANENTLY,
    /** Status is currently being determined. */
    CHECKING,
}

/**
 * Defines a permission required by the application.
 *
 * @param permission The manifest permission string, e.g. `Manifest.permission.CAMERA`.
 * @param name       A human-readable name shown in rationale messages, e.g. "Camera".
 * @param isRequired If `true`, this permission is critical; [AppPermissionsOverallState.allRequiredPermissionsGranted]
 *                   will be `false` until it is granted.
 */
data class AppPermission(
    val permission: String,
    val name: String,
    val isRequired: Boolean = true,
)

// ── Internal single-permission state ─────────────────────────────────────────

internal class SinglePermissionState(val appPermission: AppPermission) {
    var status by mutableStateOf(PermissionStatus.CHECKING)

    fun updateStatus(activity: Activity) {
        val granted = ContextCompat.checkSelfPermission(activity, appPermission.permission) == PackageManager.PERMISSION_GRANTED
        status = when {
            granted -> PermissionStatus.GRANTED
            ActivityCompat.shouldShowRequestPermissionRationale(activity, appPermission.permission) -> PermissionStatus.DENIED
            // First launch or permanently denied — default DENIED so the first request proceeds.
            // DENIED_PERMANENTLY is set correctly by handlePermissionsResult after a failed attempt.
            else -> PermissionStatus.DENIED
        }
    }
}

// ── Public state holder ───────────────────────────────────────────────────────

/**
 * Manages the runtime status of a set of permissions.
 * Returned by [rememberAppPermissionsState]; do not construct directly.
 */
class AppPermissionsOverallState internal constructor(
    val permissionsDefinition: List<AppPermission>,
) {
    internal var permissionStates by mutableStateOf(permissionsDefinition.map { SinglePermissionState(it) })
        private set

    internal lateinit var launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>

    /** `true` when every `isRequired` permission is granted. */
    val allRequiredPermissionsGranted: Boolean
        get() = permissionStates.filter { it.appPermission.isRequired }.all { it.status == PermissionStatus.GRANTED }

    /** `true` while any permission status is still [PermissionStatus.CHECKING]. */
    val isChecking: Boolean
        get() = permissionStates.any { it.status == PermissionStatus.CHECKING }

    /** `true` if any permission is permanently denied. */
    val hasPermanentlyDeniedPermissions: Boolean
        get() = permanentlyDeniedPermissions.isNotEmpty()

    val permissionsNeedingRationale: List<AppPermission>
        get() = permissionStates.filter { it.status == PermissionStatus.DENIED }.map { it.appPermission }

    val permanentlyDeniedPermissions: List<AppPermission>
        get() = permissionStates.filter { it.status == PermissionStatus.DENIED_PERMANENTLY }.map { it.appPermission }

    val allDeniedPermissions: List<AppPermission>
        get() = permissionStates.filter { it.status != PermissionStatus.GRANTED }.map { it.appPermission }

    internal fun registerLauncher(l: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) {
        launcher = l
    }

    internal fun updateAllStatuses(activity: Activity) {
        permissionStates.forEach { it.updateStatus(activity) }
        permissionStates = permissionStates.toList()
    }

    /** Launches the system dialog for all currently [PermissionStatus.DENIED] permissions. */
    fun requestPermissions() {
        val toRequest = permissionStates
            .filter { it.status == PermissionStatus.DENIED }
            .map { it.appPermission.permission }
            .toTypedArray()
        if (toRequest.isNotEmpty()) launcher.launch(toRequest)
    }

    internal fun handlePermissionsResult(activity: Activity, grantResults: Map<String, Boolean>) {
        grantResults.forEach { (permStr, isGranted) ->
            permissionStates.find { it.appPermission.permission == permStr }?.let { state ->
                state.status = if (isGranted) {
                    PermissionStatus.GRANTED
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permStr)) {
                    PermissionStatus.DENIED
                } else {
                    PermissionStatus.DENIED_PERMANENTLY
                }
            }
        }
        updateAllStatuses(activity)
    }

    /** Opens the app's Settings screen so the user can manually grant permissions. */
    fun openAppSettings(context: Context) {
        context.startActivity(
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.packageName, null))
                .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        )
    }
}

// ── Base hook ─────────────────────────────────────────────────────────────────

/**
 * Low-level hook that tracks the status of [permissionsList] and re-checks on every
 * [Lifecycle.Event.ON_RESUME] until all required permissions are granted.
 *
 * For most use cases prefer [rememberPermissionRequest] (dialog-based) or
 * [PermissionHandler] (full-screen) from [PermissionHandler.kt].
 */
@Composable
fun rememberAppPermissionsState(permissionsList: List<AppPermission>): AppPermissionsOverallState {
    val context = LocalContext.current
    val activity = context as? Activity
        ?: error("rememberAppPermissionsState requires an Activity context")

    val state = remember(permissionsList) { AppPermissionsOverallState(permissionsList) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        state.handlePermissionsResult(activity, results)
    }

    LaunchedEffect(permissionsList, activity) {
        state.registerLauncher(launcher)
        state.updateAllStatuses(activity)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, state, activity) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && !state.allRequiredPermissionsGranted) {
                state.updateAllStatuses(activity)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    return state
}
