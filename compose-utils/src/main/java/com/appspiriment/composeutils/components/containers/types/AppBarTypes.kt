package com.appspiriment.composeutils.components.containers.types

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.toUiImage

/**
 * Describes the leading navigation icon behaviour of an [com.appspiriment.composeutils.components.containers.AppsTopBar].
 *
 * [EMPTY] carries no icon — the navigation slot is left blank and no resource is loaded.
 * All other variants carry a non-null [icon] that is rendered as an [androidx.compose.material3.IconButton].
 */
sealed class NavigationMode(
    val icon: UiImage?,
    val contentDescription: String? = null,
) {
    /** No navigation icon. The navigation slot is completely empty. */
    data object EMPTY : NavigationMode(icon = null, contentDescription = null)

    data object BACK : NavigationMode(
        icon = Icons.AutoMirrored.Filled.ArrowBack.toUiImage(),
        contentDescription = "Back",
    )

    data object CLOSE : NavigationMode(
        icon = Icons.Default.Close.toUiImage(),
        contentDescription = "Close",
    )

    data object DRAWER : NavigationMode(
        icon = Icons.Default.Menu.toUiImage(),
        contentDescription = "Menu",
    )

    data object HOME : NavigationMode(
        icon = Icons.Default.Home.toUiImage(),
        contentDescription = "Home",
    )
}

/**
 * Describes the content rendered in the title area of an
 * [com.appspiriment.composeutils.components.containers.AppsTopBar].
 */
sealed class AppBarTitle(open val modifier: Modifier) {

    /** No title — the title slot is left blank. */
    data object None : AppBarTitle(Modifier)

    /** A brand logo image fills the title area. */
    data class BrandLogo(
        val image: UiImage,
        override val modifier: Modifier = Modifier,
    ) : AppBarTitle(modifier)

    /** A plain text title. */
    data class ScreenTitle(
        val title: UiText,
        override val modifier: Modifier = Modifier,
    ) : AppBarTitle(modifier)

    /** An icon followed by a primary title and an optional subtitle. */
    data class ScreenTitleWithIcon(
        val icon: UiImage,
        val iconHeight: Dp = 40.dp,
        val title: UiText,
        val subTitle: UiText? = null,
        val titleStyle: TextStyle? = null,
        val subTitleStyle: TextStyle? = null,
        val iconPadding: Dp = 12.dp,
        override val modifier: Modifier = Modifier,
    ) : AppBarTitle(modifier)
}
