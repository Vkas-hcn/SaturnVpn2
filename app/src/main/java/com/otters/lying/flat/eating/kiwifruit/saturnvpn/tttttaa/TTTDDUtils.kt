package com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.webkit.WebSettings
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.android.installreferrer.api.ReferrerDetails
import com.facebook.appevents.AppEventsLogger.Companion.newLogger
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.ResponseInfo
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.BuildConfig
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp.Companion.adManager
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp.Companion.appComponent
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp.Companion.vvState
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.AdEasy
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.AdDataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.ben_ip
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.install_up_state
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.userData
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.vpn_city
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.vpn_ip
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils.postInformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber.Forest
import timber.log.Timber.Forest.tag
import java.math.BigDecimal
import java.util.Currency
import java.util.Locale
import java.util.UUID
import kotlin.jvm.internal.Intrinsics


object TTTDDUtils {
    fun getAppVersion(): String {
        try {
            val companion = AAApp
            val packageInfo =
                appComponent.packageManager.getPackageInfo(appComponent.packageName, 0)
            val str = packageInfo.versionName
            Intrinsics.checkNotNullExpressionValue(str, "packageInfo.versionName")
            return str
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return "Version information not available"
        }
    }
    fun firstJsonData(): JSONObject {
        val jsonData = JSONObject()
        jsonData.put("senile", getAppVersion())
        jsonData.put("upraise", System.currentTimeMillis())
        jsonData.put("levitt", "111")
        jsonData.put("runoff", "111")
        jsonData.put("vow", appComponent.userData)
        jsonData.put("nutcrack", Locale.getDefault().language + '_' + Locale.getDefault().country)
        jsonData.put("acme", appComponent.packageName)
        jsonData.put("ouzo", UUID.randomUUID().toString())
        jsonData.put("cesare", "1")
        jsonData.put("copeland", "1")
        jsonData.put("tarnish", "intrigue")
        jsonData.put("britain", "111")
        return jsonData
    }
    fun getSessionJson(): String {
        return firstJsonData().apply {
            put("dulse", {})
        }.toString()
    }
    fun getInstallJson(referrerDetails: ReferrerDetails): String {
        return firstJsonData().apply {
            put("downward", JSONObject().apply {
                //build
                put("wealthy", "build/${Build.ID}")

                //referrer_url
                put("boris", referrerDetails.installReferrer)

                //install_version
                put("throng", referrerDetails.installVersion)

                //user_agent
                put("surmise", getWebDefaultUserAgent())

                //lat
                put("bloop", getLimitTracking())

                //referrer_click_timestamp_seconds
                put("unitary", referrerDetails.referrerClickTimestampSeconds)

                //install_begin_timestamp_seconds
                put("defeat", referrerDetails.installBeginTimestampSeconds)

                //referrer_click_timestamp_server_seconds
                put("carlsbad", referrerDetails.referrerClickTimestampServerSeconds)

                //install_begin_timestamp_server_seconds
                put("impound", referrerDetails.installBeginTimestampServerSeconds)

                //install_first_seconds
                put("schloss", getFirstInstallTime())

                //last_update_seconds
                put("cerberus", getLastUpdateTime())
            })

        }.toString()
    }
    fun getAdJson(
        adValue: AdValue,
        responseInfo: ResponseInfo,
        adInformation: AdEasy,
        adType: String?
    ): String {
        val topLevelJson = firstJsonData()
        val `$this$getAdJson_u24lambda_u243` = JSONObject()
        `$this$getAdJson_u24lambda_u243`.put("xhy", adInformation.loadCity)
        `$this$getAdJson_u24lambda_u243`.put("yy", adInformation.showTheCity)
        val unit = Unit
        topLevelJson.put("airline", `$this$getAdJson_u24lambda_u243`)
        val `$this$getAdJson_u24lambda_u245_u24lambda_u244` = JSONObject()
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("wiggle", adValue.valueMicros)
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("rigel", adValue.currencyCode)
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put(
            "slavic",
            responseInfo.mediationAdapterClassName
        )
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("hall", "admob")
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("wack", adInformation.saturn_dd)
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("filbert", adType)
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("seymour", adInformation.saturn_tt)
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put(
            "jason",
            getPrecisionType(adValue.precisionType)
        )
        var loadIp = adInformation.loadIp
        if (loadIp == null) {
            loadIp = ""
        }
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("gal", loadIp)
        val showIp = adInformation.showIp
        `$this$getAdJson_u24lambda_u245_u24lambda_u244`.put("dairy", showIp ?: "")
        topLevelJson.put("twosome", `$this$getAdJson_u24lambda_u245_u24lambda_u244`)
        val jSONObject = topLevelJson.toString()
        Intrinsics.checkNotNullExpressionValue(jSONObject, "topLevelJson.toString()")
        return jSONObject
    }


