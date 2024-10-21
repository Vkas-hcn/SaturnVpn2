package com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.BuildConfig
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp.Companion.SaDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

object DataUtils {
    //TODO tba url
    val pp_url = "https://www.baidu.com/"
    const val vpn_url = "https://test.moonstarstable.com/zkgfP/PSWmxag/BeiM/"
    val tba_url = if (BuildConfig.DEBUG) {
        "https://test-eagan.moonstarstable.com/inflater/kruger"
    } else {
        "https://eagan.moonstarstable.com/radium/whine/frighten"
    }

    private fun stringPreferenceKey(key: String) = stringPreferencesKey(key)
    // TODO firebase key
    const val onlien_ad_key = "clap"
    const val onlien_pingbi_key = "evil"

    var openTypeIp = ""
    var homeTypeIp = ""
    var resultTypeIp = ""
    var contTypeIp = ""
    var listTypeIp = ""
    var endTypeIp = ""

    var nowLoadIpData = ""
    var nowLoadCityData = ""

    var Context.point1: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("point1")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("point1")] = value.orEmpty()
                }
            }
        }
    var Context.point7: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("point7")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("point7")] = value.orEmpty()
                }
            }
        }
    var Context.point8: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("point8")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("point8")] = value.orEmpty()
                }
            }
        }
    var Context.point16: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("point16")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("point16")] = value.orEmpty()
                }
            }
        }
    var Context.aaatt_state: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("aaatt_state")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("aaatt_state")] = value.orEmpty()
                }
            }
        }
    var Context.install_up_state: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("install_up_state")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("install_up_state")] = value.orEmpty()
                }
            }
        }
    var Context.vpn_ip: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("vpn_ip")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("vpn_ip")] = value.orEmpty()
                }
            }
        }
    var Context.vpn_city: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("vpn_city")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("vpn_city")] = value.orEmpty()
                }
            }
        }
    var Context.ben_ip: String?
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("ben_ip")] }
                .first()
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("ben_ip")] = value.orEmpty()
                }
            }
        }
    var Context.cmpData: String?
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("cmpData")] }
                .first()
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("cmpData")] = value.orEmpty()
                }
            }
        }
    var Context.userData: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("user_data")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("user_data")] = value.orEmpty()
                }
            }
        }

    //black
    var Context.blackData: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("blackData")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("blackData")] = value.orEmpty()
                }
            }
        }


    //online_vpn_data
    var Context.online_vpn_data: String?
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("online_vpn_data")] }
                .first()
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("online_vpn_data")] = value.orEmpty()
                }
            }
        }
    var Context.fast_now_vpn: String?
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("fast_now_vpn")] }
                .first()
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("fast_now_vpn")] = value.orEmpty()
                }
            }
        }
    var Context.click_now_vpn: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("click_now_vpn")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("click_now_vpn")] = value.orEmpty()
                }
            }
        }

    var Context.connect_now_vpn: String
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("connect_now_vpn")] }
                .first() ?: ""
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("connect_now_vpn")] = value.orEmpty()
                }
            }
        }

    var Context.ip_con: String?
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("ip_con")] }
                .first()
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("ip_con")] = value.orEmpty()
                }
            }
        }

    var Context.code_con: String?
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("code_con")] }
                .first()
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("code_con")] = value.orEmpty()
                }
            }
        }

    var Context.app_pack_info: String?
        get() = runBlocking {
            SaDataStore.data
                .map { preferences -> preferences[stringPreferenceKey("app_pack_info")] }
                .first()
        }
        set(value) {
            runBlocking {
                SaDataStore.edit { preferences ->
                    preferences[stringPreferenceKey("app_pack_info")] = value.orEmpty()
                }
            }
        }


    var onlien_ad_data = ""
        set(value) {
            AAApp.saturnUtils.encode("onlien_ad_data", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeString("onlien_ad_data", "") ?: ""


    var onlien_pingbi_data = ""
        set(value) {
            AAApp.saturnUtils.encode("onlien_pingbi_data", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeString("onlien_pingbi_data", "") ?: ""


    var ad_load_date = ""
        set(value) {
            AAApp.saturnUtils.encode("ad_load_date", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeString("ad_load_date", "") ?: ""


    var ad_c_num = 0
        set(value) {
            AAApp.saturnUtils.encode("ad_c_num", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeInt("ad_c_num", 0)

    var ad_s_num = 0
        set(value) {
            AAApp.saturnUtils.encode("ad_s_num", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeInt("ad_s_num", 0)

    //0:false,1:true
    var rl_state = ""
        set(value) {
            AAApp.saturnUtils.encode("rl_state", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeString("rl_state", "") ?: ""

    var rl_list = ""
        set(value) {
            AAApp.saturnUtils.encode("rl_list", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeString("rl_list", "") ?: ""


    var saturn_dow_num = ""
        set(value) {
            AAApp.saturnUtils.encode("saturn_dow_num", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeString("saturn_dow_num", "") ?: ""

    var saturn_up_num = ""
        set(value) {
            AAApp.saturnUtils.encode("saturn_up_num", value)
            field = value
        }
        get() = AAApp.saturnUtils.decodeString("saturn_up_num", "") ?: ""
}
