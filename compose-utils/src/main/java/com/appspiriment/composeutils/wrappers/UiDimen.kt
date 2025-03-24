package com.appspiriment.composeutils.wrappers

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A sealed class to handle different types of dimensions in Compose UI.
 *
 * This class allows you to represent dimensions as a raw Dp, a TextUnit, or a dimension resource ID.
 * It provides a unified way to manage dimensions in your UI, regardless of its source.
 */
sealed class UiDimen {
    /**
     * Represents a raw Dp value.
     *
     * @property value The raw Dp value.
     */
    data class DynamicDp(val value: Dp) : UiDimen() {
        companion object {
            val Zero = DynamicDp(0.dp)
            val Unspecified = DynamicDp(Dp.Unspecified)
        }
    }

    /**
     * Represents a raw TextUnit (sp) value.
     *
     * @property value The raw TextUnit (sp) value.
     */
    data class DynamicTextUnit(val value: TextUnit) : UiDimen() {
        companion object {
            val Unspecified = DynamicTextUnit(TextUnit.Unspecified)
        }
    }

    /**
     * Represents a dimension resource ID.
     *
     * @property resId The dimension resource ID.
     */
    class DimenResource(@DimenRes val resId: Int) : UiDimen()

    /**
     * Resolves the UiDimen to a Dp value.
     *
     * @param context The context used to resolve dimension resources.
     * @return The resolved Dp value, or null if the resource is not found.
     */
    fun asDp(context: Context): Dp? {
        return when (this) {
            is DynamicDp -> value
            is DimenResource -> context.resources.getDimension(resId).dp
            is DynamicTextUnit -> null
        }
    }

    /**
     * Resolves the UiDimen to a TextUnit (sp) value.
     *
     * @param context The context used to resolve dimension resources.
     * @return The resolved TextUnit (sp) value, or null if the resource is not found.
     */
    fun asSp(context: Context): TextUnit? {
        return when (this) {
            is DynamicTextUnit -> value
            is DimenResource -> context.resources.getDimension(resId).sp
            is DynamicDp -> null
        }
    }
}

/**
 * Extension function to convert a Dp to a UiDimen.
 */
fun Dp.toUiDimen() = UiDimen.DynamicDp(this)

/**
 * Extension function to convert a TextUnit to a UiDimen.
 */
fun TextUnit.toUiDimen() = UiDimen.DynamicTextUnit(this)

/**
 * Extension function to convert a dimension resource id to a UiDimen.
 */
fun Int.toUiDimenResource() = UiDimen.DimenResource(this)

/**
 * Composable function to get a UiDimen from a dimension resource ID.
 *
 * @param id The dimension resource ID.
 * @return The UiDimen representing the dimension resource.
 */
@Composable
fun uiDimenResource(id: Int): UiDimen {
    return UiDimen.DimenResource(id)
}

/**
 * Utility functions for converting dimension resource IDs to various dimension units.
 */
object DimensionUtils {

    /**
     * Converts a dimension resource ID to a Dp value.
     *
     * @param context The context used to access resources.
     * @param dimenResId The dimension resource ID.
     * @return The Dp value, or null if the resource is not found.
     */
    fun dimenResToDp(context: Context, @DimenRes dimenResId: Int): Dp? {
        return try {
            val px = context.resources.getDimension(dimenResId)
            px.toDp(context)
        } catch (e: Resources.NotFoundException) {
            println("Error: Dimension resource not found: $dimenResId")
            null
        }
    }

    /**
     * Converts a dimension resource ID to a TextUnit (sp) value.
     *
     * @param context The context used to access resources.
     * @param dimenResId The dimension resource ID.
     * @return The TextUnit (sp) value, or null if the resource is not found.
     */
    fun dimenResToSp(context: Context, @DimenRes dimenResId: Int): TextUnit? {
        return try {
            val px = context.resources.getDimension(dimenResId)
            px.toSp(context)
        } catch (e: Resources.NotFoundException) {
            println("Error: Dimension resource not found: $dimenResId")
            null
        }
    }

    /**
     * Converts a pixel value to a Dp value.
     *
     * @param px The pixel value.
     * @param context The context used to get display metrics.
     * @return The Dp value.
     */
    private fun Float.toDp(context: Context): Dp {
        return (this / context.resources.displayMetrics.density).dp
    }

    /**
     * Converts a pixel value to a TextUnit (sp) value.
     *
     * @param px The pixel value.
     * @param context The context used to get display metrics.
     * @return The TextUnit (sp) value.
     */
    private fun Float.toSp(context: Context): TextUnit {
        return (this / context.resources.displayMetrics.scaledDensity).sp
    }

    @Composable
    fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }
}

@Composable
@ReadOnlyComposable
fun textSizeResource(@DimenRes id: Int): TextUnit {
    return dimensionResource(id = id).value.sp
}

