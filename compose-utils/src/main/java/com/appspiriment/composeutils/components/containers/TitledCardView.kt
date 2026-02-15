package com.appspiriment.composeutils.components.containers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
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
import com.appspiriment.composeutils.components.core.text.AppsImageText
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.wrappers.UiText
import com.appspiriment.composeutils.wrappers.toUiText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.Appspiriment.sizes
import com.appspiriment.composeutils.theme.semiBold
import com.appspiriment.composeutils.wrappers.UiImage


@Composable
fun TextTitledCardView(
    modifier: Modifier = Modifier,
    background: Color = Appspiriment.colors.primaryCardContainer,
    shape: Shape = RoundedCornerShape(sizes.cornerRadiusNormal),
    title: UiText,
    titleStyle: TitledCardViewTitleStyle = TitleCardViewDefaults.titleAtStart(),
    cardElevation: Dp = sizes.noPadding,
    borderStroke: BorderStroke = BorderStroke(sizes.noPadding, Color.Transparent),
    contentModifier: Modifier = Modifier.padding(
        start = sizes.paddingMedium, end = sizes.paddingMedium,
        bottom = sizes.paddingMedium, top = sizes.paddingSmall
    ),
    titleAlignment: Alignment = Alignment.CenterStart,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentVerticalArrangement: Arrangement.Vertical = Arrangement.Center,
    content: @Composable ColumnScope.() -> Unit
) {
    TitledCardView(
        modifier = modifier,
        background = background,
        shape = shape,
        cardElevation = cardElevation,
        borderStroke = borderStroke,
        contentModifier = contentModifier,
        titleAlignment = titleAlignment,
        contentHorizontalAlignment = contentHorizontalAlignment,
        contentVerticalArrangement = contentVerticalArrangement,
        title = {
            AppspirimentText(
                text = title,
                style = titleStyle.style,
                textAlign = titleStyle.align,
                color = titleStyle.color,
                modifier = titleStyle.titleModifier
                    .background(titleStyle.background)
                    .padding(titleStyle.titlePadding),
            )
        },
        content = content
    )
}

@Composable
fun TitledCardView(
    modifier: Modifier = Modifier,
    background: Color = Appspiriment.colors.primaryCardContainer,
    shape: Shape = RoundedCornerShape(sizes.cornerRadiusNormal),
    cardElevation: Dp = sizes.noPadding,
    borderStroke: BorderStroke = BorderStroke(sizes.noPadding, Color.Transparent),
    contentModifier: Modifier = Modifier.padding(
        start = sizes.paddingMedium, end = sizes.paddingMedium,
        bottom = sizes.paddingMedium, top = sizes.paddingSmall
    ),
    titleAlignment: Alignment = Alignment.CenterStart,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentVerticalArrangement: Arrangement.Vertical = Arrangement.Center,
    title: (@Composable BoxScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = shape,
        border = borderStroke,
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(containerColor = background)
    ) {
        Box(
            contentAlignment = titleAlignment
        ) {
            title?.invoke(this)
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


@Preview
@Composable
fun TitledCardViewPreview() {
    Column(
        modifier = Modifier
            .padding(sizes.paddingMedium)
    ) {
        TextTitledCardView(
            title = "നക്ഷത്രം".toUiText(),
            titleStyle = TitleCardViewDefaults.noticeStyle(),
            background = Appspiriment.colors.primaryCardContainer,
        ) {
            AppspirimentText(text = "Appspiriment".toUiText())
        }

        Spacer(modifier = Modifier.height(24.dp))

        TitledCardView(
            background = Appspiriment.colors.primaryCardContainer,
        ) {
            AppspirimentText(text = "Appspiriment 24".toUiText())
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextTitledCardView(
            title = "നക്ഷത്രം".toUiText(),
            titleStyle = TitleCardViewDefaults.titleAtStart(),
            background = Appspiriment.colors.primaryCardContainer,
        ) {
            AppspirimentText(text = "Appspiriment".toUiText())
        }

    }
}

data class TitledCardViewTitleStyle(
    val background: Color = Color.Transparent,
    val style: TextStyle,
    val color: Color,
    val align: TextAlign = TextAlign.Start,
    val titleModifier: Modifier = Modifier,
    val titlePadding: PaddingValues
)

object TitleCardViewDefaults {
    @Composable
    fun noticeStyle(
        background: Color = Appspiriment.colors.primary,
        color: Color = Appspiriment.colors.onPrimary,
        style: TextStyle = Appspiriment.typography.textMedium.semiBold,
        align: TextAlign = TextAlign.Center,
        titleModifier: Modifier = Modifier.fillMaxWidth(),
        titlePadding: PaddingValues = PaddingValues(
            start = sizes.paddingMedium,
            end = sizes.paddingMedium,
            top = sizes.paddingSmall,
            bottom = sizes.paddingSmall
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style,
        color = color,
        align = align,
        titleModifier = titleModifier,
        titlePadding = titlePadding
    )

    @Composable
    fun titleAtStart(
        background: Color = Color.Transparent,
        color: Color = Appspiriment.colors.onPrimaryCardContainer,
        style: TextStyle = Appspiriment.typography.textSmall.semiBold,
        titleModifier: Modifier = Modifier,
        titlePadding: PaddingValues = PaddingValues(
            start = sizes.paddingMedium,
            end = sizes.paddingMedium,
            top = sizes.paddingSmall,
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style,
        color = color,
        align = TextAlign.Start,
        titleModifier = titleModifier,
        titlePadding = titlePadding
    )

    @Composable
    fun centerTitle(
        background: Color = Color.Transparent,
        color: Color = Appspiriment.colors.onPrimaryCardContainer,
        style: TextStyle = Appspiriment.typography.textMedium.semiBold,
        titleModifier: Modifier = Modifier.fillMaxWidth(),
        titlePadding: PaddingValues = PaddingValues(
            start = sizes.paddingMedium,
            end = sizes.paddingMedium,
            top = sizes.paddingSmall,
        )
    ) = TitledCardViewTitleStyle(
        background = background,
        style = style,
        color = color,
        align = TextAlign.Center,
        titleModifier = titleModifier,
        titlePadding = titlePadding
    )
}


