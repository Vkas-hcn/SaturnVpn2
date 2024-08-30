package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.wwwww

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseFragment
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentEndBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentHomeBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentPpBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentProxyBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentStartBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils
import java.lang.System.exit
import kotlin.system.exitProcess

class PpFragment : BaseFragment<FragmentPpBinding, PpViewModel>(){
    override val layoutId: Int
        get() = R.layout.fragment_pp

    override val viewModelClass: Class<PpViewModel>
        get() = PpViewModel::class.java


    override fun setupViews() {
        AAApp.adTbaActivityName = this.javaClass.simpleName
        binding.iconBack.setOnClickListener {
            customizeReturnKey()
        }
        binding.webPp.apply {
            loadUrl(DataUtils.pp_url)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return true
                }
            }
        }
    }

    override fun observeViewModel() {
    }

    override fun customizeReturnKey() {
        navigateTo(R.id.action_ppFragment_to_homeFragment)
    }
}