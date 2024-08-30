package com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss


import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.appopen.AppOpenAd
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.AdEasy
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.VpnAdBean
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.ad_c_num
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.ad_load_date
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.ad_s_num
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.point16
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.hhhee.HomeFragment
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.rrrll.EndFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import java.util.Date
import java.text.SimpleDateFormat

class AdManager(private val application: Application) {
    private val adCache = mutableMapOf<String, Any>()
    private val adLoadInProgress = mutableMapOf<String, Boolean>()
    private val adTimestamps = mutableMapOf<String, Long>()
    private var adAllData: VpnAdBean = AdDataUtils.getAdListData()
    private var adDataOpen: AdEasy? = null
    private var adDataHome: AdEasy? = null
    private var adDataResult: AdEasy? = null
    private var adDataCont: AdEasy? = null
    private var adDataList: AdEasy? = null
    private var adDataBa: AdEasy? = null

    init {
        MobileAds.initialize(application) {
            Log.d("AdManager", "AdMob initialized")
        }
        isAppOpenSameDayBa()
    }

    private fun canRequestAd(adType: String): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastLoadTime = adTimestamps[adType] ?: 0L
        return currentTime - lastLoadTime > 3600 * 1000 // 1 hour
    }

    fun loadAd(adType: String) {
        adAllData = AdDataUtils.getAdListData()
        if (adLoadInProgress[adType] == true) return
        adLoadInProgress[adType] = true
        val adList = when (adType) {
            AdDataUtils.open_type -> adAllData.far
            AdDataUtils.home_type -> adAllData.eek
            AdDataUtils.result_type -> adAllData.mug
            AdDataUtils.cont_type -> adAllData.yum
            AdDataUtils.list_type -> adAllData.buy
            AdDataUtils.end_type -> adAllData.gab

            else -> emptyList()
        }.sortedByDescending { it.saturn_kk }

        loadAdFromList(adType, adList, 0)
    }

    private fun loadAdFromList(adType: String, adList: List<AdEasy>, index: Int) {
        if (index >= adList.size) {
            adLoadInProgress[adType] = false
            return
        }
        if (adCache.containsKey(adType) && !canRequestAd(adType)) {
            Log.e("TAG", "已有$adType 广告，不在加载: ")
            return
        }
        if (!canLoadAd()) {
            Log.e("TAG", "广告超限，不在加载")
            return
        }
        val blackData = AdDataUtils.getAdBlackData()

        if (blackData && (adType == AdDataUtils.home_type || adType == AdDataUtils.cont_type || adType == AdDataUtils.list_type || adType == AdDataUtils.end_type)) {
            Log.e("TAG", "黑名单屏蔽$adType 广告，不在加载: ")
            return
        }

        val adEasy = adList[index]
        Log.e("TAG", "$adType 广告，开始加载: id=${adEasy.saturn_dd};we=${adEasy.saturn_kk}")
        TTTDDUtils.moo14(adType)
        when (adEasy.saturn_tt) {
            "open" -> loadOpenAd(adType, adEasy, adList, index)
            "native" -> loadNativeAd(adType, adEasy, adList, index)
            "interstitial" -> loadInterstitialAd(adType, adEasy, adList, index)
        }
    }

    private fun loadOpenAd(adType: String, adEasy: AdEasy, adList: List<AdEasy>, index: Int) {
        adDataOpen = TTTDDUtils.beforeLoadLink(adEasy)
        AppOpenAd.load(application, adEasy.saturn_dd, AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    Timber.tag("TAG").e("$adType+广告加载成功")
                    adCache[adType] = ad
                    adTimestamps[adType] = System.currentTimeMillis()
                    adLoadInProgress[adType] = false
                    ad.setOnPaidEventListener { adValue ->
                        adValue.let {
                            TTTDDUtils.postAdData(
                                it,
                                ad.responseInfo,
                                adDataOpen!!,
                                "ope_easy"
                            )
                        }
                    }
                    TTTDDUtils.moo15(adType)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("TAG", "${adType}广告加载失败=${loadAdError}")
                    loadAdFromList(adType, adList, index + 1)
                    TTTDDUtils.moo17(adType,loadAdError.message)
                }
            })
    }

    private fun loadNativeAd(adType: String, adEasy: AdEasy, adList: List<AdEasy>, index: Int) {
        if (adType == AdDataUtils.home_type) {
            adDataHome = TTTDDUtils.beforeLoadLink(adEasy)
        } else {
            adDataResult = TTTDDUtils.beforeLoadLink(adEasy)
        }
        val builder = NativeAdOptions.Builder()
        val adLoader = com.google.android.gms.ads.AdLoader.Builder(application, adEasy.saturn_dd)
            .forNativeAd { ad: NativeAd ->
                Timber.tag("TAG").e("${adType}广告加载成功")
                adCache[adType] = ad
                adTimestamps[adType] = System.currentTimeMillis()
                adLoadInProgress[adType] = false
                getNatData(ad, adType)
                TTTDDUtils.moo15(adType)
            }
            .withNativeAdOptions(builder.build())
            .withAdListener(object : com.google.android.gms.ads.AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("TAG", "${adType}广告加载失败=${loadAdError}")
                    loadAdFromList(adType, adList, index + 1)
                    TTTDDUtils.moo17(adType,loadAdError.message)
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.e("TAG", "点击原生广告")
                    setCLickNumFun()
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun loadInterstitialAd(
        adType: String,
        adEasy: AdEasy,
        adList: List<AdEasy>,
        index: Int
    ) {
        when (adType) {
            AdDataUtils.cont_type -> {
                adDataCont = TTTDDUtils.beforeLoadLink(adEasy)
            }

            AdDataUtils.list_type -> {
                adDataList = TTTDDUtils.beforeLoadLink(adEasy)
            }

            else -> {
                adDataBa = TTTDDUtils.beforeLoadLink(adEasy)
            }
        }
        InterstitialAd.load(application, adEasy.saturn_dd, AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Log.e("TAG", "${adType}广告加载成功")

                    adCache[adType] = ad
                    adTimestamps[adType] = System.currentTimeMillis()
                    adLoadInProgress[adType] = false
                    getIntData(ad, adType)
                    TTTDDUtils.moo15(adType)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("TAG", "${adType}广告加载失败=${loadAdError}")
                    loadAdFromList(adType, adList, index + 1)
                    TTTDDUtils.moo17(adType,loadAdError.message)
                }
            })
    }

    fun qcAd(adType: String) {
        adLoadInProgress.remove(adType)
        adCache.remove(adType)
    }

    fun showAd(
        adType: String, activity: FragmentActivity, fragment: Fragment, nextFun: () -> Unit
    ) {
        if (adCache.containsKey(adType) && isAppInForeground(activity)) {
            when (val ad = adCache[adType]) {
                is AppOpenAd -> {
                    ad.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                if (isAppInForeground(activity)) {
                                    nextFun()
                                }
                                qcAd(adType)
                            }

                            override fun onAdShowedFullScreenContent() {
                            }

                            override fun onAdClicked() {
                                setCLickNumFun()
                            }
                        }
                    ad.show(activity)
                    setShowNumFun()
                    Log.e("TAG", "展示-${adType}广告: ")
                    adCache.remove(adType)
                    adDataOpen = TTTDDUtils.afterLoadLink(adDataOpen!!)
                }

                is NativeAd -> {
                    if (adType == AdDataUtils.home_type) {
                        setDisplayHomeNativeAdFlash(ad, fragment as HomeFragment)
                    } else {
                        setDisplayEndNativeAdFlash(ad, fragment as EndFragment)
                    }
                }

                is InterstitialAd -> {
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            Log.e("TAG", "关闭-${adType}广告: ")
                            qcAd(adType)
                            if (adType == AdDataUtils.cont_type) {
                                loadAd(adType)
                            }
                            if (isAppInForeground(activity)) {
                                nextFun()
                            }
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            setCLickNumFun()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                            adCache.remove(adType)
                        }

                        override fun onAdShowedFullScreenContent() {
                        }
                    }
                    ad.show(activity)
                    setShowNumFun()
                    Log.e("TAG", "展示-${adType}广告: ")
                    adCache.remove(adType)
                    when (adType) {
                        AdDataUtils.cont_type -> {
                            adDataCont = TTTDDUtils.afterLoadLink(adDataCont!!)
                        }

                        AdDataUtils.list_type -> {
                            adDataList = TTTDDUtils.afterLoadLink(adDataList!!)
                        }

                        else -> {
                            adDataBa = TTTDDUtils.afterLoadLink(adDataBa!!)
                        }
                    }
                }
            }
        }
    }

    fun canShowAd(adType: String): String {
        val ad = adCache[adType]
        val blackData = AdDataUtils.getAdBlackData()

        if (blackData && (adType == AdDataUtils.home_type || adType == AdDataUtils.cont_type || adType == AdDataUtils.list_type || adType == AdDataUtils.end_type)) {
            return AdDataUtils.ad_jump_over
        }

        if (ad == null && !canLoadAd()) {
            if (AAApp.appComponent.point16 != "1") {
                val type = if (adAllData.saturn_esc <= ad_s_num) {
                    "show"
                } else {
                    "click"
                }
                TTTDDUtils.postPointData(
                    "moo16",
                    "seru",
                    type,
                )
                AAApp.appComponent.point16 = "1"
            }
            return AdDataUtils.ad_jump_over
        }
        if (ad == null && canLoadAd()) {
            return AdDataUtils.ad_wait
        }
        if (ad != null && canLoadAd()) {
            return AdDataUtils.ad_show
        }
        return AdDataUtils.ad_show
    }

    fun getAdDataState(adType: String): Boolean {
        return adCache[adType] != null
    }

    private fun canLoadAd(): Boolean {
        isAppOpenSameDayBa()
        val adOpenNum = adAllData.saturn_esc
        val adClickNum = adAllData.saturn_kfv
        val currentOpenCount = ad_s_num
        val currentClickCount = ad_c_num
        return currentOpenCount < adOpenNum && currentClickCount < adClickNum
    }

    private fun isAppOpenSameDayBa() {
        if (ad_load_date.isBlank()) {
            ad_load_date = formatDateNow()
        } else {
            if (dateAfterDate(ad_load_date, formatDateNow())) {
                ad_load_date = formatDateNow()
                Log.e("TAG", "超限-qingling:")
                ad_s_num = 0
                ad_c_num = 0
            }
        }
    }

    fun dateAfterDate(startTime: String?, endTime: String?): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
            val startDate: Date = format.parse(startTime)
            val endDate: Date = format.parse(endTime)
            val start: Long = startDate.getTime()
            val end: Long = endDate.getTime()
            if (end > start) {
                return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    fun formatDateNow(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return simpleDateFormat.format(date)
    }

    fun setCLickNumFun() {
        ad_c_num += 1
    }

    private fun setShowNumFun() {
        ad_s_num += 1
    }

    fun isAppInForeground(activity: FragmentActivity): Boolean {
        val activityManager =
            activity.getSystemService(Activity.ACTIVITY_SERVICE) as android.app.ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses ?: return false
        for (processInfo in runningAppProcesses) {
            if (processInfo.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && processInfo.processName == activity.packageName) {
                return true
            }
        }
        return false
    }

    private fun setDisplayHomeNativeAdFlash(ad: NativeAd, activity: HomeFragment) {
        activity.lifecycleScope.launch(Dispatchers.Main) {
            ad.let { adData ->
                val state = activity.lifecycle.currentState == Lifecycle.State.RESUMED
                Log.e("TAG", "setDisplayHomeNativeAdFlash: ${state}")
                if (state) {
                    val adView = activity.layoutInflater.inflate(
                        R.layout.layout_main,
                        null
                    ) as NativeAdView
                    populateNativeAdView(adData, adView)
                    activity.binding.adLayoutAdmob.apply {
                        removeAllViews()
                        addView(adView)
                    }
                    activity.binding.imgOcAd.isVisible = false
                    activity.binding.adLayoutAdmob.isVisible = true
                    setShowNumFun()
                    Log.e("TAG", "展示-home_saturn广告: ")
                    adCache.remove(AdDataUtils.home_type)
                    adLoadInProgress[AdDataUtils.home_type] = false
                    adDataHome = TTTDDUtils.afterLoadLink(adDataHome!!)
                }
            }
        }
    }

    private fun setDisplayEndNativeAdFlash(ad: NativeAd, activity: EndFragment) {
        activity.lifecycleScope.launch(Dispatchers.Main) {
            ad.let { adData ->
                val state = activity.lifecycle.currentState == Lifecycle.State.RESUMED

                if (state) {
                    activity.binding.imgOc.isVisible = true

                    val adView = activity.layoutInflater.inflate(
                        R.layout.layout_end,
                        null
                    ) as NativeAdView
                    populateNativeAdView(adData, adView)
                    activity.binding.adLayoutAdmob.apply {
                        removeAllViews()
                        addView(adView)
                    }
                    activity.binding.imgOc.isVisible = false
                    activity.binding.adLayoutAdmob.isVisible = true
                    setShowNumFun()
                    Log.e("TAG", "展示-resu_saturn广告: ")
                    adCache.remove(AdDataUtils.result_type)
                    adLoadInProgress[AdDataUtils.result_type] = false
                    adDataResult = TTTDDUtils.afterLoadLink(adDataResult!!)
                }
            }
        }
    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.mediaView = adView.findViewById(R.id.ad_media)

        nativeAd.mediaContent?.let {
            adView.mediaView?.apply { setImageScaleType(ImageView.ScaleType.CENTER_CROP) }?.mediaContent =
                it
        }
        adView.mediaView?.clipToOutline = true
        if (nativeAd.body == null) {
            adView.bodyView?.visibility = View.INVISIBLE
        } else {
            adView.bodyView?.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView?.visibility = View.INVISIBLE
        } else {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as TextView).text = nativeAd.callToAction
        }
        if (nativeAd.headline == null) {
            adView.headlineView?.visibility = View.INVISIBLE
        } else {
            adView.headlineView?.visibility = View.VISIBLE
            (adView.headlineView as TextView).text = nativeAd.headline
        }

        if (nativeAd.icon == null) {
            adView.iconView?.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon?.drawable
            )
            adView.iconView?.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)
    }

    fun getNatData(ad: NativeAd, adType: String) {
        if (adType == AdDataUtils.home_type) {
            ad.setOnPaidEventListener { adValue ->
                Log.e("TAG", "原生广告 -${adType}，开始上报: ")
                ad.responseInfo?.let {
                    TTTDDUtils.postAdData(
                        adValue,
                        it,
                        adDataHome!!,
                        adType
                    )
                }
            }
            loadAd(AdDataUtils.home_type)
        } else {
            ad.setOnPaidEventListener { adValue ->
                Log.e("TAG", "原生广告 -${adType}，开始上报: ")
                ad.responseInfo?.let {
                    TTTDDUtils.postAdData(
                        adValue,
                        it,
                        adDataResult!!,
                        adType
                    )
                }
            }
            loadAd(AdDataUtils.result_type)
        }
    }

    fun getIntData(ad: InterstitialAd, adType: String) {
        val bean = when (adType) {
            AdDataUtils.cont_type -> {
                adDataCont!!
            }

            AdDataUtils.list_type -> {
                adDataList!!
            }

            else -> {
                adDataBa!!
            }
        }
        ad.setOnPaidEventListener { adValue ->
            Log.e("TAG", "插屏广告 -${adType}，开始上报: ")
            TTTDDUtils.postAdData(
                adValue,
                ad.responseInfo,
                bean,
                adType
            )
        }
    }
}
