package com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity

data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    var isSate:Boolean,
    var isGone: Boolean = false
)
