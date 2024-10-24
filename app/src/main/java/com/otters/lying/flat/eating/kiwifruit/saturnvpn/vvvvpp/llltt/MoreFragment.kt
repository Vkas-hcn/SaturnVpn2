package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.llltt

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseFragment
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.SX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseSX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentMoreBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils.log
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.AdDataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.CenterUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.connect_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.fast_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.online_vpn_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MoreFragment : BaseFragment<FragmentMoreBinding, MoreViewModel>(){
    override val layoutId: Int
        get() = R.layout.fragment_more

    override val viewModelClass: Class<MoreViewModel>
        get() = MoreViewModel::class.java

    private var moreList:MutableList<SX>?=null
    private var moreAdapter: MoreListAdapter?=null
    override fun setupViews() {
        AAApp.adTbaActivityName = this.javaClass.simpleName
        binding.imgBack.setOnClickListener {
            customizeReturnKey()
        }
        initAdapter()
        initFastService()
        AAApp.adManagerListInt?.loadAd(AdDataUtils.list_type)
        TTTDDUtils.postPointData("moo18")
    }

    override fun observeViewModel() {
    }

    override fun customizeReturnKey() {
        backFun()
    }

    private fun initFastService(){
        var isSele = false
        if (AAApp.appComponent.connect_now_vpn.isNotBlank()) {
            val connectNowVpn = AAApp.appComponent.connect_now_vpn.let { parseSX(it) }
            if(connectNowVpn.best_data && AAApp.vvState){
                isSele =true
                binding.linearLayout.background =activity?.getDrawable(R.drawable.bg_service_main2)
            } else {
                binding.linearLayout.background =activity?.getDrawable(R.drawable.bg_service_main)
            }
        }
           binding.linearLayout.setOnClickListener {
               AAApp.appComponent.online_vpn_data?.let { it1 -> NetUtils.getFastVpnData(it1) }
               val bestBean = AAApp.appComponent.fast_now_vpn?.let { parseSX(it) }

               if (bestBean != null && !isSele) {
                   AAApp.jumpToHomeType = "1"
                   moreAdapter?.chooeServices(bestBean)
               }
           }
    }

    private fun initAdapter(){
        moreList =  CenterUtils.getAllVpnListData()
        if(moreList?.isEmpty() == true){
            binding.rvVpn.isVisible = false
            binding.tvNoData.isVisible = true
        }else{
            binding.rvVpn.isVisible = true
            binding.tvNoData.isVisible = false
        }
        binding.rvVpn.layoutManager = LinearLayoutManager(activity)
        moreAdapter = moreList?.let { MoreListAdapter(it,requireActivity(),this) }
        binding.rvVpn.adapter = moreAdapter
    }


    private fun backFun() {
        showBackAd {
            log( "backFun:more ", )
            navigateToBack()
        }
        TTTDDUtils.postPointData("moo20")
    }

    private fun showBackAd(nextFun: () -> Unit) {
        activity?.lifecycleScope?.launch(Dispatchers.Main) {
            if (AAApp.adManagerListInt?.canShowAd(AdDataUtils.list_type) == AdDataUtils.ad_jump_over) {
                nextFun()
                return@launch
            }
            AAApp.adManagerListInt?.loadAd(AdDataUtils.list_type)
            val startTime = System.currentTimeMillis()
            var elapsedTime: Long
            binding.conLoadAd.isVisible = true
            try {
                while (isActive) {
                    elapsedTime = System.currentTimeMillis() - startTime
                    if (elapsedTime >= 4000L) {
                        log( "连接超时")
                        nextFun()
                        binding.conLoadAd.isVisible = false
                        break
                    }

                    if (AAApp.adManagerListInt?.canShowAd(AdDataUtils.list_type) == AdDataUtils.ad_show) {
                        AAApp.adManagerListInt?.showAd(AdDataUtils.list_type, requireActivity(), this@MoreFragment) {
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
}