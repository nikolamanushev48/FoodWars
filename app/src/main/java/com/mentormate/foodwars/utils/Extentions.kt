package com.mentormate.foodwars

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mentormate.foodwars.data.Gender

fun Context.toast(text: Int): Toast =
    Toast.makeText(this, getString(text), Toast.LENGTH_SHORT).apply { show() }

fun Context.toast(text: String): Toast =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).apply { show() }

fun AppCompatActivity.getCurrentFragment(): Fragment? =
    this.supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
        ?.run { childFragmentManager.fragments[0] ?: null }

fun Context.getGender(text: String): Gender? =
    Gender.values().find {
        this.getString(it.stringResource) == text
    }
