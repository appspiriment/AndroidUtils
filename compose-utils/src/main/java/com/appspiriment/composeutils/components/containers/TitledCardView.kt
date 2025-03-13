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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.text.MalayalamText
import com.appspiriment.composeutils.components.core.text.types.UiText
import com.appspiriment.composeutils.components.core.text.types.toUiText
import com.appspiriment.composeutils.theme.Appspiriment


@Composable
fun TitledCardView(
    modifier: Modifier = Modifier,
    title: UiText? = null,
    titleStyle: TitledCardViewTitleStyle = TitleCardViewDefaults.titleAtStart(),
    background: Color = Appspiriment.colors.primaryCardContainer,
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
        colors = CardDefaults.cardColors(containerColor = background)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            title?.let {
                MalayalamText(
                    text = it,
                    style = titleStyle.style,
                    textAlign = titleStyle.align,
                    lineHeight = titleStyle.style.fontSize,
                    modifier = titleStyle.titleModifier
                        .fillMaxWidth()
                        .background(color = titleStyle.background)
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
            background = Appspiriment.colors.primaryCardContainer,
        ) {
            MalayalamText(text = "Appspiriment".toUiText())
        }

        Spacer(modifier = Modifier.height(24.dp))

        TitledCardView(
            background = Appspiriment.colors.primaryCardContainer,
            titleStyle = TitleCardViewDefaults.noticeStyle(),
        ) {
            MalayalamText(text = "Appspiriment 24".toUiText())
        }
        Spacer(modifier = Modifier.height(24.dp))

        TitledCardView(
            title = "നക്ഷത്രം".toUiText(),
            titleStyle = TitleCardViewDefaults.titleAtStart(),
            background = Appspiriment.colors.primaryCardContainer,
        ) {
            MalayalamText(text = "Appspiriment".toUiText())
        }

    }
}

data class TitledCardViewTitleStyle(
    val background: Color = Color.Transparent,
    val style: TextStyle,
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
        background: Color = Appspiriment.colors.primary,
        color: Color = Appspiriment.colors.onPrimary,
        style: TextStyle = Appspiriment.typography.textMediumSemiBold,
        align: TextAlign = TextAlign.Center,
        titleModifier: Modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 8.dp
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style.copy(color = color),
        align = align,
        titleModifier = titleModifier
    )

    @Composable
    fun titleAtStart(
        background: Color = Color.Transparent,
        color: Color = Appspiriment.colors.onPrimaryCardContainer,
        style: TextStyle = Appspiriment.typography.textSmallSemiBold,
        titleModifier: Modifier = Modifier.padding(
            start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style.copy(color = color),
        align = TextAlign.Start,
        titleModifier = titleModifier
    )

    @Composable
    fun centerTitle(
        background: Color = Color.Transparent,
        color: Color = Appspiriment.colors.onPrimaryCardContainer,
        style: TextStyle = Appspiriment.typography.textMediumSemiBold,
        titleModifier: Modifier = Modifier.padding(
            start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style.copy(color = color),
        align = TextAlign.Center,
        titleModifier = titleModifier
    )
}


