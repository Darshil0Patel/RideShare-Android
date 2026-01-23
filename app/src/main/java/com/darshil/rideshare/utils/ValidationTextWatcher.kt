package com.darshil.rideshare.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class ValidationTextWatcher(
    private val textInputLayout: TextInputLayout,
    private val validator: (String) -> ValidationResult
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString()

        when (val result = validator(text)) {
            is ValidationResult.Success -> {
                textInputLayout.error = null
                textInputLayout.isErrorEnabled = false
            }
            is ValidationResult.Error -> {
                if (text.isNotEmpty()) {  // Only show error if user has typed something
                    textInputLayout.error = result.message
                }
            }
        }
    }
}