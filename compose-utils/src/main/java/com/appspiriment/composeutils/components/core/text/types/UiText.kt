package com.appspiriment.composeutils.components.core.text.types

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml

/**
 * A sealed class to handle different types of text in Compose UI.
 *
 * This class allows you to represent text as a raw string, a string resource ID,
 * a quantity string resource ID, or an AnnotatedString. It provides a unified way to manage text in your UI,
 * regardless of its source or complexity.
 */
sealed class UiText {
    /**
     * Represents a raw string.
     *
     * @property value The raw string value.
     */
    data class DynamicString(val value: String) : UiText(){
        companion object{
            val Empty = DynamicString("")
        }
    }

    /**
     * Represents a string resource ID.
     *
     * @property resId The string resource ID.
     * @property args Optional arguments to format the string.
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
     * @property args Optional arguments to format the string.
     */
    class PluralResource(
        @PluralsRes val resId: Int,
        val quantity: Int,
        vararg val args: Any
    ) : UiText()

    /**
     * Represents an AnnotatedString.
     *
     * @property value The AnnotatedString value.
     */
    data class DynamicAnnotatedString(val value: AnnotatedString) : UiText(){
        companion object{
            val Empty = DynamicAnnotatedString(AnnotatedString(""))
        }
    }

    /**
     * Resolves the UiText to a String.
     *
     * @param context The context used to resolve string resources.
     * @return The resolved string.
     */
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
            is PluralResource -> context.resources.getQuantityString(resId, quantity, *args)
            is DynamicAnnotatedString -> value.text
        }
    }

    val isAnnotatedString = this is DynamicAnnotatedString
    fun asText(context: Context) = if(isAnnotatedString) asAnnotatedString(context) else asString(context)

    /**
     * Resolves the UiText to an AnnotatedString.
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
        }
    }
}

fun String.toUiText() = UiText.DynamicString(this)
fun Int.toUiText() = UiText.DynamicString("$this")
fun List<String>.toUiText() = map{ UiText.DynamicString(it) }
fun AnnotatedString.toUiText() = UiText.DynamicAnnotatedString(this)
fun CharSequence.toUiText() = UiText.DynamicString(this.toString())


@Composable
fun uiTextResource(id: Int, vararg formatArgs: Any): UiText {
    return stringResource(id= id, *formatArgs).toUiText()
}
@Composable
fun uiPluralTextResource(id: Int, count:Int, vararg formatArgs: Any): UiText {
    return pluralStringResource(id= id, count=count, *formatArgs).toUiText()
}