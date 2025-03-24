package com.appspiriment.composeutils.components.containers.types

import com.appspiriment.composeutils.wrappers.UiImage

data class AppsBottomBarButton <T : Any>(val name: String, val route: T, val icon: UiImage)
