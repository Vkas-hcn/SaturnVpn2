package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.hhhee

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager
import com.github.shadowsocks.preference.DataStore
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseViewModel
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.IPInfo
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.OnlineVpnBean
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.SX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.getIPInfo
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseJson
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseSX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.shouldIntercept
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.showDueDialog
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.toJson
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentHomeBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentMoreBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.CenterUtils.getSaturnImage
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.click_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.connect_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.fast_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.online_vpn_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.vpn_city
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.vpn_ip
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils
import kotlin.system.exitProcess

class HomeViewModel : BaseViewModel() {
    fun initializeServerData(binding: FragmentHomeBinding, connectFun: () -> Unit) {
        AAApp.appComponent.online_vpn_data?.let { it1 -> NetUtils.getFastVpnData(it1) }

        var saveBean = try {
            AAApp.appComponent.connect_now_vpn.let { parseSX(it) }

        } catch (e: Exception) {
            null
        }
        if (saveBean != null && saveBean.best_data) {
            saveBean = AAApp.appComponent.fast_now_vpn?.let { parseSX(it) }
        }
        val clickNowVpn = saveBean
            ?: if (AAApp.jumpToHomeType == "2") {
                AAApp.appComponent.click_now_vpn.let { parseSX(it) }
            } else {
                getFastVpnData() ?: return
            }
        if (clickNowVpn.CwDwOnOtuQ.isBlank()) return
        ProfileManager.getProfile(DataStore.profileId).let {
            if (it != null) {
                ProfileManager.updateProfile(setSkServerData(it, clickNowVpn))
            } else {
                val profile = Profile()
                ProfileManager.createProfile(setSkServerData(profile, clickNowVpn))
            }
        }
        DataStore.profileId = 1L
        updateUiWithServerData(binding)
        if (AAApp.jumpToHomeType != "0") {
            connectFun()
            AAApp.jumpToHomeType = "0"
        }
    }

    private fun updateUiWithServerData(binding: FragmentHomeBinding) {
        val connectNowVpn = AAApp.appComponent.connect_now_vpn.let { parseSX(it) }
        Log.e("TAG", "updateUiWithServerData: ${AAApp.appComponent.connect_now_vpn}")
        if (connectNowVpn.best_data) {
            binding.imgFast.setImageResource("Fast Server".getSaturnImage())
            binding.tvFast.text = "Fast Server"
        } else {
            binding.imgFast.setImageResource(connectNowVpn.TiORzr.getSaturnImage())
            binding.tvFast.text = "${connectNowVpn?.TiORzr}-${connectNowVpn.LMfccpX}"
        }
    }

    private fun setSkServerData(profile: Profile, bestData: SX): Profile {
        Log.e("TAG", "setSkServerData: ip=${bestData.CwDwOnOtuQ}")
        DataUtils.nowLoadIpData = bestData.CwDwOnOtuQ
        DataUtils.nowLoadCityData= bestData.LMfccpX
        AAApp.appComponent.connect_now_vpn = bestData.toJson()
        profile.name = bestData.TiORzr + "-" + bestData.LMfccpX
        profile.host = bestData.CwDwOnOtuQ
        profile.password = bestData.ivIQX
        profile.method = "chacha20-ietf-poly1305"
        profile.remotePort = bestData.TpG
        return profile
    }

    private fun getFastVpnData(): SX? {
        if (AAApp.appComponent.fast_now_vpn == null) {
            return null
        }
        val bestBean = AAApp.appComponent.fast_now_vpn?.let { parseSX(it) }
        return bestBean
    }

    fun showDueDialog(context: Context): Boolean {
        if (!NetUtils.isNetworkConnected(context)) {
            AlertDialog.Builder(context).apply {
                setTitle("WARN")
                setMessage("Network request timed out. Please make sure your network is connected")
                setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                setCancelable(false)
                show()
            }
            return true
        }
        return false
    }
}