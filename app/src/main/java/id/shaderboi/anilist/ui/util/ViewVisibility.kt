package id.shaderboi.anilist.ui.util

import android.view.View

fun View.shown(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
