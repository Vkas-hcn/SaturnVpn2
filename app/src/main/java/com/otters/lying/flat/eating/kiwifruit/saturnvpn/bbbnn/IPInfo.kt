package com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils.log
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.ben_ip
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.code_con
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.ip_con
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import java.util.Locale
import kotlin.system.exitProcess

data class IPInfo(
    var ip: String? = null,
    var country: String? = null,
    var countryCode: String? = null,
    var city: String? = null,
    var region: String? = null,
    var timezone: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var postalCode: String? = null,
    var countryShort: String? = null,
    var countryLong: String? = null
)

fun fetchIPInfo(url: String): String? {

    return try {

        val urlObj = URL(url)
        val conn = urlObj.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connectTimeout = 5000
        conn.readTimeout = 5000
        val responseCode = conn.responseCode

        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = conn.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = reader.readText()
            reader.close()
            response
        } else {
            Log.e(
                "TAG",
                "Error fetching IP info=connection.responseCode=${conn.responseCode}"
            )
            null
        }
    } catch (e: Exception) {
        log( "Error fetching IP info: ${e.message}")
        e.printStackTrace()
        null
    }
}

fun getIPInfo() {
    val primaryUrl = "https://api.myip.com/"
    val secondaryUrl = "https://api.infoip.io/"
    val primaryResponse = fetchIPInfo(primaryUrl)
    if (primaryResponse != null) {
        val jsonObject = JSONObject(primaryResponse)
        IPInfo(
            ip = jsonObject.getString("ip"),
            country = jsonObject.getString("country"),
            countryCode = jsonObject.getString("cc")
        )
        AAApp.appComponent.ip_con = jsonObject.getString("country")
        AAApp.appComponent.code_con = jsonObject.getString("cc")
        if (!AAApp.vvState) {
            AAApp.appComponent.ben_ip = jsonObject.getString("ip")
        }

    } else {
        val secondaryResponse = fetchIPInfo(secondaryUrl)
        if (secondaryResponse != null) {
            val jsonObject = JSONObject(secondaryResponse)
            IPInfo(
                ip = jsonObject.getString("ip"),
                country = jsonObject.getString("country_long"),
                countryCode = jsonObject.getString("country_short"),
                city = jsonObject.getString("city"),
                region = jsonObject.getString("region"),
                timezone = jsonObject.getString("timezone"),
                latitude = jsonObject.getDouble("latitude"),
                longitude = jsonObject.getDouble("longitude"),
                postalCode = jsonObject.getString("postal_code"),
                countryShort = jsonObject.getString("country_short"),
                countryLong = jsonObject.getString("country_long")
            )
            AAApp.appComponent.ip_con = jsonObject.getString("country_long")
            AAApp.appComponent.code_con = jsonObject.getString("country_short")
            if (!AAApp.vvState) {
                AAApp.appComponent.ben_ip = jsonObject.getString("ip")
            }
        }
    }
}

fun isRestrictedCountryOrRegion(): Boolean {
    val restrictedCountries = listOf("China", "Iran", "Macau", "Hong Kong")
    val restrictedCountryCodes = listOf("CN", "IR", "MO", "HK")
    return AAApp.appComponent.ip_con in restrictedCountries || AAApp.appComponent.code_con in restrictedCountryCodes
}

fun isRestrictedLocale(context: Context): Boolean {
    val restrictedLocales =
        listOf(Locale.SIMPLIFIED_CHINESE, Locale.TRADITIONAL_CHINESE, Locale("fa", "IR"))
    val currentLocale: Locale =
        context.resources.configuration.locales[0]
    return currentLocale in restrictedLocales
}

fun shouldIntercept(context: Context): Boolean {
    return isRestrictedCountryOrRegion() || isRestrictedLocale(context)
}

fun showDueDialog(context: Context): Boolean {
    // TODO ip info
    if (shouldIntercept(context)) {
        TTTDDUtils.postPointData("moo3","seru",context.ip_con)
        AlertDialog.Builder(context).apply {
            setTitle("WARN")
            setMessage("Due to the policy reason , this service is not available in your country")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                exitProcess(0)
            }
            setCancelable(false)
            show()
        }
        return true
    }
    return false
}
