package com.appspiriment.composeutils.components.core.dropdowns

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.wrappers.UiText


@Composable
fun TextDropDown(
    items: List<UiText>,
    onItemSelected: (index: Int) -> Unit
) {
    DropDownSpinner(
        items = items,
        onSelectedIndexChange = { onItemSelected(it) },
    ) { index, item, onClick ->

        AppspirimentText(
            text = item,
            modifier = Modifier.clickable { onClick() }
        )
    }
}