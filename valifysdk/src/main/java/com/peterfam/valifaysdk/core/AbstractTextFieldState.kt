package com.peterfam.valifaysdk.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * This class represents a state for a text field. It includes validation and error handling.
 *
 * @property validator A lambda function that takes a string and returns a boolean. It is used to validate the text field's input.
 * @property errorFor A lambda function that takes a string and returns a UiText. It is used to generate the error message for invalid input.
 */
abstract class AbstractTextFieldState(
    private val validator: (String) -> Boolean = { true },
    var errorFor: (String) -> UiText? = { UiText.DynamicText("") },
    private val inputFilter: (String) -> String = { it },
) {
    // The current text of the text field.
    var text by mutableStateOf("")

    // A flag indicating whether the text field has been focused at least once.
    private var isFocusedDirty by mutableStateOf(false)

    // A flag indicating whether the text field is currently focused.
    private var isFocused by mutableStateOf(false)

    // A flag indicating whether to display errors.
    private var displayErrors by mutableStateOf(false)

    /**
     * A property that checks whether the current text is valid according to the validator function.
     */
    open val isValid get() = validator(text)

    /**
     * A property that checks whether there is an error. An error exists if the text is not valid and errors are set to be displayed.
     */
    val hasError get() = !isValid && displayErrors

    /**
     * A function that updates the focus state of the text field. If the text field is focused, it sets isFocusedDirty to true.
     *
     * @param focused A boolean indicating whether the text field is focused.
     */
    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    /**
     * A function that updates the text of the text field.
     *
     * @param newText The new text to set.
     */
    open fun onValueChange(newText: String) {
        text = inputFilter(newText)
    }

    /**
     * A function that clears the text of the text field.
     */
    fun clearText(): AbstractTextFieldState {
        return apply {
            text = ""
        }
    }

    fun updateText(newText: String): AbstractTextFieldState {
        return apply {
            text = newText
        }
    }

    /**
     * A function that checks whether the text field is empty.
     *
     * @return A boolean indicating whether the text field is empty.
     */
    fun isEmpty() = text.isEmpty()

    /**
     * A function that enables the display of errors if the text field has been focused at least once.
     */
    fun enableShowErrors() {
        if (isFocusedDirty)
            displayErrors = true
    }

    /**
     * A function that returns the error message if there is an error, otherwise it returns null.
     *
     * @return The error message as a UiText if there is an error, otherwise null.
     */
    open fun getError() = if (hasError) errorFor(text) else null
}
