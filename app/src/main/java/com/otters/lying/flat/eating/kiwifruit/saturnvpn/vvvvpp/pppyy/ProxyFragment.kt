package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.pppyy

import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.BaseFragment
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.AppInfo
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentEndBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentHomeBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentProxyBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.databinding.FragmentStartBinding
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.CenterUtils.getInstalledApps
import java.lang.System.exit
import java.util.Locale
import kotlin.system.exitProcess

class ProxyFragment : BaseFragment<FragmentProxyBinding, ProxyViewModel>(){
    override val layoutId: Int
        get() = R.layout.fragment_proxy

    override val viewModelClass: Class<ProxyViewModel>
        get() = ProxyViewModel::class.java

    private var appList:MutableList<AppInfo>?=null
    private var appAdapter:AppListAdapter?=null
    override fun setupViews() {
        binding.imgBack.setOnClickListener {
            customizeReturnKey()
        }
        initAdapter()
        setEditView()
        TTTDDUtils.postPointData("moo24")
    }

    override fun observeViewModel() {
    }

    override fun customizeReturnKey() {
        navigateToBack()
    }

    private fun initAdapter(){
        appList = activity?.let { getInstalledApps(it) }
        if(appList?.isEmpty() == true){
            binding.rvApp.isVisible = false
            binding.tvNoData.isVisible = true
        }else{
            binding.rvApp.isVisible = true
            binding.tvNoData.isVisible = false
        }
        binding.rvApp.layoutManager = LinearLayoutManager(activity)
        appAdapter = appList?.let { AppListAdapter(it) }
        binding.rvApp.adapter = appAdapter
    }
    private fun setEditView() {
        binding.appEtApp.addTextChangedListener {
            Log.e("TAG", "setEditView: ${it.toString()}", )
            showSearchWebItem(it.toString())
        }
    }
    private fun showSearchWebItem(
        seachString: String,
    ) {
        if (appList?.isNotEmpty() == true) {
            appList?.forEach { all ->
                all.isGone = !((all.name).lowercase(Locale.getDefault()).contains(
                    seachString
                        .lowercase(Locale.getDefault())
                ))
            }
            appAdapter?.upDataList(appList!!)
            showNoData()
        }
    }
    private fun showNoData() {
        var type = false
        appList?.forEach {
            if (!it.isGone) {
                type = true
            }
        }
        binding.tvNoData.isVisible = !type
        binding.rvApp.isVisible = type
    }
}