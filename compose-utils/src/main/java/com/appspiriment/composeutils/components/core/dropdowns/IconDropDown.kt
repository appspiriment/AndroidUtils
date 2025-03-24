package com.appspiriment.composeutils.components.core.dropdowns

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.appspiriment.composeutils.components.core.image.AppsIcon
import com.appspiriment.composeutils.wrappers.UiImage
import com.appspiriment.composeutils.wrappers.UiText


@Composable
fun IconDropDown(
    items: List<UiText>,
    icon: UiImage,
    onItemSelected: (index: Int) -> Unit
) {
    DropDownSpinner(
        items = items,
        onSelectedIndexChange = { onItemSelected(it) },
    ) { _, _, onClick ->

        AppsIcon(
            icon = icon,
                modifier = Modifier.clickable { onClick() }
            )
    }
}