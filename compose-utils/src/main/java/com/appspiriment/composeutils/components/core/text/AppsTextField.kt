package com.appspiriment.composeutils.components.core.text

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appspiriment.composeutils.components.core.text.AppspirimentText
import com.appspiriment.composeutils.theme.Appspiriment
import com.appspiriment.composeutils.theme.Appspiriment.colors
import com.appspiriment.composeutils.theme.Appspiriment.sizes
import com.appspiriment.composeutils.theme.Appspiriment.typography
import com.appspiriment.composeutils.theme.medium
import com.appspiriment.composeutils.theme.normal
import com.appspiriment.composeutils.theme.semiBold
import com.appspiriment.composeutils.wrappers.UiText

@Composable
fun AppsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: UiText? = null,
    placeholder: UiText? = null,
    errorText: UiText? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = typography.textMediumMid.semiBold,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = AppsTextFieldDefaults.defaultColor(),
    labelStyle: TextFieldItemStyle = AppsTextFieldDefaults.defaultLabel(),
    placeHolderStyle: TextFieldItemStyle = AppsTextFieldDefaults.defaultItem(
        unselectedColor = Appspiriment.colors.subText.copy(alpha = 0.8f),
        unselectedTextStyle = typography.textMedium.normal
    ),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Column(modifier = modifier) {
        label?.let {
            AppspirimentText(
                text = it,
                style = labelStyle.unselectedTextStyle,
                color = labelStyle.unselectedColor,
                modifier = labelStyle.modifier
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colors.background,
                    shape = colors.borderShape
                )
                .border(
                    border = if (errorText != null) BorderStroke(1.dp, Appspiriment.colors.error) else colors.border,
                    shape = colors.borderShape
                ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle.copy(color = colors.contentColor),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            cursorBrush = SolidColor(colors.contentColor),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty() && placeholder != null) {
                            AppspirimentText(
                                text = placeholder,
                                style = placeHolderStyle.unselectedTextStyle,
                                color = placeHolderStyle.unselectedColor,
                                modifier = placeHolderStyle.modifier
                            )
                        }
                        innerTextField()
                    }

                    if (trailingIcon != null) {
                        trailingIcon()
                    }
                }
            }
        )

        AnimatedVisibility(
            visible = errorText != null,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            errorText?.let {
                AppspirimentText(
                    text = it,
                    style = typography.textSmall,
                    color = Appspiriment.colors.error,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }
        }
    }
}

data class TextFieldColors(
    val background: Color,
    val contentColor: Color,
    val border: BorderStroke,
    val borderShape: Shape
)

data class TextFieldItemStyle(
    val unselectedColor: Color,
    val unselectedTextStyle: TextStyle,
    val modifier: Modifier = Modifier,
)

object AppsTextFieldDefaults {
    @Composable
    fun defaultItem(
        unselectedColor: Color = colors.onBackground.copy(alpha = 0.7f),
        unselectedTextStyle: TextStyle = typography.textMediumMid.medium,
        modifier: Modifier = Modifier
    ): TextFieldItemStyle {
        return TextFieldItemStyle(
            unselectedColor = unselectedColor,
            unselectedTextStyle = unselectedTextStyle,
            modifier = modifier,
        )
    }

    @Composable
    fun defaultColor(
        background: Color = colors.mainSurface,
        contentColor: Color = colors.onMainSurface,
        borderWidth: Dp = 1.dp,
        borderColor: Color = colors.onMainSurface.copy(alpha = 0.1f),
        borderShape: Shape = RoundedCornerShape(sizes.cornerRadiusNormal)
    ): TextFieldColors {
        return TextFieldColors(
            background = background,
            contentColor = contentColor,
            border = BorderStroke(width = borderWidth, color = borderColor),
            borderShape = borderShape
        )
    }

    @Composable
    fun defaultLabel(
        color: Color = colors.subText,
        textStyle: TextStyle = typography.textSmallMedium,
        modifier: Modifier = Modifier.padding(
            start = sizes.paddingXSmall,
            bottom = sizes.paddingXSmall
        )
    ): TextFieldItemStyle {
        return TextFieldItemStyle(
            unselectedColor = color,
            unselectedTextStyle = textStyle,
            modifier = modifier,
        )
    }
}
