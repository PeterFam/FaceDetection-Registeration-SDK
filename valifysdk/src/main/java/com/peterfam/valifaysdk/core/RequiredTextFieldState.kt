package com.peterfam.valifaysdk.core

import com.peterfam.valifysdk.R

/**
 * This class represents a state for a required text field. It extends from AbstractTextFieldState.
 *
 * @property newText A nullable string that represents the initial text of the text field.
 * @property inputFilter A function that takes a string and returns a string. It is used to filter the input of the text field.
 *
 * @author Hassan Mohammed
 */
class RequiredTextFieldState(
    newText: String? = null,
    inputFilter: (String) -> String = { it }
) : AbstractTextFieldState(
    // The validator function checks if the text is not empty.
    validator = ::isValid,
    // The errorFor function returns a UiText with a string resource for the required error message.
    errorFor = { validationErrorMessage() },
    inputFilter = inputFilter
) {
    init {
        // If newText is not null, it is assigned to the text property.
        newText?.let {
            text = it
        }
    }
}

/**
 * This function returns a UiText with a string resource for the required error message.
 *
 * @return A UiText with a string resource for the required error message.
 */
private fun validationErrorMessage(): UiText {
    return UiText.StringResource(R.string.error_required)
}

/**
 * This function checks if the text is not empty.
 *
 * @param text The text to validate.
 * @return A boolean indicating whether the text is not empty.
 */
private fun isValid(text: String): Boolean {
    return text.isNotEmpty()
}