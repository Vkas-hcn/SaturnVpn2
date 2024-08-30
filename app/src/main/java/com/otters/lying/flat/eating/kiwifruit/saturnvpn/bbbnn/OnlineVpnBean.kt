package com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn

import android.util.Base64
import androidx.annotation.Keep
import org.json.JSONArray
import org.json.JSONObject

@Keep
data class OnlineVpnBean(
    val code: Int,
    val `data`: Data,
    val msg: String
)

@Keep
data class Data(
    val AnuW: MutableList<SX>,
    val Xtco: MutableList<SX>
)

@Keep
data class SX(
    val TiORzr: String,
    val ect: String,
    val LMfccpX: String,
    val EtJazV: String,
    val dAIpu: String,
    val ivIQX: String,
    val TpG: Int,
    val rqHlQEGQk: String,
    val CwDwOnOtuQ: String,

    var best_data: Boolean = false,
    var check_data: Boolean = false,
)


fun parseJson(jsonString: String): OnlineVpnBean? {
    val jsonObject = JSONObject(jsonString)

    val code = jsonObject.getInt("code")
    val msg = jsonObject.getString("msg")

    val dataObject: JSONObject? = jsonObject.optJSONObject("data")
    val sXsArray = dataObject?.getJSONArray("AnuW")
    val smart = dataObject?.getJSONArray("Xtco")

    val AnuW = mutableListOf<SX>()
    val Xtco = mutableListOf<SX>()

    if (dataObject == null) {
        return null
    }
    if (sXsArray == null) {
        return null
    }
    if (smart == null) {
        return null
    }
    for (i in 0 until sXsArray.length()) {
        val sxObject = sXsArray.getJSONObject(i)
        val sx = SX(
            TiORzr = sxObject.getString("TiORzr"),
            ect = sxObject.getString("ect"),
            LMfccpX = sxObject.getString("LMfccpX"),
            EtJazV = sxObject.getString("EtJazV"),
            dAIpu = sxObject.getString("dAIpu"),
            ivIQX = sxObject.getString("ivIQX"),
            TpG = sxObject.getInt("TpG"),
            rqHlQEGQk = sxObject.getString("rqHlQEGQk"),
            CwDwOnOtuQ = sxObject.getString("CwDwOnOtuQ"),
        )
        AnuW.add(sx)
    }

    for (i in 0 until smart.length()) {
        val sxObject = smart.getJSONObject(i)
        val sx = SX(
            TiORzr = sxObject.getString("TiORzr"),
            ect = sxObject.getString("ect"),
            LMfccpX = sxObject.getString("LMfccpX"),
            EtJazV = sxObject.getString("EtJazV"),
            dAIpu = sxObject.getString("dAIpu"),
            ivIQX = sxObject.getString("ivIQX"),
            TpG = sxObject.getInt("TpG"),
            rqHlQEGQk = sxObject.getString("rqHlQEGQk"),
            CwDwOnOtuQ = sxObject.getString("CwDwOnOtuQ"),
        )
        Xtco.add(sx)
    }
    val data = Data(AnuW = AnuW, Xtco = Xtco)
    return OnlineVpnBean(code = code, `data` = data, msg = msg)
}

fun parseSX(jsonString: String): SX {
    val jsonObject = JSONObject(jsonString)
    return SX(
        TiORzr = jsonObject.getString("TiORzr"),
        ect = jsonObject.getString("ect"),
        LMfccpX = jsonObject.getString("LMfccpX"),
        EtJazV = jsonObject.getString("EtJazV"),
        dAIpu = jsonObject.getString("dAIpu"),
        ivIQX = jsonObject.getString("ivIQX"),
        TpG = jsonObject.getInt("TpG"),
        rqHlQEGQk = jsonObject.getString("rqHlQEGQk"),
        CwDwOnOtuQ = jsonObject.getString("CwDwOnOtuQ"),

        best_data = jsonObject.getBoolean("best_data"),
        check_data = jsonObject.getBoolean("check_data")
    )
}

// 扩展函数将 JSONArray 转换为 List<String>
fun JSONArray.toStringList(): List<String> {
    val list = mutableListOf<String>()
    for (i in 0 until this.length()) {
        list.add(this.getString(i))
    }
    return list
}

// 扩展函数将 JSONArray 转换为 List<Any>
fun JSONArray.toList(): List<Any> {
    val list = mutableListOf<Any>()
    for (i in 0 until this.length()) {
        list.add(this.get(i))
    }
    return list
}

fun SX.toJson(): String {
    val jsonObject = JSONObject()
    jsonObject.put("TiORzr", this.TiORzr)
    jsonObject.put("ect", this.ect)
    jsonObject.put("LMfccpX", this.LMfccpX)
    jsonObject.put("EtJazV", this.EtJazV)
    jsonObject.put("dAIpu", this.dAIpu)
    jsonObject.put("ivIQX", this.ivIQX)
    jsonObject.put("TpG", this.TpG)
    jsonObject.put("rqHlQEGQk", this.rqHlQEGQk)
    jsonObject.put("CwDwOnOtuQ", this.CwDwOnOtuQ)
    jsonObject.put("best_data", this.best_data)
    jsonObject.put("check_data", this.check_data)
    return jsonObject.toString()
}


