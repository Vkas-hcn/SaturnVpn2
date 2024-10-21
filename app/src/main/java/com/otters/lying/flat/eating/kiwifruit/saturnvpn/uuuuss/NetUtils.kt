package com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Base64
import android.util.Log
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.SX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseJson
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.toJson
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils.log
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.blackData
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.fast_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.online_vpn_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.userData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NetUtils {
    interface Callback {
        fun onSuccess(result: String)
        fun onFailure(error: String)
    }

    @SuppressLint("HardwareIds")
    fun blackData(context: Context): Map<String, Any> {
        // TODO hmd package name
        return mapOf(
            "acme" to "com.selene.moon.star.link.stable",
            "tarnish" to "intrigue",
            "senile" to getAppVersion(context).orEmpty(),
            "vow" to AAApp.appComponent.userData,
        )
    }

    fun getBlackList(context: Context) {
        if (AAApp.appComponent.blackData.isNotEmpty()) {
            return
        }
        log( "Black--URL: ${AAApp.appComponent.blackData}")
        getMapData(
            "https://quince.moonstarstable.com/sci/aromatic/cork",
            blackData(context),
            onNext = {
                log( "The blacklist request is successful：$it")
                AAApp.appComponent.blackData = it
            },
            onError = {
                GlobalScope.launch(Dispatchers.IO) {
                    delay(10000)
                    log( "The blacklist request failed：$it")
                    getBlackList(context)
                }
            })
    }

    private fun getAppVersion(context: Context): String? {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun getVpnNetData() {
        getServiceData(
            DataUtils.vpn_url,
            onSuccess = {
                val result = processString(it)
                log( "getVpnNetData-onSuccess:$result")
                if (result == null) return@getServiceData
                AAApp.appComponent.online_vpn_data = (result ?: "").toString()
                getFastVpnData(result)
            },
            onError = {
                log( "getVpnNetData -onError: $it")
            }
        )
    }

    fun getFastVpnData(resultData: String) {
        val result = parseJson(resultData) ?: return
        if (result.data.Xtco.isNotEmpty()) {
            val fastVpnBean: SX = result.data.Xtco.random()
            fastVpnBean.best_data = true
            AAApp.appComponent.fast_now_vpn = fastVpnBean.toJson()
        }
    }

    private fun processString(input: String): String? {
        if (input.length <= 16) {
            return null
        }
        val trimmedString = input.drop(10)
        val swappedCaseString = trimmedString.map {
            when {
                it.isUpperCase() -> it.toLowerCase()
                it.isLowerCase() -> it.toUpperCase()
                else -> it
            }
        }.joinToString("")

        try {
            val decodedBytes = Base64.decode(swappedCaseString, Base64.DEFAULT)
            return String(decodedBytes!!, Charsets.UTF_8)

        } catch (e: IllegalArgumentException) {
            println("Invalid Base64 input: $e")
            return null
        }
    }

    private fun getServiceData(
        url: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        Thread {
            try {
                val urlObj = URL(url)
                val conn = urlObj.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000
                val customHeaders = mapOf(
                    "KPHS" to "ZZ",
                    "YBI" to AAApp.appComponent.packageName,
                )
                for ((key, value) in customHeaders) {
                    conn.setRequestProperty(key, value)
                }
                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = conn.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = reader.readText()
                    reader.close()
                    onSuccess(response)
                } else {
                    onError("Error from server: $responseCode")
                }
            } catch (e: Exception) {
                onError("Network error or other exception: ${e.message}")
            }
        }.start()
    }


    fun postInformation(body: Any, callback: Callback) {
        Thread {
            var connection: HttpURLConnection? = null
            try {
                val urlConnection = URL(DataUtils.tba_url).openConnection() as HttpURLConnection
                connection = urlConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                connection.setRequestProperty("Accept", "application/json")
                connection.doOutput = true
                connection.doInput = true

                // Write body to the request
                BufferedWriter(OutputStreamWriter(connection.outputStream, "UTF-8")).use { writer ->
                    writer.write(body.toString())
                    writer.flush()
                }

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseBody = connection.inputStream.bufferedReader().use { it.readText() }
                    callback.onSuccess(responseBody)
                } else {
                    val errorBody = connection.errorStream.bufferedReader().use { it.readText() }
                    callback.onFailure(errorBody)
                }
            } catch (e: IOException) {
                callback.onFailure("Network error: ${e.message}")
            } finally {
                connection?.disconnect()
            }
        }.start()
    }

    fun getMapData(
        url: String,
        map: Map<String, Any>,
        onNext: (response: String) -> Unit,
        onError: (error: String) -> Unit
    ) {

        val queryParameters = StringBuilder()
        for ((key, value) in map) {

            queryParameters.append("&")
            queryParameters.append(URLEncoder.encode(key, "UTF-8"))
            queryParameters.append("=")
            queryParameters.append(URLEncoder.encode(value.toString(), "UTF-8"))
        }

        val urlString = if (url.contains("?")) {
            "$url&$queryParameters"
        } else {
            "$url?$queryParameters"
        }
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 15000

        try {

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                inputStream.close()
                onNext(response.toString())
            } else {
                onError("HTTP error: $responseCode")
            }
        } catch (e: Exception) {
            onError("Network error: ${e.message}")
        } finally {
            connection.disconnect()
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        if (networkCapabilities != null) {
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }
        return false
    }

    private fun isLoopbackAddress(address: ByteArray): Boolean {
        return address[0] == 127.toByte()
    }
}