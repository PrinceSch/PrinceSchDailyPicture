package ru.geeekbrains.princeschdailypicture.ui.main

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.show(): View {
    if (visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE){
        visibility = View.GONE
    }
    return this
}

fun View.showMessage(text: String, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, text, length).show()
}