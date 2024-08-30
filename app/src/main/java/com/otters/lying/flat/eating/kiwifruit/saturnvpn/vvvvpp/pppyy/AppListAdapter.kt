package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.pppyy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.AppInfo
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.CenterUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.app_pack_info

class AppListAdapter(private var appList: List<AppInfo>) :
    RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: AppCompatImageView = view.findViewById(R.id.img_icon)
        val appName: TextView = view.findViewById(R.id.tv_app_name)
        val appState: AppCompatImageView = view.findViewById(R.id.img_sw)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ch_proxy, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val appInfo = appList[position]
        holder.appIcon.setImageDrawable(appInfo.icon)
        holder.appName.text = appInfo.name
        val imgData = if (appInfo.isSate) {
            R.drawable.icon_switch
        } else {
            R.drawable.icon_switch2
        }
        holder.appState.setImageResource(imgData)
        holder.appState.setOnClickListener {
            Log.e("TAG", "onBindViewHolder: ${appInfo.isSate}", )
            if(appInfo.isSate){
                appInfo.isSate = false
                CenterUtils.removeAppPack(appInfo.packageName)
            }else{
                appInfo.isSate = true
                CenterUtils.saveAppStateList(appInfo.packageName)
            }
            DataUtils.rl_list = AAApp.appComponent.app_pack_info.toString()
            notifyDataSetChanged()
        }
        setVisibility(appInfo.isGone, holder.itemView)
    }

    override fun getItemCount(): Int = appList.size

    private fun setVisibility(isVisible: Boolean, itemView: View) {
        val param = itemView.layoutParams as RecyclerView.LayoutParams
        if (isVisible) {
            // 隐藏 item 并让其不占用空间
            param.height = 0
            param.width = 0
            itemView.visibility = View.GONE
        } else {
            // 显示 item 并恢复其占用空间
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT
            param.width = LinearLayout.LayoutParams.MATCH_PARENT
            itemView.visibility = View.VISIBLE
        }
        itemView.layoutParams = param
    }


    fun upDataList(listData:MutableList<AppInfo>){
        appList = listData
        notifyDataSetChanged()
    }
}
