package com.appspiriment.composeutils.utils.permission

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.messages.DialogButtonStyle
import com.appspiriment.composeutils.components.messages.MessageDialog
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.toUiText
import com.appspiriment.composeutils.wrappers.uiTextResource

// ── Lower-level container ─────────────────────────────────────────────────────

/**
 * Low-level building block that manages permission states and surfaces the outcome via
 * composable lambdas. Use when you need full control over the UI for each state.
 *
 * For typical use cases prefer [PermissionHandler] (full-screen default UI) or
 * [rememberPermissionRequest] (button-triggered dialog flow).
 */
@Composable
fun PermissionsManagerContainer(
    permissions: List<AppPermission>,
    onAllPermissionsGranted: @Composable (state: AppPermissionsOverallState) -> Unit,
    onPermissionsNotGranted: @Composable (
        allDenied: List<AppPermission>,
        permanentlyDenied: List<AppPermission>,
        deniedForRationale: List<AppPermission>,
        requestPermissions: () -> Unit,
        openSettings: () -> Unit,
        state: AppPermissionsOverallState,
    ) -> Unit,
    onChecking: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val state = rememberAppPermissionsState(permissionsList = permissions)

    when {
        state.isChecking -> onChecking()
        state.allRequiredPermissionsGranted -> onAllPermissionsGranted(state)
        else -> onPermissionsNotGranted(
            state.allDeniedPermissions,
            state.permanentlyDeniedPermissions,
            state.permissionsNeedingRationale,
            state::requestPermissions,
            { state.openAppSettings(context) },
            state,
        )
    }
}

// ── High-level full-screen handler ───────────────────────────────────────────

/**
 * Displays `content` only when all [permissions] are granted. While permissions are
 * missing it shows [DefaultPermissionsRequestUI], the opinionated full-screen prompt.
 */
@Composable
fun PermissionHandler(
    permissions: List<AppPermission>,
    content: @Composable () -> Unit,
) {
    PermissionsManagerContainer(
        permissions = permissions,
        onAllPermissionsGranted = { content() },
        onPermissionsNotGranted = { _, permanentlyDenied, needingRationale, requestPermissions, openSettings, _ ->
            DefaultPermissionsRequestUI(
                permanentlyDenied = permanentlyDenied,
                needingRationale = needingRationale,
                onRequestPermissions = requestPermissions,
                onOpenSettings = openSettings,
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

// ── Button-triggered dialog flow ──────────────────────────────────────────────

/**
 * Returns a `() -> Unit` launcher that, when called, triggers a dialog-based permission
 * request flow. Attach it to a button's `onClick`.
 *
 * Flow:
 * 1. All granted → calls [onActionGranted] immediately.
 * 2. Permanently denied → shows a "Go to Settings" dialog.
 * 3. [askFirst] = true → shows a rationale dialog before the system prompt.
 * 4. Otherwise → requests the permissions and calls [onActionGranted] once all are granted.
 */
@Composable
fun rememberPermissionRequest(
    permissions: List<AppPermission>,
    onActionGranted: () -> Unit,
    askFirst: Boolean = false,
    rationaleMessage: (List<AppPermission>) -> UiText = { perms ->
        ("To proceed, please grant the following permissions:\n\n" +
                perms.joinToString("\n") { "● ${it.name}" }).toUiText()
    },
    rationaleTitle: UiText = uiTextResource(R.string.permission_required),
    rationaleConfirmText: UiText = uiTextResource(R.string.grant_permission),
    rationaleDismissText: UiText = uiTextResource(R.string.cancel),
    settingsMessage: (List<AppPermission>) -> UiText = { perms ->
        ("You have permanently denied permissions required for this feature. " +
                "Please enable them in app settings:\n\n" +
                perms.joinToString("\n") { "● ${it.name}" }).toUiText()
    },
    settingsTitle: UiText = uiTextResource(R.string.permission_required),
    settingsConfirmText: UiText = uiTextResource(R.string.open_settings),
    settingsDismissText: UiText = uiTextResource(R.string.cancel),
    dialogButtonStyle: DialogButtonStyle = DialogButtonStyle.transparent(),
): () -> Unit {
    val context = LocalContext.current
    val permissionState = rememberAppPermissionsState(permissions)

    var currentRationaleMessage by remember { mutableStateOf<UiText?>(null) }
    var currentSettingsMessage by remember { mutableStateOf<UiText?>(null) }
    var showRationaleDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var actionPending by remember { mutableStateOf(false) }

    LaunchedEffect(permissionState.allRequiredPermissionsGranted, actionPending) {
        if (permissionState.allRequiredPermissionsGranted && actionPending) {
            onActionGranted()
            actionPending = false
        }
    }

    if (showRationaleDialog) {
        MessageDialog(
            title = rationaleTitle,
            message = currentRationaleMessage,
            positiveText = rationaleConfirmText,
            negativeText = rationaleDismissText,
            buttonStyle = dialogButtonStyle,
            onDismissRequest = { showRationaleDialog = false },
            listener = { confirmed ->
                showRationaleDialog = false
                if (confirmed) { actionPending = true; permissionState.requestPermissions() }
            },
        )
    }

    if (showSettingsDialog) {
        MessageDialog(
            title = settingsTitle,
            message = currentSettingsMessage,
            positiveText = settingsConfirmText,
            negativeText = settingsDismissText,
            buttonStyle = dialogButtonStyle,
            onDismissRequest = { showSettingsDialog = false },
            listener = { confirmed ->
                showSettingsDialog = false
                if (confirmed) { actionPending = true; permissionState.openAppSettings(context) }
            },
        )
    }

    return {
        when {
            permissionState.allRequiredPermissionsGranted -> onActionGranted()
            permissionState.hasPermanentlyDeniedPermissions -> {
                currentSettingsMessage = settingsMessage(permissionState.permanentlyDeniedPermissions)
                showSettingsDialog = true
            }
            askFirst -> {
                currentRationaleMessage = rationaleMessage(
                    permissionState.permissionsNeedingRationale.ifEmpty { permissions }
                )
                showRationaleDialog = true
            }
            else -> {
                actionPending = true
                permissionState.requestPermissions()
            }
        }
    }
}
