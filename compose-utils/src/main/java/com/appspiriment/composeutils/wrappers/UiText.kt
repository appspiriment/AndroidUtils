package com.appspiriment.composeutils.wrappers

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import kotlin.collections.map

/**
 * A sealed class to handle different types of text in Compose UI.
 *
 * This class allows you to represent text as a raw string, a string resource ID,
 * a quantity string resource ID, or an AnnotatedString. It provides a unified way to manage text in your UI,
 * regardless of its source or complexity.
 */
sealed class UiText {
    companion object {
        val Empty: UiText = DynamicString("")
    }

    /**
     * Represents a raw string.
     *
     * @property value The raw string value.
     */
    data class DynamicString(val value: String) : UiText()

    /**
     * Represents a string resource ID.
     *
     * @property resId The string resource ID.
     * @property args Optional arguments to format the string. These can be raw values or other [UiText] instances.
     */
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    /**
     * Represents a quantity string resource ID.
     *
     * @property resId The quantity string resource ID.
     * @property quantity The quantity used to select the correct plural form.
     * @property args Optional arguments to format the string. These can be raw values or other [UiText] instances.
     */
    class PluralResource(
        @PluralsRes val resId: Int,
        val quantity: Int,
        vararg val args: Any
    ) : UiText()

    /**
     * Represents a string array resource ID.
     * Use [asStringList] to get the resolved list of strings.
     *
     * @property resId The string array resource ID.
     */
    data class StringArrayResource(@ArrayRes val resId: Int) : UiText()

    /**
     * Represents an AnnotatedString.
     *
     * @property value The AnnotatedString value.
     */
    data class DynamicAnnotatedString(val value: AnnotatedString) : UiText()

    /**
     * Resolves the UiText to a single String.
     * If the UiText contains other UiText instances as format arguments, they will be recursively resolved.
     *
     * @param context The context used to resolve string resources.
     * @return The resolved string.
     */
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is DynamicAnnotatedString -> value.text
            is StringArrayResource -> context.resources.getStringArray(resId).joinToString()
            is StringResource -> {
                val resolvedArgs = args.map { if (it is UiText) it.asString(context) else it }.toTypedArray()
                context.getString(resId, *resolvedArgs)
            }
            is PluralResource -> {
                val resolvedArgs = args.map { if (it is UiText) it.asString(context) else it }.toTypedArray()
                context.resources.getQuantityString(resId, quantity, *resolvedArgs)
            }
        }
    }

    /**
     * Resolves the UiText to a list of strings.
     * For single string types, it returns a list with one item.
     * For [StringArrayResource], it returns all items in the array.
     *
     * @param context The context used to resolve string resources.
     * @return The resolved list of strings.
     */
    fun asStringList(context: Context): List<String> {
        return when (this) {
            is StringArrayResource -> context.resources.getStringArray(resId).toList()
            else -> listOf(asString(context))
        }
    }

    val isAnnotatedString = this is DynamicAnnotatedString
    fun asText(context: Context) = if (isAnnotatedString) asAnnotatedString(context) else asString(context)

    /**
     * Resolves the UiText to an AnnotatedString.
     * If the UiText contains other UiText instances as format arguments, they will be recursively resolved to plain strings.
     *
     * @param context The context used to resolve string resources.
     * @return The resolved AnnotatedString.
     */
    private fun getAnnotatedString(text: String, formatHtml: Boolean = false): AnnotatedString {
        return if (formatHtml) AnnotatedString.fromHtml(text) else buildAnnotatedString { append(text) }
    }
    fun asAnnotatedString(context: Context, formatHtml: Boolean = false): AnnotatedString {
        return when (this) {
            is DynamicString -> getAnnotatedString(value, formatHtml)
            is StringResource -> getAnnotatedString(context.getString(resId, *args), formatHtml)
            is PluralResource -> getAnnotatedString(context.resources.getQuantityString(resId, quantity, *args), formatHtml)
            is DynamicAnnotatedString -> value
            else -> buildAnnotatedString { append(asString(context)) } // Fallback to asString for all other types
        }
    }

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            other !is UiText -> false
            this is DynamicString && other is DynamicString -> this.value == other.value
            this is DynamicAnnotatedString && other is DynamicAnnotatedString -> this.value == other.value
            this is StringResource && other is StringResource -> this.resId == other.resId && this.args.contentEquals(other.args)
            this is PluralResource && other is PluralResource -> this.resId == other.resId && this.quantity == other.quantity && this.args.contentEquals(other.args)
            this is StringArrayResource && other is StringArrayResource -> this.resId == other.resId
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is DynamicString -> value.hashCode()
            is StringResource -> resId.hashCode() + args.contentHashCode()
            is PluralResource -> resId.hashCode() + quantity.hashCode() + args.contentHashCode()
            is StringArrayResource -> resId.hashCode()
            is DynamicAnnotatedString -> value.hashCode()
        }
    }

    fun isBlank(context: Context): Boolean = asString(context).isBlank()
    fun isEmpty(context: Context): Boolean = asString(context).isEmpty()

    /**
     * Checks if the UiText is blank (empty or contains only whitespace) without a context.
     * This is only applicable to DynamicString and DynamicAnnotatedString.
     *
     * @return True if the UiText is blank, false otherwise.
     */
    fun isBlank(): Boolean {
        return when (this) {
            is DynamicString -> value.isBlank()
            is DynamicAnnotatedString -> value.text.isBlank()
            else -> throw Exception("For non-DynamicString values use isEmpty with context")

        }
    }

    /**
     * Checks if the UiText is empty without a context.
     * This is only applicable to DynamicString and DynamicAnnotatedString.
     *
     * @return True if the UiText is empty, false otherwise.
     */
    fun isEmpty(): Boolean {
        return when (this) {
            is DynamicString -> value.isEmpty()
            is DynamicAnnotatedString -> value.text.isEmpty()
            else -> throw Exception("For non-DynamicString values use isEmpty with context")
        }
    }

    fun takeIfNoEmpty() = takeIf { !isEmpty() }
    fun takeIfNoBlank() = takeIf { !isBlank() }
    fun takeIfNoEmpty(context: Context) = takeIf { !isEmpty(context) }
    fun takeIfNoBlank(context: Context) = takeIf { !isBlank(context) }

    @Composable
    fun asString() = asString(LocalContext.current)

    @Composable
    fun asStringList() = asStringList(LocalContext.current)
}

