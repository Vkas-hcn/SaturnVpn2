package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.ssstt

import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.BuildConfig
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseViewModel
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_ad_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_pingbi_data


class StartViewModel: BaseViewModel() {
    val liveHomeData = MutableLiveData<Boolean>()
    var isFinish = false
    fun getOnlineData(){
        if (!BuildConfig.DEBUG) {
            val auth = Firebase.remoteConfig
            auth.fetchAndActivate().addOnSuccessListener {
                onlien_ad_data = auth.getString(DataUtils.onlien_ad_key)
                onlien_pingbi_data = auth.getString(DataUtils.onlien_pingbi_key)
                isFinish = true
            }
        }
    }
}