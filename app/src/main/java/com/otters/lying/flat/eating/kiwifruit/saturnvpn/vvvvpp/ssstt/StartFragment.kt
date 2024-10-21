package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.ssstt

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.BuildConfig
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp.Companion.adManager
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseFragment
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentStartBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils.log
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.AdDataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.AdDataUtils.getLjData
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.cmpData
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_ad_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_ad_key
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_pingbi_data
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.onlien_pingbi_key
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StartFragment : BaseFragment<FragmentStartBinding, StartViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_start

    override val viewModelClass: Class<StartViewModel>
        get() = StartViewModel::class.java

    private var jobStart: Job? = null

    override fun setupViews() {
        AAApp.adTbaActivityName = this.javaClass.simpleName
        lifecycleScope.launch(Dispatchers.IO) {
            NetUtils.getVpnNetData()
            TTTDDUtils.postSessionData()
        }
        updateUserOpinions()
        getFirebaseDataFun {
            wODFun()
            adManager?.loadAd(AdDataUtils.open_type)
            adManager?.loadAd(AdDataUtils.cont_type)
            adManager?.loadAd(AdDataUtils.home_type)
        }
        log( "onCreate: Apppppppppp-start=${AAApp.vvState}", )
    }

    override fun observeViewModel() {
        viewModel.liveHomeData.observe(viewLifecycleOwner) {
            if (it) {
                navigateTo(R.id.action_startFragment_to_homeFragment)
            }
        }
    }

    override fun customizeReturnKey() {

    }


    fun getFirebaseDataFun(loadAdFun: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        var isCa = false
        var attemptCount = 0
        if (!BuildConfig.DEBUG) {
            val auth = Firebase.remoteConfig
            auth.fetchAndActivate().addOnSuccessListener {
                onlien_ad_data = auth.getString(onlien_ad_key)
                onlien_pingbi_data = auth.getString(onlien_pingbi_key)
                isCa = true
                initFaceBook()
            }
        }
        val checkConditionAndPreloadAd = object : Runnable {
            override fun run() {
                if (isCa) {
                    loadAdFun()
                } else {
                    attemptCount++
                    log( "检测远程数据中。。。")
                    if (attemptCount < 8) { // 4000ms / 500ms = 8 attempts
                        handler.postDelayed(this, 500)
                    } else {
                        log( "检测远程数据超时。。。")
                        loadAdFun()
                        initFaceBook()
                    }
                }
            }
        }
        handler.postDelayed(checkConditionAndPreloadAd, 500)
    }
    private fun initFaceBook() {
        val bean = getLjData()
        // TODO fb id 1
        if(bean.mosd.isBlank()){return}
        log( "initFaceBook: ${bean.mosd}")
        FacebookSdk.setApplicationId(bean.mosd)
        FacebookSdk.sdkInitialize(AAApp.appComponent)
        AppEventsLogger.activateApp(AAApp.thisApplication)
    }

    private fun openOpenAd2(jumpFun: () -> Unit) {
        if (adManager?.canShowAd(AdDataUtils.open_type) == AdDataUtils.ad_jump_over) {
            jumpFun()
            return
        }
        var adShown = false
        var attemptCount = 0
        val handler = Handler(Looper.getMainLooper())
        val checkConditionAndPreloadAd = object : Runnable {
            override fun run() {
                log( "等待OPEN广告中。。。${adShown} ")
                if (adShown) return
                attemptCount++
                if (attemptCount < 20) {
                    handler.postDelayed(this, 500)
                } else {
                    adShown = true
                    log( "OPEN广告超时。。。 ")
                    jumpFun()
                }
                if (adManager?.canShowAd(AdDataUtils.open_type) == AdDataUtils.ad_show) {
                    adShown = true
                    log( "准备OPEN广告中。。。${adShown} ")
                    adManager?.showAd(
                        AdDataUtils.open_type,
                        requireActivity(),
                        this@StartFragment
                    ) {
                        jumpFun()
                    }
                }
            }
        }
        handler.postDelayed(checkConditionAndPreloadAd, 500)
    }
    private fun openOpenAd(jumpFun: () -> Unit) {
        if (adManager?.canShowAd(AdDataUtils.open_type) == AdDataUtils.ad_jump_over) {
            jumpFun()
            return
        }
        var adShown = false
        var attemptCount = 0
        val handler = Handler(Looper.getMainLooper())
        val checkConditionAndPreloadAd = object : Runnable {
            override fun run() {
                log( "等待OPEN广告中。。。${adShown} ")
                if (adShown || !isAdded) return // 确保 Fragment 已附加
                attemptCount++
                if (attemptCount < 10) {
                    handler.postDelayed(this, 500)
                } else {
                    adShown = true
                    log( "OPEN广告超时。。。 ")
                    jumpFun()
                }
                if (adManager?.canShowAd(AdDataUtils.open_type) == AdDataUtils.ad_show) {
                    adShown = true
                    log( "准备OPEN广告中。。。${adShown} ")
                    activity?.let { activity ->
                        adManager?.showAd(AdDataUtils.open_type, activity, this@StartFragment) {
                            jumpFun()
                        }
                    }
                }
            }
        }
        handler.postDelayed(checkConditionAndPreloadAd, 500)
    }


    private var isNavigated = false
    fun ffffffDDD() {
        if (!isNavigated) {
            isNavigated = true
            openOpenAd {
                if (isAdded) {
                    try {
                        navigateTo(R.id.action_startFragment_to_homeFragment)
                    } catch (e: Exception) {
                        Log.e("Navigation Error", "Error navigating to homeFragment", e)
                    }
                }
                isNavigated = false
            }
        }
    }

    private fun wODFun() {
        lifecycleScope.launch {
            while (isActive) {
                if (AAApp.appComponent.cmpData == "1") {
                    ffffffDDD()
                    cancel()
                }
                delay(500)
            }
        }
    }

    private fun updateUserOpinions() {
        if (AAApp.appComponent.cmpData == "1") {
            return
        }
        val debugSettings =
            ConsentDebugSettings.Builder(requireActivity())
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId("D78A835D7D887BF1B02B84F27479EC60")
                .build()
        val params = ConsentRequestParameters
            .Builder()
            .setConsentDebugSettings(debugSettings)
            .build()
        val consentInformation: ConsentInformation =
            UserMessagingPlatform.getConsentInformation(requireActivity())
        consentInformation.requestConsentInfoUpdate(
            requireActivity(),
            params, {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(requireActivity()) {
                    if (consentInformation.canRequestAds()) {
                        AAApp.appComponent.cmpData = "1"
                    }
                }
            },
            {
                AAApp.appComponent.cmpData = "1"
            }
        )
    }
}