package com.greydog.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

inline fun <T : ViewDataBinding> Fragment.inflateBinding(
    @LayoutRes layoutId: Int,
    parent: ViewGroup?,
    attachToParent: Boolean = false,
    dataBindAction: (dataBind: T) -> Unit
): View = DataBindingUtil.inflate<T>(this.layoutInflater, layoutId, parent, attachToParent).apply {
    dataBindAction(this)
    lifecycleOwner = this@inflateBinding
}.root