package com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.github.shadowsocks.Core
import com.google.android.gms.ads.AdActivity
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.getIPInfo
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils.log
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.AdManager
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.aaatt_state
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.install_up_state
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.userData
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.GlobalTimer
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.NetUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.mmmnn.MainActivity
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class AAApp : Application() {
    var adActivity: Activity? = null

    companion object {
        var isInBackground = false
        var lastBackgroundTime: Long = 0
        private const val DATASTORE_NAME = "app_preferences"
        val Context.SaDataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
        lateinit var appComponent: Context
        lateinit var thisApplication: Application

        lateinit var saturnUtils: MMKV
        var vvState = false
        var globalTimer: GlobalTimer? = null
        var adManagerOpen: AdManager? = null
        var adManagerConnect: AdManager? = null
        var adManagerHomeNav: AdManager? = null
        var adManagerResultNav: AdManager? = null
        var adManagerEndInt: AdManager? = null
        var adManagerListInt: AdManager? = null

        var cloneGuide: Boolean = false

        var jumpToHomeType = "0"//0:shoudong,1:fast,2:item,
        var isCanHots = false
        var adTbaActivityName = ""
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = this
        thisApplication = this
        MMKV.initialize(this)
        saturnUtils =
            MMKV.mmkvWithID("saturnUtils", MMKV.MULTI_PROCESS_MODE)
        Core.init(this, MainActivity::class)
        Core.stopService()
        vvState = false
        Log.e("TAG", "onCreate: App --${isMainProcess(this)}")
        if (isMainProcess(this)) {
            globalTimer = GlobalTimer()
            adManagerOpen = AdManager(this)
            adManagerConnect = AdManager(this)
            adManagerHomeNav = AdManager(this)
            adManagerResultNav = AdManager(this)
            adManagerEndInt = AdManager(this)
            adManagerListInt = AdManager(this)
            if (appComponent.userData.isBlank()) {
                appComponent.userData = UUID.randomUUID().toString()
            }
            CoroutineScope(Dispatchers.IO).launch {
                getIPInfo()
                NetUtils.getBlackList(this@AAApp)
                RRRRDDDT()
            }
            hotFun()
            initAdJust()
        }
    }

    private fun isMainProcess(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myPid = Process.myPid()
        val packageName = context.packageName

        val runningAppProcesses = activityManager.runningAppProcesses ?: return false

        for (processInfo in runningAppProcesses) {
            if (processInfo.pid == myPid && processInfo.processName == packageName) {
                return true
            }
        }
        return false
    }

    private fun hotFun() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {
                if (activity is AdActivity) {
                    adActivity = activity
                }
            }

            override fun onActivityResumed(activity: Activity) {
                // TODO Adjust
                Adjust.onResume()
                isInBackground = false
                if (isCanHots) {
                    (activity as? MainActivity)?.showStartFragment()
                    isCanHots = false
                }
            }

            override fun onActivityPaused(activity: Activity) {
                Adjust.onPause()
            }

            override fun onActivityStopped(activity: Activity) {
                if (activity is AdActivity) {
                    return
                }
                isInBackground = true
                isCanHots = false
                CoroutineScope(Dispatchers.Main).launch {
                    delay(3000)
                    if (isInBackground) {
                        isCanHots = true
                        adActivity?.finish()
                    }
                }
                lastBackgroundTime = System.currentTimeMillis()
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }

    private fun RRRRDDDT() {
        runCatching {
            val referrerClient = InstallReferrerClient.newBuilder(appComponent).build()
            referrerClient.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(p0: Int) {
                    when (p0) {
                        InstallReferrerClient.InstallReferrerResponse.OK -> {
                            referrerClient.installReferrer?.run {
                                if (appComponent.install_up_state != "1") {
                                    log("onInstallReferrerSetupFinished: ")
                                    TTTDDUtils.postInstallJson(this)
                                }
                            }
                        }
                    }
                    referrerClient.endConnection()
                }

                override fun onInstallReferrerServiceDisconnected() {
                }
            })
        }.onFailure { e ->
        }
    }

    private fun initAdJust() {
        Adjust.addSessionCallbackParameter(
            "customer_user_id",
            Settings.Secure.getString(appComponent.contentResolver, Settings.Secure.ANDROID_ID)
        )
        // TODO Adjust id ,model
        val appToken = "ih2pm2dr3k74"
        val environment: String = AdjustConfig.ENVIRONMENT_SANDBOX
        val config = AdjustConfig(appComponent, appToken, environment)
        config.needsCost = true
        config.setOnAttributionChangedListener { attribution ->
            log("adjust=${attribution}")
            if (appComponent.aaatt_state != "1" && attribution.network.isNotEmpty() && attribution.network.contains(
                    "organic",
                    true
                ).not()
            ) {
                appComponent.aaatt_state = "1"
            }
        }
        Adjust.onCreate(config)
    }
}
