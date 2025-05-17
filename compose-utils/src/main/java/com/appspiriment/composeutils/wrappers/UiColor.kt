package com.appspiriment.composeutils.wrappers

import android.content.Context
import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

/**
 * A sealed class to handle different types of colors in Compose UI.
 *
 * This class allows you to represent colors as a raw Color, a color resource ID,
 * or a hex string. It provides a unified way to manage colors in your UI,
 * regardless of its source.
 */
sealed class UiColor {
    companion object {
        val Black = DynamicColor(Color.Black)
        val White = DynamicColor(Color.White)
        val Transparent = DynamicColor(Color.Transparent)
        val Unspecified = DynamicColor(Color.Unspecified)
    }

    /**
     * Represents a raw Color.
     *
     * @property value The raw Color value.
     */
    data class DynamicColor(val value: Color) : UiColor()
    /**
     * Represents a color resource ID.
     *
     * @property resId The color resource ID.
     */
    class ColorResource(@ColorRes val resId: Int) : UiColor()

    /**
     * Represents a color defined by a hex string.
     *
     * @property hex The hex string representation of the color (e.g., "#FF0000" for red).
     */
    data class HexColor(val hex: String) : UiColor() {
        init {
            require(hex.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$"))) {
                "Invalid hex color format. Must be #RRGGBB or #AARRGGBB"
            }
        }
    }

    /**
     * Resolves the UiColor to a Color.
     *
     * @param context The context used to resolve color resources.
     * @return The resolved Color.
     */
    @Composable
    fun asColor(context: Context): Color {
        return when (this) {
            is DynamicColor -> value
            is ColorResource -> colorResource(resId)
            is HexColor -> Color(android.graphics.Color.parseColor(hex))
        }
    }


    fun getColor(context: Context): Color {
        return when (this) {
            is DynamicColor -> value
            is ColorResource -> Color(context.resources.getColor(resId))
            is HexColor -> Color(android.graphics.Color.parseColor(hex))
        }
    }
}

/**
 * Extension function to convert a Color to a UiColor.
 */
fun Color.toUiColor() = UiColor.DynamicColor(this)

/**
 * Extension function to convert a color resource id to a UiColor.
 */
fun Int.toUiColorResource() = UiColor.ColorResource(this)

/**
 * Extension function to convert a hex string to a UiColor.
 */
fun String.toUiColorHex() = UiColor.HexColor(this)

/**
 * Composable function to get a UiColor from a color resource ID.
 *
 * @param id The color resource ID.
 * @return The UiColor representing the color resource.
 */
@Composable
fun uiColorResource(id: Int): UiColor {
    return UiColor.ColorResource(id)
}