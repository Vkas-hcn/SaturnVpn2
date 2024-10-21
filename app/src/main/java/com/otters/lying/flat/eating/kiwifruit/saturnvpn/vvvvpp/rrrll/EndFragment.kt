package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.rrrll

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp.Companion.adManager
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseFragment
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseSX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentEndBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.AdDataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.click_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.connect_now_vpn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class EndFragment : BaseFragment<FragmentEndBinding, EndViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_end

    override val viewModelClass: Class<EndViewModel>
        get() = EndViewModel::class.java

    var jobResult: Job? = null

    override fun setupViews() {
        AAApp.adTbaActivityName = this.javaClass.simpleName
        showTimeLi()
        binding.iconBack.setOnClickListener {
            backFun()
        }
        if (AAApp.vvState) {
            binding.tvState.text = "Connection Successful"
            binding.imageView.setImageResource(R.drawable.icon_c_e)
        } else {
            binding.tvState.text = "DisConnection Successful"
            binding.imageView.setImageResource(R.drawable.icon_start)
        }
        showVpnInfo()
        showEndAd()
        TTTDDUtils.moo12()
    }

    private fun showVpnInfo() {
        if (AAApp.appComponent.connect_now_vpn.isBlank()) {
            return
        }
        val connectNowVpn = AAApp.appComponent.connect_now_vpn.let { parseSX(it) }
        Log.e("TAG", "updateUiWithServerData-end: ${AAApp.appComponent.connect_now_vpn}")
        if (connectNowVpn.best_data == true) {
            binding.tvName.text = "Fast Server"
        } else {
            binding.tvName.text = "${connectNowVpn?.TiORzr} - ${connectNowVpn?.LMfccpX}"
        }
    }


    override fun observeViewModel() {
    }

    override fun customizeReturnKey() {
        backFun()
    }

    private fun backFun() {
        if (AAApp.appComponent.click_now_vpn.isNotBlank()) {
            AAApp.appComponent.connect_now_vpn= AAApp.appComponent.click_now_vpn
        }
        AAApp.appComponent.click_now_vpn = ""
        showBackAd {
            findNavController().navigateUp()
        }
        TTTDDUtils.postPointData("moo21")
    }

    private fun showBackAd(nextFun: () -> Unit) {
        activity?.lifecycleScope?.launch(Dispatchers.Main) {
            if (adManager?.canShowAd(AdDataUtils.end_type) == AdDataUtils.ad_jump_over) {
                nextFun()
                return@launch
            }
            adManager?.loadAd(AdDataUtils.end_type)
            val startTime = System.currentTimeMillis()
            var elapsedTime: Long
            binding.conLoadAd.isVisible = true
            try {
                while (isActive) {
                    elapsedTime = System.currentTimeMillis() - startTime
                    if (elapsedTime >= 4000L) {
                        Log.e("TAG", "连接超时")
                        nextFun()
                        binding.conLoadAd.isVisible = false
                        break
                    }

                    if (adManager?.canShowAd(AdDataUtils.end_type) == AdDataUtils.ad_show) {
                        adManager?.showAd(AdDataUtils.end_type, requireActivity(), this@EndFragment) {
                            nextFun()
                            binding.conLoadAd.isVisible = false
                        }
                        break
                    }
                    delay(500L)
                }
            } catch (e: Exception) {
                nextFun()
                binding.conLoadAd.isVisible = false
            }
        }
    }

    private fun showTimeLi() {
        if (!AAApp.vvState) return
        GlobalScope.launch(Dispatchers.Main) {
            while (isActive) {
                if (AAApp.vvState) {
                    binding.tvDate.text = AAApp.globalTimer?.getFormattedTime()
                    binding.tvDown.text = DataUtils.saturn_dow_num
                    binding.tvUp.text = DataUtils.saturn_up_num
                }
                delay(500)
            }
        }
    }

    private fun showEndAd() {
        jobResult?.cancel()
        jobResult = null
        if (adManager?.canShowAd(AdDataUtils.result_type) == AdDataUtils.ad_jump_over) {
            binding.imgOc.isVisible = true
            binding.adLayoutAdmob.isVisible = false
        }
        adManager?.loadAd(AdDataUtils.end_type)
        jobResult = lifecycleScope.launch {
            delay(300)
            while (isActive) {
                if (adManager?.canShowAd(AdDataUtils.result_type) == AdDataUtils.ad_show) {
                    adManager?.showAd(
                        AdDataUtils.result_type,
                        requireActivity(),
                        this@EndFragment
                    ) {}
                    jobResult?.cancel()
                    jobResult = null
                    break
                }
                delay(500L)
            }
        }
    }
}