package com.appspiriment.composeutils.utils.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.appspiriment.composeutils.R
import com.appspiriment.composeutils.components.core.VerticalSpacer
import com.appspiriment.composeutils.components.core.buttons.AppsButton
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.components.messages.MessageDialog
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.CompositionBaseProvider
import com.appspiriment.composeutils.theme.semiBold
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.uiTextResource

// ── Dialog-gated permission handler ──────────────────────────────────────────

/**
 * Displays `content` when all [permissions] are granted. Otherwise shows a [MessageDialog]
 * asking the user to grant or navigate to Settings. Dismissing the dialog calls [onDismiss].
 *
 * Use [PermissionHandler] (full-screen default UI) or [rememberPermissionRequest] (launcher hook)
 * for alternative entry points.
 */
@Composable
fun PermissionRequestDialog(
    permissions: List<AppPermission>,
    onDismiss: () -> Unit,
    rationaleMessage: (List<AppPermission>) -> UiText = { perms ->
        ("To access this feature, please grant the following permissions:\n\n" +
                perms.joinToString("\n") { "● ${it.name}" }).let { UiText.DynamicString(it) }
    },
    settingsMessage: (List<AppPermission>) -> UiText = { perms ->
        ("You have permanently denied permissions required for this feature. " +
                "Please enable them in app settings:\n\n" +
                perms.joinToString("\n") { "● ${it.name}" }).let { UiText.DynamicString(it) }
    },
    content: @Composable () -> Unit,
) {
    var hasBeenDismissed by remember { mutableStateOf(false) }

    PermissionsManagerContainer(
        permissions = permissions,
        onAllPermissionsGranted = { content() },
        onPermissionsNotGranted = { _, permanentlyDenied, rationale, requestPermissions, openSettings, _ ->
            if (!hasBeenDismissed) {
                val isPermanent = permanentlyDenied.isNotEmpty()
                MessageDialog(
                    title = uiTextResource(R.string.permission_required),
                    message = if (isPermanent) settingsMessage(permanentlyDenied)
                    else rationaleMessage(rationale.ifEmpty { permissions }),
                    positiveText = if (isPermanent) uiTextResource(R.string.open_settings)
                    else uiTextResource(R.string.grant_permission),
                    negativeText = uiTextResource(R.string.cancel),
                    listener = { confirmed ->
                        if (confirmed) { if (isPermanent) openSettings() else requestPermissions() }
                        else { hasBeenDismissed = true; onDismiss() }
                    },
                    onDismissRequest = { hasBeenDismissed = true; onDismiss() },
                )
            }
        },
    )
}

// ── Default full-screen UI ────────────────────────────────────────────────────

/**
 * Opinionated full-screen UI that guides the user to grant missing permissions.
 * Shows a "Go to Settings" button for permanently denied permissions and a
 * "Grant Permission" button for permissions that can still be requested.
 *
 * Customise text and per-permission rendering via the parameters; or replace entirely
 * with a custom composable passed to [PermissionsManagerContainer].
 */
@Composable
fun DefaultPermissionsRequestUI(
    permanentlyDenied: List<AppPermission>,
    needingRationale: List<AppPermission>,
    onRequestPermissions: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
    title: UiText = uiTextResource(R.string.permissions_required_title),
    permanentlyDeniedMessage: UiText = uiTextResource(R.string.permissions_denied_permanantly_message),
    openSettingsButtonText: UiText = uiTextResource(R.string.open_settings),
    rationaleMessage: UiText = uiTextResource(R.string.permissions_needed_message),
    grantPermissionButtonText: UiText = uiTextResource(R.string.grant_permission),
    genericMessage: UiText = uiTextResource(R.string.permissions_generic_title),
    permissionItemContent: @Composable (AppPermission) -> Unit = { perm ->
        AppspirimentText(text = "● ${perm.name}", color = Appspiriment.colors.accentedBlueText)
    },
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Appspiriment.sizes.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AppspirimentText(text = title, style = Appspiriment.typography.textMediumLarge.semiBold)
            VerticalSpacer(height = Appspiriment.sizes.paddingSmall)

            val allPermissions = (permanentlyDenied + needingRationale).distinctBy { it.permission }
            allPermissions.forEach { permissionItemContent(it) }

            VerticalSpacer(height = Appspiriment.sizes.paddingSmall)

            when {
                permanentlyDenied.isNotEmpty() -> {
                    AppspirimentText(text = permanentlyDeniedMessage, color = Appspiriment.colors.subText, textAlign = TextAlign.Center)
                    VerticalSpacer(height = Appspiriment.sizes.paddingLarge)
                    AppsButton(openSettingsButtonText) { onOpenSettings() }
                }
                allPermissions.isNotEmpty() -> {
                    AppspirimentText(text = rationaleMessage, color = Appspiriment.colors.subText, textAlign = TextAlign.Center)
                    VerticalSpacer()
                    AppsButton(grantPermissionButtonText) { onRequestPermissions() }
                }
                else -> {
                    AppspirimentText(text = genericMessage)
                    VerticalSpacer(height = Appspiriment.sizes.paddingLarge)
                    AppsButton(grantPermissionButtonText) { onRequestPermissions() }
                }
            }
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Permissions UI — Rationale")
@Composable
private fun Preview_Rationale() {
    CompositionBaseProvider {
        Surface {
            DefaultPermissionsRequestUI(
                permanentlyDenied = emptyList(),
                needingRationale = listOf(
                    AppPermission("android.permission.CAMERA", "Camera"),
                    AppPermission("android.permission.RECORD_AUDIO", "Microphone"),
                ),
                onRequestPermissions = {},
                onOpenSettings = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(showBackground = true, name = "Permissions UI — Permanently Denied")
@Composable
private fun Preview_PermanentlyDenied() {
    CompositionBaseProvider {
        Surface {
            DefaultPermissionsRequestUI(
                permanentlyDenied = listOf(
                    AppPermission("android.permission.CAMERA", "Camera"),
                    AppPermission("android.permission.ACCESS_FINE_LOCATION", "Location"),
                ),
                needingRationale = emptyList(),
                onRequestPermissions = {},
                onOpenSettings = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
