package com.appspiriment.composeutils.wrappers

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.core.graphics.drawable.toBitmap
import com.appspiriment.composeutils.wrappers.UiImage.DrawableIcon
import com.appspiriment.composeutils.wrappers.UiImage.DrawableResourceIcon
import com.appspiriment.composeutils.wrappers.UiImage.ImageVectorIcon
import com.appspiriment.composeutils.wrappers.UiImage.PainterIcon
import com.appspiriment.composeutils.wrappers.UiImage.VectorResourceIcon

sealed class UiImage(
    open val description: String?,
    open val tint: UiColor?
) {
    abstract fun setDescription(contentDescription: String?): UiImage
    abstract fun setTint(tint: UiColor? = null): UiImage

    data class ImageVectorIcon(
        val imageVector: ImageVector,
        override val description: String? = null,
        override val tint: UiColor? = null
    ): UiImage(description, tint){
        override fun setDescription(contentDescription: String?): UiImage {
            return copy(description = description)
        }
        override fun setTint(tint: UiColor?): UiImage {
            return copy(tint = tint)
        }
    }

    data class DrawableResourceIcon(
        @DrawableRes val resId: Int,
        override val description: String? = null,
        override val tint: UiColor? = null
    ): UiImage(description, tint){
        override fun setDescription(contentDescription: String?): UiImage {
            return copy(description = description)
        }
        override fun setTint(tint: UiColor?): UiImage {
            return copy(tint = tint)
        }
    }


    data class VectorResourceIcon(
        @DrawableRes val resId: Int,
        override val description: String? = null,
        override val tint: UiColor? = null
    ): UiImage(description, tint){
        override fun setDescription(contentDescription: String?): UiImage {
            return copy(description = description)
        }
        override fun setTint(tint: UiColor?): UiImage {
            return copy(tint = tint)
        }
    }

    data class DrawableIcon(
        val drawable: Drawable,
        override val description: String? = null,
        override val tint: UiColor? = null
    ): UiImage(description, tint){
        override fun setDescription(contentDescription: String?): UiImage {
            return copy(description = description)
        }
        override fun setTint(tint: UiColor?): UiImage {
            return copy(tint = tint)
        }
    }

    data class PainterIcon(
        val painter: Painter,
        override val description: String? = null,
        override val tint: UiColor? = null
    ): UiImage(description, tint){
        override fun setDescription(contentDescription: String?): UiImage {
            return copy(description = description)
        }
        override fun setTint(tint: UiColor?): UiImage {
            return copy(tint = tint)
        }
    }

    @Composable
    fun getImageVector(): ImageVector? {
        return when (this) {
            is ImageVectorIcon -> imageVector
            is VectorResourceIcon -> ImageVector.vectorResource(id = resId)
            is DrawableResourceIcon, is DrawableIcon, is PainterIcon -> null
        }
    }

    @Composable
    fun getPainter(): Painter {
        return when (this) {
            is ImageVectorIcon -> rememberVectorPainter(image = imageVector)
            is VectorResourceIcon -> painterResource(id = resId)
            is DrawableResourceIcon -> painterResource(id = resId)
            is DrawableIcon -> BitmapPainter(drawable.toBitmap().asImageBitmap())
            is PainterIcon -> painter
        }
    }
}


fun ImageVector.toUiImage(
    description: String? = null,
    tint: UiColor? = null
): UiImage = ImageVectorIcon(this, description, tint)

fun uiImageResouce(
    @DrawableRes resId: Int,
    description: String? = null,
    tint: UiColor? = null
): UiImage = DrawableResourceIcon(resId, description, tint)

fun uiVectorResouce(
    @DrawableRes resId: Int,
    description: String? = null,
    tint: UiColor? = null
): UiImage = VectorResourceIcon(resId, description, tint)

@Composable
fun uiImageDrawable(
    drawable: Drawable,
    description: String? = null,
    tint: UiColor? = null
): UiImage = DrawableIcon(drawable, description, tint)

fun Painter.toUiImage(
    description: String? = null,
    tint: UiColor? = null
): UiImage = PainterIcon(this, description, tint)
