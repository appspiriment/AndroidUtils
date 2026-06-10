package com.appspiriment.composeutils.wrappers

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.core.graphics.drawable.toBitmap

/**
 * A sealed class that abstracts all image/icon sources in Compose UI into a single type.
 *
 * Use the factory functions ([ImageVector.toUiImage], [uiImageResource], [uiVectorResource],
 * [uiImageDrawable], [Painter.toUiImage]) to construct instances rather than referencing
 * subclasses directly.
 *
 * Call [withDescription] or [withTint] to produce a modified copy. Both are safe to chain.
 * Call [isVectorBased] to cheaply determine the rendering path without a composable call.
 * Call [getPainter] to render in any context; call [getImageVector] only when an [ImageVector]
 * is explicitly required.
 */
sealed class UiImage(
    open val description: String?,
    open val tint: UiColor?
) {

    // ── Subclasses ──────────────────────────────────────────────────────────────

    data class ImageVectorIcon(
        val imageVector: ImageVector,
        override val description: String? = null,
        override val tint: UiColor? = null,
    ) : UiImage(description, tint)

    data class DrawableResourceIcon(
        @DrawableRes val resId: Int,
        override val description: String? = null,
        override val tint: UiColor? = null,
    ) : UiImage(description, tint)

    /**
     * A vector drawable loaded from an XML resource. Prefer this over [DrawableResourceIcon]
     * when the resource is a vector so callers of [getImageVector] receive the typed value.
     */
    data class VectorResourceIcon(
        @DrawableRes val resId: Int,
        override val description: String? = null,
        override val tint: UiColor? = null,
    ) : UiImage(description, tint)

    data class DrawableIcon(
        val drawable: Drawable,
        override val description: String? = null,
        override val tint: UiColor? = null,
    ) : UiImage(description, tint)

    data class PainterIcon(
        val painter: Painter,
        override val description: String? = null,
        override val tint: UiColor? = null,
    ) : UiImage(description, tint)

    // ── Copy-with helpers ────────────────────────────────────────────────────────

    /**
     * Returns a copy of this image with [contentDescription] applied.
     * Adding a new subclass will produce an exhaustive-when compile error here,
     * preventing silent regressions.
     */
    fun withDescription(contentDescription: String?): UiImage = when (this) {
        is ImageVectorIcon    -> copy(description = contentDescription)
        is DrawableResourceIcon -> copy(description = contentDescription)
        is VectorResourceIcon -> copy(description = contentDescription)
        is DrawableIcon       -> copy(description = contentDescription)
        is PainterIcon        -> copy(description = contentDescription)
    }

    /** Returns a copy of this image with [tint] applied. */
    fun withTint(tint: UiColor?): UiImage = when (this) {
        is ImageVectorIcon    -> copy(tint = tint)
        is DrawableResourceIcon -> copy(tint = tint)
        is VectorResourceIcon -> copy(tint = tint)
        is DrawableIcon       -> copy(tint = tint)
        is PainterIcon        -> copy(tint = tint)
    }

    @Deprecated(
        message = "Use withDescription(contentDescription)",
        replaceWith = ReplaceWith("withDescription(contentDescription)")
    )
    fun setDescription(contentDescription: String?): UiImage = withDescription(contentDescription)

    @Deprecated(
        message = "Use withTint(tint)",
        replaceWith = ReplaceWith("withTint(tint)")
    )
    fun setTint(tint: UiColor? = null): UiImage = withTint(tint)

    // ── Type classification ──────────────────────────────────────────────────────

    /**
     * Returns true if this image is backed by a vector and [getImageVector] will return
     * a non-null value. Use this for cheap branching instead of calling [getImageVector]
     * and checking for null.
     */
    fun isVectorBased(): Boolean = this is ImageVectorIcon || this is VectorResourceIcon

    // ── Rendering ────────────────────────────────────────────────────────────────

    /**
     * Returns the [ImageVector] for vector-backed images, or null for raster/painter types.
     *
     * Prefer [isVectorBased] for branching logic to avoid the composable overhead of this call
     * when only the type matters. Use this only when you genuinely need the [ImageVector] value.
     */
    @Composable
    fun getImageVector(): ImageVector? = when (this) {
        is ImageVectorIcon    -> imageVector
        is VectorResourceIcon -> ImageVector.vectorResource(id = resId)
        is DrawableResourceIcon, is DrawableIcon, is PainterIcon -> null
    }

    /** Returns a [Painter] that renders this image. Works for all subtypes. */
    @Composable
    fun getPainter(): Painter = when (this) {
        is ImageVectorIcon    -> rememberVectorPainter(image = imageVector)
        is VectorResourceIcon -> painterResource(id = resId)
        is DrawableResourceIcon -> painterResource(id = resId)
        is DrawableIcon       -> remember(drawable) { BitmapPainter(drawable.toBitmap().asImageBitmap()) }
        is PainterIcon        -> painter
    }
}

// ── Factory functions ─────────────────────────────────────────────────────────────

fun ImageVector.toUiImage(
    description: String? = null,
    tint: UiColor? = null,
): UiImage = UiImage.ImageVectorIcon(this, description, tint)

fun uiImageResource(
    @DrawableRes resId: Int,
    description: String? = null,
    tint: UiColor? = null,
): UiImage = UiImage.DrawableResourceIcon(resId, description, tint)

fun uiVectorResource(
    @DrawableRes resId: Int,
    description: String? = null,
    tint: UiColor? = null,
): UiImage = UiImage.VectorResourceIcon(resId, description, tint)

/**
 * Wraps an Android [Drawable] as a [UiImage]. Not composable — can be called anywhere.
 * The bitmap conversion is deferred to [UiImage.getPainter] where it is safely remembered.
 */
fun uiImageDrawable(
    drawable: Drawable,
    description: String? = null,
    tint: UiColor? = null,
): UiImage = UiImage.DrawableIcon(drawable, description, tint)

fun Painter.toUiImage(
    description: String? = null,
    tint: UiColor? = null,
): UiImage = UiImage.PainterIcon(this, description, tint)

/**
 * Resolves a drawable resource by name at runtime and returns a [UiImage], or null if the
 * drawable name cannot be resolved in the current package.
 *
 * Note: [getIdentifier] returns 0 when the resource is not found. Zero is treated as
 * "not found" and produces a null return rather than a broken [UiImage.DrawableResourceIcon].
 */
@Composable
fun uiImageFromDrawableName(drawableName: String): UiImage? {
    val context = LocalContext.current
    val resources = context.resources
    val packageName = context.packageName

    val resourceId = remember(drawableName, packageName) {
        resources.getIdentifier(drawableName, "drawable", packageName)
            .takeIf { it != 0 }
            ?: run {
                Log.w("UiImage", "Drawable '$drawableName' not found in package '$packageName'")
                null
            }
    }
    return resourceId?.let { uiImageResource(it) }
}
