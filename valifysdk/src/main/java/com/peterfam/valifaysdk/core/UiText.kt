package com.peterfam.valifaysdk.core

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.peterfam.valifysdk.R

sealed interface UiText {
    /**
     * Represents a dynamic text which can be null.
     * @property value The actual text value.
     */
    data class DynamicText(val value: String?) : UiText

    /**
     * Represents a string resource.
     * @property resId The resource ID of the string.
     */
    data class StringResource(@StringRes val resId: Int) : UiText

    /**
     * Represents a string resource with format arguments.
     * @property resId The resource ID of the string.
     * @property formatArgs The format arguments for the string resource.
     */
    class StringResourceWithArgs(@StringRes val resId: Int, vararg val formatArgs: Any) : UiText

    /**
     * Converts the UI text to a string using the provided context.
     * @param context The context to use for string resources.
     * @return The UI text as a string.
     */
    fun asString(context: Context): String = when (this) {
        is DynamicText -> value ?: context.getString(R.string.error_unknown)
        is StringResource -> context.getString(resId)
        is StringResourceWithArgs -> context.getString(resId, *formatArgs)
    }

    /**
     * Converts the UI text to a string in a composable function.
     * @return The UI text as a string.
     */
    @Composable
    fun asString(): String = when (this) {
        is DynamicText -> value ?: ""
        is StringResource -> stringResource(id = resId)
        is StringResourceWithArgs -> stringResource(id = resId, formatArgs)
    }

    companion object {
        fun unknownError() = StringResource(R.string.error_unknown)
    }
}

/**
 * Returns the UI text if it's not null, otherwise returns a string resource representing an unknown error.
 * @return The UI text or a string resource representing an unknown error.
 */
fun UiText?.orUnknownError(): UiText = this ?: UiText.StringResource(R.string.error_unknown)

/**
 * Returns the UI text if it's not null, otherwise returns a dynamic text with an empty string.
 * @return The UI text or a dynamic text with an empty string.
 */
fun UiText?.orEmpty(): UiText = this ?: UiText.DynamicText("")