package com.greydog.extensions

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

@BindingAdapter("format_string","argId")
fun TextView.setFormattedText(@StringRes format: Int, argId: Any?) {
    if(format == 0) return
    text = resources.getString(format, argId?.toString() ?: "")
}