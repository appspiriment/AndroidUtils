package com.appspiriment.composeutils.components.core.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.components.containers.TitleCardViewDefaults
import com.appspiriment.composeutils.components.containers.TitledCardView


@Composable
fun KeyValuePairText(
    key: UiText,
    value: UiText,
    modifier: Modifier = Modifier,
    prefix: UiText? = null,
    keyStyle: TextStyle = Appspiriment.typography.textMediumSemiBold,
    valueStyle: TextStyle = Appspiriment.typography.textMedium,
    prefixStyle: TextStyle = Appspiriment.typography.textMedium,
    alignBothSides: Boolean = true,
    keyModifier: Modifier = Modifier,
    valueModifier: Modifier = Modifier,
    spaceBetween: Dp = 8.dp,
) {
    val modifier = if (alignBothSides) modifier.fillMaxWidth() else modifier.wrapContentWidth()
    Row(
        modifier = modifier,
        horizontalArrangement = if (alignBothSides) Arrangement.SpaceBetween else Arrangement.spacedBy(
            spaceBetween,
            Alignment.Start
        )
    ) {
        PrefixedText(
            text = key,
            prefix = prefix,
            textStyle = keyStyle,
            prefixStyle = prefixStyle,
            modifier = keyModifier
        )
        MalayalamText(
            text = value,
            style = valueStyle,
            modifier = valueModifier
        )
    }
}


@Preview
@Composable
fun DetailsListItemPreview() {
    TitledCardView(
        background = Appspiriment.colors.primaryCardContainer,
        titleStyle = TitleCardViewDefaults.titleAtStart()
    ) {
        KeyValuePairText(key = "Appspiriment".toUiText(), value = "Labs".toUiText())
        KeyValuePairText(key = "Appspiriment".toUiText(), value = "Labs".toUiText())
        KeyValuePairText(key = "Appspiriment".toUiText(), value = "Labs".toUiText(), alignBothSides = false)
    }
}