fun String.toUiText(): UiText = UiText.DynamicString(this)
fun Int.toUiText(): UiText = UiText.DynamicString("$this")
fun List<String>.toUiText(): List<UiText> = map{ UiText.DynamicString(it) }
fun AnnotatedString.toUiText(): UiText = UiText.DynamicAnnotatedString(this)
fun CharSequence.toUiText(): UiText = UiText.DynamicString(this.toString())

fun String?.toUiTextOrEmpty(): UiText = this?.toUiText() ?: UiText.Empty
fun Int?.toUiTextOrEmpty(): UiText = this?.toUiText() ?: UiText.Empty
fun List<String?>?.toUiTextOrEmpty(): List<UiText> = this?.map{it?.toUiText() ?: UiText.Empty } ?: emptyList()
fun AnnotatedString?.toUiTextOrEmpty(): UiText = this?.toUiText() ?: UiText.Empty
fun CharSequence?.toUiTextOrEmpty() : UiText= this?.toUiText() ?: UiText.Empty

fun String?.toUiTextOrElse(provider: ()-> String): UiText = (this?:provider()).toUiText()
fun Int?.toUiTextOrElse(provider: ()-> String): UiText = this?.toUiText() ?: provider().toUiText()
fun List<String>?.toUiTextOrElse(provider: ()-> List<String>): List<UiText> = this?.map{it.toUiText()} ?: provider().map { it.toUiText() }
fun AnnotatedString?.toUiTextOrElse(provider: ()-> String): UiText = this?.toUiText() ?: provider().toUiText()
fun CharSequence?.toUiTextOrElse(provider: ()-> String): UiText = this?.toUiText() ?: provider().toUiText()

fun String?.toUiTextOr(provider: ()-> UiText): UiText = this?.toUiText() ?:provider()
fun Int?.toUiTextOr(provider: ()-> UiText): UiText = this?.toUiText() ?: provider()
fun List<String>?.toUiTextOr(provider: ()-> List<UiText>): List<UiText> = this?.map{it.toUiText()} ?: provider().map { it }
fun AnnotatedString?.toUiTextOr(provider: ()-> UiText): UiText = this?.toUiText() ?: provider()
fun CharSequence?.toUiTextOr(provider: ()-> UiText): UiText = this?.toUiText() ?: provider()



@Composable
fun uiTextResource(id: Int, vararg formatArgs: Any): UiText {
    return stringResource(id= id, *formatArgs).toUiText()
}
@Composable
fun uiPluralTextResource(id: Int, count:Int, vararg formatArgs: Any): UiText {
    return pluralStringResource(id= id, count=count, *formatArgs).toUiText()
}

@Composable
fun uiTextArrayResource(id: Int): List<UiText> {
    return stringArrayResource(id= id).map{
        it.toUiText()
    }
}