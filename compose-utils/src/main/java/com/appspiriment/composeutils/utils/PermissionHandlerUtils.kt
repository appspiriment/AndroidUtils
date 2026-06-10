@file:JvmName("PermissionHandlerUtils")
// Forwarding shim — all permission types and composables have moved to utils/permission/.
// This file re-exports them at the old package path so existing imports continue to compile.
package com.appspiriment.composeutils.utils

@Suppress("unused")
@Deprecated(
    message = "Import from com.appspiriment.composeutils.utils.permission instead",
    replaceWith = ReplaceWith(
        "com.appspiriment.composeutils.utils.permission.*",
        "com.appspiriment.composeutils.utils.permission"
    )
)
typealias PermissionStatus = com.appspiriment.composeutils.utils.permission.PermissionStatus

@Deprecated(
    message = "Import from com.appspiriment.composeutils.utils.permission instead",
    replaceWith = ReplaceWith(
        "AppPermission",
        "com.appspiriment.composeutils.utils.permission.AppPermission"
    )
)
typealias AppPermission = com.appspiriment.composeutils.utils.permission.AppPermission

@Deprecated(
    message = "Import from com.appspiriment.composeutils.utils.permission instead",
    replaceWith = ReplaceWith(
        "AppPermissionsOverallState",
        "com.appspiriment.composeutils.utils.permission.AppPermissionsOverallState"
    )
)
typealias AppPermissionsOverallState = com.appspiriment.composeutils.utils.permission.AppPermissionsOverallState
