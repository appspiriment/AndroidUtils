package com.appspiriment.composeutils.components.containers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.toUiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.semiBold
import com.appspiriment.composeutils.wrappers.UiColor


@Composable
fun TitledCardView(
    modifier: Modifier = Modifier,
    title: UiText? = null,
    titleStyle: TitledCardViewTitleStyle = TitleCardViewDefaults.titleAtStart(),
    background: UiColor = Appspiriment.uiColors.primaryCardContainer,
    shape: Shape = RoundedCornerShape(8.dp),
    cardElevation: Dp = 0.dp,
    borderStroke: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    contentModifier: Modifier = Modifier.padding(
        start = 16.dp, end = 16.dp,
        bottom = 16.dp, top = 8.dp
    ),
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentVerticalArrangement: Arrangement.Vertical = Arrangement.Center,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .background(color = Color.Blue, shape = shape),
        shape = shape,
        border = borderStroke,
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(containerColor = background.asColor(LocalContext.current))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            title?.let {
                AppspirimentText(
                    text = it,
                    color = titleStyle.color,
                    style = titleStyle.style,
                    textAlign = titleStyle.align,
                    lineHeight = titleStyle.style.fontSize,
                    modifier = titleStyle.titleModifier
                        .fillMaxWidth()
                        .background(color = titleStyle.background.asColor(LocalContext.current))
                )
            }
            Column(
                horizontalAlignment = contentHorizontalAlignment,
                verticalArrangement = contentVerticalArrangement,
                modifier = contentModifier
                    .fillMaxWidth()
            ) {
                content()
            }
        }
    }
}


@Preview
@Composable
fun TitledCardViewPreview() {
    Column(
        modifier = Modifier
            .background(Color.Red)
            .padding(16.dp)
    ) {
        TitledCardView(
            title = "നക്ഷത്രം".toUiText(),
            titleStyle = TitleCardViewDefaults.noticeStyle(),
            background = Appspiriment.uiColors.primaryCardContainer,
        ) {
            AppspirimentText(text = "Appspiriment".toUiText())
        }

        Spacer(modifier = Modifier.height(24.dp))

        TitledCardView(
            background = Appspiriment.uiColors.primaryCardContainer,
            titleStyle = TitleCardViewDefaults.noticeStyle(),
        ) {
            AppspirimentText(text = "Appspiriment 24".toUiText())
        }
        Spacer(modifier = Modifier.height(24.dp))

        TitledCardView(
            title = "നക്ഷത്രം".toUiText(),
            titleStyle = TitleCardViewDefaults.titleAtStart(),
            background = Appspiriment.uiColors.primaryCardContainer,
        ) {
            AppspirimentText(text = "Appspiriment".toUiText())
        }

    }
}

data class TitledCardViewTitleStyle(
    val background: UiColor = UiColor.Transparent,
    val style: TextStyle,
    val color: UiColor,
    val align: TextAlign = TextAlign.Center,
    val titleModifier: Modifier = Modifier.padding(
        start = 16.dp,
        end = 16.dp,
        top = 8.dp,
        bottom = 8.dp
    )
)

object TitleCardViewDefaults {
    @Composable
    fun noticeStyle(
        background: UiColor = Appspiriment.uiColors.primary,
        color: UiColor = Appspiriment.uiColors.onPrimary,
        style: TextStyle = Appspiriment.typography.textMedium.semiBold,
        align: TextAlign = TextAlign.Center,
        titleModifier: Modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 8.dp
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style,
        color = color,
        align = align,
        titleModifier = titleModifier
    )

    @Composable
    fun titleAtStart(
        background: UiColor = UiColor.Transparent,
        color: UiColor = Appspiriment.uiColors.onPrimaryCardContainer,
        style: TextStyle = Appspiriment.typography.textSmall.semiBold,
        titleModifier: Modifier = Modifier.padding(
            start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style,
        color = color,
        align = TextAlign.Start,
        titleModifier = titleModifier
    )

    @Composable
    fun centerTitle(
        background: UiColor = UiColor.Transparent,
        color: UiColor = Appspiriment.uiColors.onPrimaryCardContainer,
        style: TextStyle = Appspiriment.typography.textMedium.semiBold,
        titleModifier: Modifier = Modifier.padding(
            start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style,
        color = color,
        align = TextAlign.Center,
        titleModifier = titleModifier
    )
}