    private fun getTbaDataJson(name: String): String {
        return firstJsonData().apply {
            put("rooftop", name)
        }.toString()
    }

    fun getTbaTimeDataJson(
        name: String?,
        parameterName1: String,
        parameterValue1: Any?,
        parameterName2: String?,
        parameterValue2: Any?
    ): String {
        val `$this$getTbaTimeDataJson_u24lambda_u247` = firstJsonData()
        `$this$getTbaTimeDataJson_u24lambda_u247`.put("rooftop", name)
        `$this$getTbaTimeDataJson_u24lambda_u247`.put("cricket$$parameterName1", parameterValue1)
        if (parameterName2 != null) {
            `$this$getTbaTimeDataJson_u24lambda_u247`.put(
                "cricket$$parameterName2",
                parameterValue2
            )
        }
        val jSONObject = `$this$getTbaTimeDataJson_u24lambda_u247`.toString()
        Intrinsics.checkNotNullExpressionValue(
            jSONObject,
            "firstJsonData().apply {\n…   }\n        }.toString()"
        )
        return jSONObject
    }

    fun postAdOnline(adValue: AdValue, responseInfo: ResponseInfo?) {
        val adRevenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_ADMOB)
        adRevenue.setRevenue(adValue.valueMicros / 1000000.0, adValue.currencyCode)
        adRevenue.setAdRevenueNetwork(responseInfo?.mediationAdapterClassName)
        Adjust.trackAdRevenue(adRevenue)
        if (!BuildConfig.DEBUG) {
            newLogger(appComponent).logPurchase(
                BigDecimal((adValue.valueMicros / 1000000.0).toString()),
                Currency.getInstance("USD")
            )
            return
        }
        Log.d("TBA", "purchase打点--value=" + adValue.valueMicros)
    }

    fun beforeLoadLink(adInformation: AdEasy): AdEasy {
        if (vvState) {
            val dataUtils =AdDataUtils.raoliu()
            if (!dataUtils) {
                adInformation.loadIp = appComponent.vpn_ip
                adInformation.loadCity = appComponent.vpn_city
                return adInformation
            }
        }
        adInformation.loadIp = appComponent.ben_ip
        adInformation.loadCity = "null"
        return adInformation
    }

    fun afterLoadLink(adInformation: AdEasy): AdEasy {
        if (vvState) {
            val dataUtils =AdDataUtils.raoliu()
            if (!dataUtils) {
                adInformation.showIp = appComponent.vpn_ip
                adInformation.showTheCity =appComponent.vpn_city
                return adInformation
            }
        }
        adInformation.showIp =  appComponent.ben_ip
        adInformation.showTheCity = "null"
        return adInformation
    }
    fun postSessionData() {
        val data = getSessionJson()
        tag("TAG").e("postSessionData: data=%s", data)
        postInformation(data, object : NetUtils.Callback {
            override fun onSuccess(response: String) {
                Intrinsics.checkNotNullParameter(response, "response")
                tag("TAG").e("postSessionData: onSuccess=%s", response)
            }

            // com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils.Callback
            override fun onFailure(error: String) {
                Intrinsics.checkNotNullParameter(error, "error")
                tag("TAG").e("postSessionData: onFailure=%s", error)
            }
        })
    }

    fun postInstallJson(rd: ReferrerDetails?) {
        Intrinsics.checkNotNullParameter(rd, "rd")
        val data = getInstallJson(rd!!)
        tag("TAG").e("postInstallJson: data=%s", data)
        postInformation(data, object : NetUtils.Callback {
            // from class: com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils$postInstallJson$1
            // com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils.Callback
            override fun onSuccess(response: String) {
                Intrinsics.checkNotNullParameter(response, "response")
                tag("TAG").e("postInstallJson: onSuccess=%s", response)
                appComponent.install_up_state = "1"
            }

            // com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils.Callback
            override fun onFailure(error: String) {
                Intrinsics.checkNotNullParameter(error, "error")
                tag("TAG").e("postInstallJson: onFailure=%s", error)
                appComponent.install_up_state = "0"
            }
        })
    }

    fun postAdData(
        adValue: AdValue?,
        responseInfo: ResponseInfo?,
        adInformation: AdEasy?,
        adType: String?
    ) {
        Intrinsics.checkNotNullParameter(adValue, "adValue")
        Intrinsics.checkNotNullParameter(responseInfo, "responseInfo")
        Intrinsics.checkNotNullParameter(adInformation, "adInformation")
        Intrinsics.checkNotNullParameter(adType, "adType")
        val data = getAdJson(adValue!!, responseInfo!!, adInformation!!, adType)
        tag("TAG").e("postAdData: data=%s", data)
        postInformation(data, object : NetUtils.Callback {
            override fun onSuccess(response: String) {
                Intrinsics.checkNotNullParameter(response, "response")
                tag("TAG").e("postAdData: onSuccess=%s", response)
            }

            override fun onFailure(error: String) {
                Intrinsics.checkNotNullParameter(error, "error")
                tag("TAG").e("postAdData: onFailure=%s", error)
            }
        })
        postAdOnline(adValue, responseInfo)
    }


    fun postPointData(name: String, key: String?=null, keyValue: Any??=null, key2: String??=null, keyValue2: Any??=null) {
        Intrinsics.checkNotNullParameter(name, "name")
        val pointJson = if (key != null && keyValue != null) {
            getTbaTimeDataJson(name, key, keyValue, key2, keyValue2)
        } else {
            getTbaDataJson(name)
        }
        Log.e("TBA", "${name}-打点--Json--->${pointJson}")
        try {
            postInformation(pointJson, object : NetUtils.Callback {
                override fun onSuccess(response: String) {
                    Log.e("TAG", "${name}-打点事件上报-成功->${response}")

                }
                override fun onFailure(error: String) {
                    Log.e("TAG", "${name}-打点事件上报-失败=$error")
                }
            })
        } catch (e: java.lang.Exception) {
            Log.e("TAG", "${name}-打点事件上报-失败=$e")
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = appComponent.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }




    fun moo10() {
        CoroutineScope(Dispatchers.IO).launch {
            val netState = isNetworkAvailable()
            val isHaveData = if (netState) {
                "1"
            } else {
                "2"
            }
            postPointData(
                "moo10",
                "seru",
                adManager?.getAdDataState(AdDataUtils.cont_type),
                "sert",
                isHaveData
            )
        }
    }

    fun moo12() {
        val text = if (vvState) {
            "cont"
        } else {
            "dis"
        }
        postPointData(
            "moo12", "seru",
            adManager?.getAdDataState(AdDataUtils.cont_type),
            "sert",
            text
        )
    }

    fun moo14(adType: String) {
        val ss_data =AdDataUtils.raoliu()
        postPointData(
            "moo14",
            "seru",
            "${adType}+${AAApp.adTbaActivityName}",
            "sert",
            (vvState).toString()
        )
        if (vvState && !ss_data) {
            postPointData(
                "moo22",
                "seru",
                adType,
            )
        }
        if (vvState) {
            postPointData(
                "moo23",
                "seru",
                adType,
            )
        }
    }

    fun moo15(adType: String) {
        postPointData(
            "moo15",
            "seru",
            "${adType}+${AAApp.adTbaActivityName}",
        )
    }

    fun moo17(adType: String, errorString: String) {
        postPointData(
            "moo17",
            "seru",
            "${adType}+${errorString}",
        )
    }

    private fun getWebDefaultUserAgent(): String {
        return try {
            WebSettings.getDefaultUserAgent(appComponent)
        } catch (e: Exception) {
            ""
        }
    }

    private fun getFirstInstallTime(): Long {
        try {
            val packageInfo = appComponent.packageManager.getPackageInfo(appComponent.packageName, 0)
            return packageInfo.firstInstallTime / 1000
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    private fun getLastUpdateTime(): Long {
        try {
            val packageInfo = appComponent.packageManager.getPackageInfo(appComponent.packageName, 0)
            return packageInfo.lastUpdateTime / 1000
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    private fun getPrecisionType(precisionType: Int): String {
        return when (precisionType) {
            0 -> {
                "UNKNOWN"
            }

            1 -> {
                "ESTIMATED"
            }

            2 -> {
                "PUBLISHER_PROVIDED"
            }

            3 -> {
                "PRECISE"
            }

            else -> {
                "UNKNOWN"
            }
        }
    }

    private fun getLimitTracking(): String {
        return try {
            if (AdvertisingIdClient.getAdvertisingIdInfo(appComponent).isLimitAdTrackingEnabled) {
                "crime"
            } else {
                "bush"
            }
        } catch (e: Exception) {
            "bush"
        }
    }


}