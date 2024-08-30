package com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss

import android.util.Base64
import com.google.gson.Gson
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.AdLjBean
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.VpnAdBean
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.blackData
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_ad_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_pingbi_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.point1

object AdDataUtils {
    fun getAdListData(): VpnAdBean {
        val onlineAdBean = onlien_ad_data
        runCatching {
            if (onlineAdBean.isNotEmpty()) {
                return Gson().fromJson(base64Decode(onlineAdBean), VpnAdBean::class.java)
            } else {
                return Gson().fromJson(adJson, VpnAdBean::class.java)
            }
        }.getOrNull() ?: return Gson().fromJson(adJson, VpnAdBean::class.java)
    }

    fun base64Decode(base64Str: String): String {
        return String(Base64.decode(base64Str, Base64.DEFAULT))
    }

    fun getLjData(): AdLjBean {
        val adRefBean = onlien_pingbi_data
        runCatching {
            if (adRefBean?.isNotEmpty() == true) {
                return Gson().fromJson(base64Decode(adRefBean), AdLjBean::class.java)
            } else {
                return Gson().fromJson(dataPingJson, AdLjBean::class.java)
            }
        }.getOrNull() ?: return Gson().fromJson(dataPingJson, AdLjBean::class.java)
    }

    fun getAdBlackData(): Boolean {
        val adBlack = when (getLjData().soon) {
            "1" -> {
                AAApp.appComponent.blackData != "squalid"
            }

            "2" -> {
                false
            }

            else -> {
                AAApp.appComponent.blackData != "squalid"
            }
        }
        if (!adBlack && AAApp.appComponent.point1 != "1") {
            TTTDDUtils.postPointData("moo1")
            AAApp.appComponent.point1 = "1"
        }
        return adBlack
    }

    fun raoliu(): Boolean {
        when (getLjData().geez) {
            "1" -> {
                return true
            }

            "2" -> {
                return false
            }

            "3" -> {
                return AAApp.appComponent.blackData != "squalid"
            }

            else -> {
                return true
            }
        }
    }

    const val open_type = "far"
    const val home_type = "eek"
    const val result_type = "mug"
    const val cont_type = "yum"
    const val list_type = "buy"
    const val end_type = "end_type"

    const val ad_wait = "ad_wait"
    const val ad_jump_over = "ad_jump_over"
    const val ad_show = "ad_show"


    const val adJson = """
        {
  "saturn_esc": 20,
  "saturn_kfv": 3,
  "far": [
    {
      "saturn_tt": "open",
      "saturn_xx": "admob",
      "saturn_dd": "ca-app-pub-3940256099942544/9257395921",
      "saturn_kk": 2
    }
  ],
  "eek": [
    {
      "saturn_tt": "native",
      "saturn_xx": "admob",
      "saturn_dd": "ca-app-pub-3940256099942544/2247696110",
      "saturn_kk": 1
    }
  ],
  "mug": [
    {
      "saturn_tt": "native",
      "saturn_xx": "admob",
      "saturn_dd": "ca-app-pub-3940256099942544/2247696110",
      "saturn_kk": 1
    }
  ],
  "yum": [
    {
      "saturn_tt": "interstitial",
      "saturn_xx": "admob",
      "saturn_dd": "ca-app-pub-3940256099942544/1033173712",
      "saturn_kk": 3
    }
  ],
  "buy": [
    {
      "saturn_tt": "interstitial",
      "saturn_xx": "admob",
      "saturn_dd": "ca-app-pub-3940256099942544/1033173712",
      "saturn_kk": 1
    }
  ],
  "gab": [
    {
      "saturn_tt": "interstitial",
      "saturn_xx": "admob",
      "saturn_dd": "ca-app-pub-3940256099942544/1033173712",
      "saturn_kk": 1
    }
  ]
}
    """
    const val dataPingJson = """
        {
  "soon": 1,
  "geez": 2
}
    """
}