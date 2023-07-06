package com.mentormate.foodwars.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputEditText
import com.mentormate.foodwars.R
import com.mentormate.foodwars.data.room.Characteristic
import com.mentormate.foodwars.getGender
import com.mentormate.foodwars.ui.constants.INVALID_RES
import java.util.*

@BindingAdapter("errorByResource")
fun TextInputEditText.setTextError(@StringRes errorMessageId: Int) {
    if (errorMessageId != INVALID_RES) {
        error = context.getString(errorMessageId)
    }
}

@BindingAdapter("textResource")
fun TextView.setTextByResource(resource: Int) {
    if (resource != INVALID_RES) {
        text = context.getString(resource)
    }
}

@BindingAdapter("chipViews")
fun ChipGroup.setChipViews(characteristics: List<Characteristic>?) {
    removeAllViews()
    characteristics?.forEach {
        Chip(context).apply {
            text = it.characteristic
            isClickable = false
            chipBackgroundColor = AppCompatResources.getColorStateList(
                context,
                R.color.change_location_button_end_custom
            )
        }.also {
            addView(it)
        }
    }
}

@BindingAdapter("genderTextIcon")
fun TextView.setGenderTextIcon(genderText: String?) {
    genderText?.let { text ->
        context.getGender(text)?.imgResource?.let { res ->
            setCompoundDrawablesWithIntrinsicBounds(
                res,
                0,
                0,
                0
            )
        }
    }
}

@BindingAdapter("imageUri")
fun ImageView.setImageUri(uri: Uri?) {
    setImageURI(uri)
}


@BindingAdapter("imageByResource")
fun ImageView.setImageRes(resource: Int) {
    setImageResource(resource)
}

@SuppressLint("StringFormatInvalid")
@BindingAdapter("successTextResource", "numberOfFoods")
fun TextView.setLocationSuccessTextByResource(
    successTextResource: Int,
    numberOfFoods: Int
) {
    if (successTextResource != INVALID_RES) {
        text = context.getString(
            successTextResource,
            numberOfFoods
        )
    }
}

@BindingAdapter("textSizeByResource")
fun TextView.setTextSizeByResource(resource: Int) {
    if (resource != INVALID_RES) {
        textSize = resources.getDimension(resource)
    }
}

@BindingAdapter("buttonTextByResource")
fun TextView.setButtonTextByResource(resource: Int) {
    if (resource != INVALID_RES) {
        this.text = context.getString(resource)
    }
}

@BindingAdapter("spannableText")
fun TextView.spannableText(check: Boolean) {
    text = buildSpannedString {
        bold {
            color(Color.WHITE) {
                append(resources.getString(R.string.motivation_text_lets))
            }
            append(" ")
            color(Color.MAGENTA) {
                append(resources.getString(R.string.motivation_text_go))
            }
            append(" ")
            append(resources.getString(R.string.motivation_text_mark))
        }
    }
}

@BindingAdapter("motivationSpan")
fun TextView.spannableTextMotivation(check: Boolean) {
    text = buildSpannedString {
        bold {
            color(Color.WHITE) {
                append(resources.getString(R.string.question_who))
            }
            append(" ")
            append(resources.getString(R.string.question_is))
            append(" ")
            color(Color.MAGENTA) {
                bold {
                    append(resources.getString(R.string.question_hungry))
                }
            }
            append(resources.getString(R.string.question_question_mark))
        }
    }
}

@BindingAdapter("ratingTextByResource")
fun TextView.setRatingTextByResource(number: Int) {
    text = if (number >= 8) {
        context.getString(R.string.rating_text, number)
    } else {
        context.getString(R.string.rating_low)
    }
}

@BindingAdapter("visibilityByBool")
fun View.setVisibilityByBoolean(isVisible: Boolean) =
    if (isVisible) this.visibility = View.VISIBLE else this.visibility = View.GONE

@BindingAdapter("sliderRange")
fun RangeSlider.setSliderRange(
    pair: Pair<Float, Float>?
) {
    if (pair != null) {
        values = listOf(pair.first, pair.second)
    }
}

@InverseBindingAdapter(
    attribute = "sliderRange",
    event = "trackValueChange"
)
fun RangeSlider.setSliderRange() = values.first() to values.last()

@BindingAdapter("trackValueChange")
fun RangeSlider.addOnRangeSliderListener(listener: InverseBindingListener) {
    addOnChangeListener { _, _, _ ->
        listener.onChange()
    }
}

@BindingAdapter("urlByGlide")
fun ImageView.setUrlByGlide(url: String?) =
    url?.let {
        Glide
            .with(context)
            .load(it)
            .override(250, 250)
            .centerCrop()
            .error(R.drawable.error_icon)
            .into(this)
    }

@BindingAdapter("type")
fun TextView.setType(type: Int) {
    text = when (type) {
        54 -> context.getString(R.string.bulgarian)
        55 -> context.getString(R.string.turkish)
        56 -> context.getString(R.string.greek)
        57 -> context.getString(R.string.serbian)
        58 -> context.getString(R.string.albanian)
        59 -> context.getString(R.string.romanian)
        else -> context.getString(R.string.invalid_type)
    }
}

@BindingAdapter("interestAdapter")
fun Spinner.interestAdapter(interestList: List<String>?) {
    interestList?.let {
        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, it)
    }
}

@BindingAdapter("cardColorByRes")
fun ConstraintLayout.cardColorByRes(resource: Int) {
    if (resource != INVALID_RES) {
        setBackgroundResource(resource)
    }
}

@SuppressLint("StringFormatMatches")
@BindingAdapter("lastSyncTime")
fun TextView.lastSyncTime(
    lastTimeSync: String?
) {
    if(lastTimeSync != null){
        text = context.getString(
            R.string.last_time_sync,
            lastTimeSync
        )
    }
}

