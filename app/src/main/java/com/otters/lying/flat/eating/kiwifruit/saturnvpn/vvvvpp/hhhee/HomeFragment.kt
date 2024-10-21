package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.hhhee

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceDataStore
import com.github.shadowsocks.Core
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.preference.DataStore
import com.github.shadowsocks.preference.OnPreferenceDataStoreChangeListener
import com.github.shadowsocks.utils.Key
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseFragment
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.Data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.getIPInfo
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.showDueDialog
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentHomeBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils.log
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils.postPointData
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.AdDataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.CenterUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.point7
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.point8
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.saturn_dow_num
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.saturn_up_num
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.vpn_city
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.vpn_ip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    ShadowsocksConnection.Callback,
    OnPreferenceDataStoreChangeListener {
    val connection = ShadowsocksConnection(true)
    var jobStart: Job? = null
    var jobConnect: Job? = null
    var jobHome: Job? = null
    var nowClickState = "-1"
    private lateinit var requestPermissionForResultVPN: ActivityResultLauncher<Intent?>

    var showAd = false
    var jumpLaMore = false
    override val layoutId: Int
        get() = R.layout.fragment_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java


    override fun setupViews() {
        jumpLaMore = false
        AAApp.adTbaActivityName = this.javaClass.simpleName
        requestPermissionForResultVPN =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                requestPermissionForResult(it)
            }
        activity?.let { connection.connect(it, this) }
        DataStore.publicStore.registerChangeListener(this)
        log( "home ----init-----: ")
        viewModel.initializeServerData(binding) {
            activity?.let {
                jumpLaMore = true
                Log.e("More", "setupViews: 1")
                clickVpnConnect(it)
            }
        }
        binding.imgMenu.setOnClickListener {
            clickDisConnect(nextFun = {
                clickChange(nextFun = {
                    binding.dlHome.open()
                })
            })
        }
//        binding.viewGuideLottie.setOnClickListener { }
        binding.tvAppProxy.setOnClickListener {
            navigateTo(R.id.action_homeFragment_to_proxyFragment)
        }
        binding.tvServerList.setOnClickListener {
            navigateTo(R.id.action_homeFragment_to_moreFragment) {
                // 从 B 返回到 A 后执行的代码
                log( "Returned from B to A, executing callback...")
            }

        }
        binding.tvPrivacyPolicy.setOnClickListener {
            navigateTo(R.id.action_homeFragment_to_ppFragment)
        }
        binding.atvConnect.setOnClickListener {
            clickChange(nextFun = {
                activity?.let { it1 -> clickVpnConnect(it1) }
            })
        }
//        binding.lavConnect.setOnClickListener {
//            clickChange(nextFun = {
//                activity?.let { it1 -> clickVpnConnect(it1) }
//            })
//        }
        binding.imageView.setOnClickListener {
            clickChange(nextFun = {
                activity?.let { it1 -> clickVpnConnect(it1) }
            })
        }
        binding.linearLayout.setOnClickListener {
            clickDisConnect(nextFun = {
                clickChange(nextFun = {
                    clickToService()
                })
            })
        }
        binding.linNav.setOnClickListener { }
        getIPInfo()
        activity?.let { showDueDialog(it) }
        showTimeLi()
        showHomeAd()
        log( "setupViews: showHomeAd", )
    }


    override fun observeViewModel() {
        CenterUtils.isHaveServeData()
    }

    override fun customizeReturnKey() {
        backFun()
    }

    private fun isConnectGuo(): Boolean {
        return !(nowClickState == "0" && binding.laKs.isVisible)
    }

    private fun clickChange(nextFun: () -> Unit) {
        if (isConnectGuo()) {
            nextFun()
        } else {
            Toast.makeText(
                activity,
                "VPN is connecting. Please try again later.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun clickDisConnect(nextFun: () -> Unit) {
        if (nowClickState == "2" && binding.laKs.isVisible) {
            stopOperate()
        } else {
            nextFun()
        }
    }

    private fun stopOperate() {
        connection.bandwidthTimeout = 0
        jobStart?.cancel()
        jobStart = null
        jobConnect?.cancel()
        jobConnect = null
        if (AAApp.vvState) {
            Log.e("More", "setSsVpnState: =2====2")
            setTypeService(2)
        } else {
            Core.stopService()
            setTypeService(0)
        }
    }

    private fun clickToService() {
        clickChange {
            lifecycleScope.launch {
                if (CenterUtils.isHaveServeData()) {
                    navigateTo(R.id.action_homeFragment_to_moreFragment)
                } else {
                    binding.conLoadAd.isVisible = true
                    delay(1000)
                    binding.conLoadAd.isVisible = false
                    navigateTo(R.id.action_homeFragment_to_moreFragment)
                }
            }
        }
    }

    private fun clickVpnConnect(context: Context) {
        if (!AAApp.vvState) {
            postPointData("moo6")
        }
        setGuideView(false)
        if (activity?.let { viewModel.showDueDialog(it) } == true) {
            return
        }
        getIPInfo()
        if (showDueDialog(context)) {
            return
        }

        lifecycleScope.launch {
            if (CenterUtils.isHaveServeData()) {
                showVpnResult()
            } else {
                binding.conLoadAd.isVisible = true
                delay(1000)
                binding.conLoadAd.isVisible = false
            }
        }
    }

    private fun showVpnResult() {
        if (checkVPNPermission()) {
            activeVpnNext()
        } else {
            if (AAApp.appComponent.point7 != "1") {
                AAApp.appComponent.point7 = "1"
                postPointData("moo7")
            }
            VpnService.prepare(activity).let {
                requestPermissionForResultVPN.launch(it)
            }
        }
    }

    private fun activeVpnNext() {
        val rlData = AdDataUtils.raoliu()
        DataUtils.rl_state = if (rlData) {
            "1"
        } else {
            "0"
        }
        startVpn()
    }

    private fun requestPermissionForResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            if (AAApp.appComponent.point8 != "1") {
                AAApp.appComponent.point8 = "1"
                postPointData("moo8")
            }
            activeVpnNext()
        } else {
            Toast.makeText(
                activity,
                "Please give permission to continue to the next step",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkVPNPermission(): Boolean {
        VpnService.prepare(requireActivity()).let {
            return it == null
        }
    }

    private fun startVpn() {
        jobStart?.cancel()
        jobStart = null
        showAd = true
        nowClickState = if (AAApp.vvState) {
            "2"
        } else {
            "0"
        }
        Log.e("More", "setupViews: 2")
        jobStart = activity?.lifecycleScope?.launch {
            setTypeService(1)
            delay(2000)
            if (!isActive) {
                return@launch
            }
            if (AAApp.vvState) {
                showConnectAd(AdDataUtils.getConnectTime().second)
            } else {
                log( "vpn连接: =${nowClickState}")
                AAApp.appComponent.vpn_ip = DataUtils.nowLoadIpData
                AAApp.appComponent.vpn_city = DataUtils.nowLoadCityData
                Core.startService()
                postPointData("moo9")
            }
        }
    }

    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) {
        log( "stateChanged: ${state.name}")
        if (state.name == "Connected") {
            AAApp.vvState = true
            binding.linSpeed.isVisible = true
            if (showAd) {
                showAd = false
                log( "vpn连接成功")
                showConnectAd(AdDataUtils.getConnectTime().first)
                AAApp.adManagerEndInt?.loadAd(AdDataUtils.end_type)
                AAApp.adManagerResultNav?.loadAd(AdDataUtils.result_type)
            }
            TTTDDUtils.moo10()
        }
        if (state.name == "Stopping") {
            AAApp.vvState = false
        }
        if (state.name == "Stopped") {
            AAApp.vvState = false
            binding.linSpeed.isVisible = false
            log( "ss vpn断开成功")
            setTypeService(0)
        }
    }

    override fun onServiceConnected(service: IShadowsocksService) {
        if (!jumpLaMore) {
            log( "onServiceConnected: ${service.state}")
            val state = BaseService.State.values()[service.state]
            log( "ss-初始化: ${state.name}")
            if (state.name == "Connected") {
                binding.linSpeed.isVisible = true
                setSsVpnState(true)
            }
            if (state.name == "Stopped") {
                binding.linSpeed.isVisible = false
                setSsVpnState(false)
            }
            setGuideView(!AAApp.vvState)
        }
    }


    override fun onStart() {
        super.onStart()
        connection.bandwidthTimeout = 500
    }

    override fun onStop() {
        super.onStop()
        connection.bandwidthTimeout = 0
        connection.disconnect(requireActivity())

    }


    override fun onDestroy() {
        super.onDestroy()
        DataStore.publicStore.unregisterChangeListener(this)
        connection.disconnect(requireActivity())
    }

    private fun setSsVpnState(canStop: Boolean) {
        AAApp.vvState = canStop
        if (AAApp.vvState) {
            setTypeService(2)
        } else {
            setTypeService(0)
        }
    }

    override fun onPreferenceDataStoreChanged(store: PreferenceDataStore, key: String) {
        when (key) {
            Key.serviceMode -> {
                connection.disconnect(requireActivity())
                connection.connect(requireActivity(), this)
            }
        }
    }

    private fun setTypeService(
        stateInt: Int
    ) {
        Log.e("More", "setupViews: =====${stateInt}")
        if (stateInt == 0) {
            AAApp.globalTimer?.reset()
            binding.atvConnect.text = "Disconnected"
            binding.laKs.isVisible = false
            binding.imageView.setImageResource(R.drawable.icon_start)
        }

        if (stateInt == 1) {
            binding.laKs.isVisible = true

            if (nowClickState == "2") {
                binding.atvConnect.text = "Disconnecting"
            } else {
                binding.atvConnect.text = "Connecting"
            }
        }

        if (stateInt == 2) {
            AAApp.globalTimer?.start()
            binding.atvConnect.text = "Connected"
            binding.laKs.isVisible = false
            binding.imageView.setImageResource(R.drawable.icon_center)
        }
    }

    private fun setGuideView(show: Boolean) {
        if (AAApp.cloneGuide) return
        AAApp.cloneGuide = !show
//        binding.viewGuideLottie.isVisible = show
//        binding.lavConnect.isVisible = show
    }

    private fun backFun() {
        if (binding.conLoadAd.isVisible) {
            return
        }
//        if (binding.viewGuideLottie.isVisible) {
//            setGuideView(false)
//            return
//        }
        if (nowClickState == "2" && binding.laKs.isVisible) {
            stopOperate()
            return
        }

        clickChange(nextFun = {
            requireActivity().finish()
        })
    }

    private fun showConnectAd(connectTimeData: Int) {
        jobConnect?.cancel()
        jobConnect = null
        jobConnect = activity?.lifecycleScope?.launch(Dispatchers.Main) {
            if (AAApp.adManagerConnect?.canShowAd(AdDataUtils.cont_type) == AdDataUtils.ad_jump_over) {
                showFinishAd()
                return@launch
            }
            AAApp.adManagerConnect?.loadAd(AdDataUtils.cont_type)
            val startTime = System.currentTimeMillis()
            var elapsedTime: Long
            try {
                while (isActive) {
                    elapsedTime = System.currentTimeMillis() - startTime
                    if (elapsedTime >= (connectTimeData * 1000)) {
                        log( "连接超时")
                        showFinishAd()
                        break
                    }

                    if (AAApp.adManagerConnect?.canShowAd(AdDataUtils.cont_type) == AdDataUtils.ad_show) {
                        binding.conLoadAd.isVisible = true
                        delay(1000)
                        binding.conLoadAd.isVisible = false
                        AAApp.adManagerConnect?.showAd(
                            AdDataUtils.cont_type,
                            requireActivity(),
                            this@HomeFragment
                        ) {
                            showFinishAd()
                        }
                        break
                    }
                    delay(500L)
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun showFinishAd() {
        log( "vpn连接-showFinishAd: $nowClickState")
        if (nowClickState == "0") {
            connectFinish()
            AAApp.adManagerConnect?.loadAd(AdDataUtils.cont_type)
            AAApp.adManagerHomeNav?.loadAd(AdDataUtils.home_type)
        }
        if (nowClickState == "2") {
            disConnectFinish()
        }
    }

    private fun connectFinish() {
        setGuideView(false)
        Log.e("More", "setSsVpnState: =2====3")
        setTypeService(2)
        pageToRePage()
    }

    private fun disConnectFinish() {
        log( "关闭广告断开vpn: ")
        Core.stopService()
        setTypeService(0)
        pageToRePage()
        postPointData("moo13")
    }

    private fun pageToRePage() {
        activity?.lifecycleScope?.launch {
            delay(300L)
            if (activity?.lifecycle?.currentState != Lifecycle.State.RESUMED) {
                return@launch
            }
            navigateTo(R.id.action_homeFragment_to_endFragment)
        }
    }

    private fun showTimeLi() {
        GlobalScope.launch(Dispatchers.Main) {
            while (isActive) {
                if (AAApp.vvState) {
                    binding.linSpeed.isVisible = true
                    binding.tvDate.text = AAApp.globalTimer?.getFormattedTime()
                    binding.tvDown.text = saturn_dow_num
                    binding.tvUp.text = saturn_up_num
                } else {
                    binding.linSpeed.isVisible = false
                    binding.tvDate.text = "00:00:00"
                    binding.tvDown.text = "0 b/s"
                    binding.tvUp.text = "0 b/s"
                }
                delay(500)
            }
        }
    }

    private fun showHomeAd() {
        val blackData = AdDataUtils.getAdBlackData()
        if (blackData) {
            log( "黑名单屏蔽home_saturn广告")
            binding.adLayout.isVisible = false
            return
        } else if (!AAApp.vvState) {
            binding.adLayout.isVisible = true
            binding.adLayoutAdmob.isVisible = false
            binding.imgOcAd.isVisible = true
            return
        } else {
            binding.adLayout.isVisible = true
            binding.adLayoutAdmob.isVisible = true
            binding.imgOcAd.isVisible = true
        }
        jobHome?.cancel()
        jobHome = null
        jobHome = lifecycleScope.launch {
            delay(300)
            while (isActive) {
                if (AAApp.adManagerHomeNav?.canShowAd(AdDataUtils.home_type) == AdDataUtils.ad_show) {
                    AAApp.adManagerHomeNav?.showAd(
                        AdDataUtils.home_type,
                        requireActivity(),
                        this@HomeFragment
                    ) {
                    }
                    jobHome?.cancel()
                    jobHome = null
                    break
                }
                delay(500L)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(300)
            val state = lifecycle.currentState == Lifecycle.State.RESUMED
            if (state) {
                postPointData("moo2")
            }
        }
    }
}