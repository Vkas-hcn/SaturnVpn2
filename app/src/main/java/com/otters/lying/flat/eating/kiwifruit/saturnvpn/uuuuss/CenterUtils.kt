package com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.AppInfo
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.OnlineVpnBean
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.SX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseJson
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseSX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.app_pack_info
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.code_con
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.fast_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.online_vpn_data
import org.json.JSONArray

object CenterUtils {
    fun isHaveServeData(): Boolean {
        val result = AAApp.appComponent.online_vpn_data?.let { parseJson(it) }
        if (result == null) {
            NetUtils.getVpnNetData()
            return false
        }
        if (result.data.AnuW.isEmpty()) {
            NetUtils.getVpnNetData()
            return false
        }
        return true
    }

    fun getAllVpnListData(): MutableList<SX>? {
        if (AAApp.appComponent.fast_now_vpn == null) {
            return null
        }
        val bestBean = AAApp.appComponent.fast_now_vpn?.let { parseSX(it) }
        val vpnListData = getVpnList()
        return if (bestBean == null || vpnListData == null) {
            null
        } else {
            vpnListData
        }
    }
    private fun getVpnList(): MutableList<SX>? {
        return runCatching {
            val listType = object : TypeToken<OnlineVpnBean>() {}.type
            Gson().fromJson<OnlineVpnBean>(AAApp.appComponent.online_vpn_data, listType)?.data?.AnuW
        }.getOrNull()
    }
    fun getInstalledApps(activity: ComponentActivity): MutableList<AppInfo> {
        val pm: PackageManager = activity.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val appInfoList = mutableListOf<AppInfo>()
        for (app in apps) {
            if (pm.getLaunchIntentForPackage(app.packageName) != null && app.packageName != activity.packageName) {
                val name = pm.getApplicationLabel(app).toString()
                val icon = pm.getApplicationIcon(app.packageName)
                val packageName = app.packageName
                appInfoList.add(AppInfo(name, packageName, icon, false))
            }
        }
        appInfoList.sortBy { it.name.toLowerCase() }
        return appInfoList
    }

    fun saveAppStateList(appPack: String) {
        if (AAApp.appComponent.app_pack_info == null || AAApp.appComponent.app_pack_info?.isBlank() == true) {
            AAApp.appComponent.app_pack_info = appPack
        } else {
            AAApp.appComponent.app_pack_info = "${AAApp.appComponent.app_pack_info},${appPack}"
        }
    }

    fun getAppPackList(): MutableList<String> {
        val appPackInfo = AAApp.appComponent.app_pack_info ?: ""
        return appPackInfo.split(",").filterNot { it.isBlank() }.toMutableList()
    }

    fun removeAppPack(appPack: String) {
        val appPackList = getAppPackList()
        appPackList.remove(appPack)
        val updatedAppPackInfo = appPackList.joinToString(",")
        AAApp.appComponent.app_pack_info = updatedAppPackInfo
    }


    fun String.getSaturnImage():  Int {
        return when (this) {
            "United States" -> R.drawable.unitedstates
            "Australia" -> R.drawable.australia
            "Canada" -> R.drawable.canada
            "China" -> R.drawable.canada
            "France" -> R.drawable.france
            "Germany" -> R.drawable.germany
            "Hong Kong" -> R.drawable.hongkong
            "India" -> R.drawable.india
            "Japan" -> R.drawable.japan
            "koreasouth" -> R.drawable.koreasouth
            "Singapore" -> R.drawable.singapore
            "Brazil" -> R.drawable.brazil
            "United Kingdom" -> R.drawable.unitedkingdom
            "Ireland" -> R.drawable.ireland
            "Netherlands" -> R.drawable.netherlands
            else -> R.drawable.icon_fast
        }
    }
}