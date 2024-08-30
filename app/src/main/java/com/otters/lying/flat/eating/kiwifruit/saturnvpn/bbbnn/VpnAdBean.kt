package com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn

import androidx.annotation.Keep

@Keep
data class VpnAdBean(
    val saturn_esc: Int,
    val saturn_kfv: Int,
    val far: List<AdEasy>,
    val eek: List<AdEasy>,
    val mug: List<AdEasy>,
    val yum: List<AdEasy>,
    val buy: List<AdEasy>,
    val gab: List<AdEasy>,

    )
@Keep
data class AdEasy(
    val saturn_dd: String,
    val saturn_kk: Int,
    val saturn_xx: String,
    val saturn_tt: String,
    var loadCity: String? = null,
    var showTheCity: String? = null,
    var loadIp: String? = null,
    var showIp: String? = null,
)

@Keep
data class AdLjBean(
    val soon: String,
    val geez: String,
)
