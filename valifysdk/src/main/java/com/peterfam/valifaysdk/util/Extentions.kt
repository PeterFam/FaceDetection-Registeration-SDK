package com.peterfam.valifaysdk.util

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

fun Context.findActivity(): ComponentActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}