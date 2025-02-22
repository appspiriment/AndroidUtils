package com.appspiriment.composeutils.components.core.dropdowns

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.appspiriment.composeutils.components.core.dropdowns.models.DropDownItem
import com.appspiriment.composeutils.components.core.dropdowns.models.SpinnerStyle
import com.appspiriment.composeutils.components.core.dropdowns.models.SpinnerStyleDefaults
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.theme.Appspiriment

@Composable
fun DropDownSpinner(
    items: List<UiText>,
    modifier: Modifier = Modifier,
    dropdownModifier: Modifier = Modifier,
    onSelectedIndexChange: (Int) -> Unit = {},
    itemStyle: SpinnerStyle = SpinnerStyleDefaults.defaultSpinner,
    spinnerComposable: @Composable (index: Int, text:UiText, onClick: ()->Unit) -> Unit
) {
    DropDownModelSpinner(
        items = items.map {
            DropDownItem(label = it)
        },
        modifier = modifier,
        dropdownModifier = dropdownModifier,
        onSelectedIndexChange = onSelectedIndexChange,
        itemStyle = itemStyle,
        spinnerComposable = spinnerComposable
    )
}

@Composable
fun DropDownModelSpinner(
    items: List<DropDownItem>,
    modifier: Modifier = Modifier,
    dropdownModifier: Modifier = Modifier,
    containerColor: Color = Appspiriment.colors.background,
    onSelectedIndexChange: (Int) -> Unit = {},
    itemStyle: SpinnerStyle = SpinnerStyleDefaults.defaultSpinner,
    spinnerComposable: @Composable (index: Int, text: UiText, onClick: () -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val selectedText by remember { derivedStateOf { items.getOrNull(selectedIndex)?.label } }
    Box(contentAlignment = Alignment.Center) {
        Box() {
            spinnerComposable(selectedIndex, selectedText ?: "Not Selected".toUiText()) {
                expanded = true
            }
        }
        DropdownMenu(
            modifier = dropdownModifier,
            expanded = expanded,
            containerColor = containerColor,
            onDismissRequest = {
                expanded = false
            }) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        itemStyle.run {
                            MalayalamText(
                                text = item.label,
                                modifier = modifier,
                                style = textStyle.copy(color = textColor),
                                textAlign =textAlign,
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                        selectedIndex = index
                        onSelectedIndexChange(index)
                    }
                )
            }
        }
    }
}


