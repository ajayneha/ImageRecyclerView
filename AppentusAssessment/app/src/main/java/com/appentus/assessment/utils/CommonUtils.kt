package com.appentus.assessment.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object CommonUtils {

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    fun hideSoftInput(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Hide the soft input.
     */
    fun hideSoftInput(view: View) {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}