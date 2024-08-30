package com.otters.lying.flat.eating.kiwifruit.saturnvpn.vvvvpp.llltt

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.R
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbee.AAApp
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.SX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.parseSX
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.bbbnn.toJson
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.tttttaa.TTTDDUtils
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.CenterUtils.getSaturnImage
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.click_now_vpn
import com.otters.lying.flat.eating.kiwifruit.saturnvpn.uuuuss.DataUtils.connect_now_vpn

class MoreListAdapter(private var vpnList: List<SX>, activity: FragmentActivity, moreFragment: MoreFragment) :
    RecyclerView.Adapter<MoreListAdapter.AppViewHolder>() {
    private var activity = activity
    private var moreFragment = moreFragment
    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFast: AppCompatImageView = view.findViewById(R.id.img_fast)
        val tvFast: TextView = view.findViewById(R.id.tv_fast)
        val linearLayout: LinearLayout = view.findViewById(R.id.linearLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ch_more, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val item = vpnList[position]
        if (item.best_data) {
            holder.tvFast.text = "Faster Server"
            holder.imgFast.setImageResource(R.drawable.icon_fast)
        } else {
            holder.tvFast.text = String.format(item.TiORzr + "-" + item.LMfccpX+ "-0" + position)
            holder.imgFast.setImageResource(item.TiORzr.getSaturnImage())
        }
        var isSele = false
        if (AAApp.appComponent.connect_now_vpn.isNotBlank()) {
            val connectNowVpn = AAApp.appComponent.connect_now_vpn.let { parseSX(it) }
            if(connectNowVpn.CwDwOnOtuQ == item.CwDwOnOtuQ && connectNowVpn.best_data == item.best_data){
                isSele = true
            }
        }
        if (isSele && AAApp.vvState) {
            holder.linearLayout.background =
                holder.itemView.context.getDrawable(R.drawable.bg_service_main2)
        } else {
            holder.linearLayout.background =
                holder.itemView.context.getDrawable(R.drawable.bg_service_main)
        }
        holder.linearLayout.setOnClickListener {
            if(isSele)return@setOnClickListener
            AAApp.jumpToHomeType = "2"
            chooeServices(item)
        }
    }

    override fun getItemCount(): Int = vpnList.size

    fun upDataList(listData:MutableList<SX>){
        vpnList = listData
        notifyDataSetChanged()
    }
    fun chooeServices(jsonBean: SX) {
        if (AAApp.vvState) {
            currentConnectionFun {
                AAApp.appComponent.click_now_vpn = jsonBean.toJson()
                moreFragment.navigateTo(R.id.action_moreFragment_to_homeFragment)
            }
        } else {
            AAApp.appComponent.connect_now_vpn = jsonBean.toJson()
            moreFragment.navigateTo(R.id.action_moreFragment_to_homeFragment)
        }
    }
    private fun currentConnectionFun( nextFUn: () -> Unit) {
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setTitle("Tip")
        alertDialog.setMessage("Whether To Disconnect The Current Connection")
        alertDialog.setIcon(R.mipmap.ic_launcher)
        alertDialog.setPositiveButton("YES") { dialog: DialogInterface?, which: Int ->
            nextFUn()
            TTTDDUtils.postPointData("moo19")
        }
        alertDialog.setNegativeButton("NO", null)
        alertDialog.show()
    }
}
