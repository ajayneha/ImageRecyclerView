package com.appentus.assessment.base

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.appentus.assessment.AssessmentApplication
import com.appentus.assessment.R
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

class BaseBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("underLineText")
        fun showUnderLineText(view: TextView, text: String?) {
            val content = SpannableString(text)
            content.setSpan(UnderlineSpan(), 0, text?.length ?: 0, 0)
            view.text = content
        }

        @JvmStatic
        @BindingAdapter("background")
        fun showBackground(view: View, id: Int) {
            view.setBackgroundResource(id)
        }

        @JvmStatic
        @BindingAdapter("tint")
        fun setTint(view: View, id: Int) {
            when (view) {
                is AppCompatTextView -> {
                    /*val wrappedDrawable = DrawableCompat.wrap(if (AppPreferencesHelper.getInstance().language == "en") view.compoundDrawables[0] else view.compoundDrawables[2])
                    DrawableCompat.setTint(wrappedDrawable, id)*/
                }
                is AppCompatImageView -> {
                    val wrappedDrawable = DrawableCompat.wrap(view.drawable!!)
                    DrawableCompat.setTint(wrappedDrawable, id)
                }
                is TextInputEditText -> {
                    /*val wrappedDrawable = DrawableCompat.wrap(if (AppPreferencesHelper.getInstance().language == "en") view.compoundDrawables[0] else view.compoundDrawables[2])
                    DrawableCompat.setTint(wrappedDrawable, id)*/
                }
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["font", "needArabic"], requireAll = false)
        fun setFont(textView: TextView, isBold: Boolean, needArabic: Boolean) {
            /*val typeface: Typeface? = if (needArabic) {
                ResourcesCompat.getFont(AssessmentApplication.getInstance(), if (isBold) R.font.almarai_bold else R.font.almarai_regular)
            } else {
                ResourcesCompat.getFont(AssessmentApplication.getInstance(), if (isBold) R.font.almarai_bold else R.font.almarai_regular)
            }

            textView.typeface = typeface*/
        }

        @JvmStatic
        @BindingAdapter("layout_marginBottom")
        fun setLayoutMarginBottom(view: ViewGroup, marginBottom: Float) {
            val params = view.layoutParams as MarginLayoutParams
            params.bottomMargin = marginBottom.toInt()
            view.layoutParams = params
        }

        @JvmStatic
        @BindingAdapter(value = ["imageUrl", "defaultImage", "notShowDefault"], requireAll = false)
        fun loadImage(view: ImageView, url: String?, @IdRes defaultImage: Int?, notShowDefault: Boolean) {

            val image = if (defaultImage == 0 || defaultImage == null) R.drawable.ic_launcher_background
            else
                defaultImage

            if (notShowDefault) {
                Glide.with(AssessmentApplication.getInstance())
                    .load(url)
                    .into(view)
            } else {
                Glide.with(AssessmentApplication.getInstance())
                    .load(url)
                    .placeholder(image)
                    .into(view)
            }
        }
    }
}