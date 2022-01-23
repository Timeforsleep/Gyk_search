package com.example.gyk_search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.startActivity
import com.example.gyk_search.MyApplication.Companion.context


class AppAdapter : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    private val appList: MutableList<AppInfo> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<AppInfo>) {
        appList.clear()
        appList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appimage: ImageView = view.findViewById(R.id.appimage)
        val appname: TextView = view.findViewById(R.id.appname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appinfo_item, parent, false)
        val viewHolder = ViewHolder(view)


        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val appInfo = appList[position]
            val appmanager: PackageManager = context.packageManager
            val intent = Intent(appmanager.getLaunchIntentForPackage(appInfo.pkgName))
            startActivity(context, intent, null)

        }
        viewHolder.appimage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val appInfo = appList[position]
            val appmanager: PackageManager = context.packageManager
            val intent = Intent(appmanager.getLaunchIntentForPackage(appInfo.pkgName))
            startActivity(context, intent, null)
        }
        return viewHolder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appInfo = appList[position]
        holder.appimage.setImageDrawable(appInfo.appImage)
        holder.appname.text = appInfo.appName
    }

    override fun getItemCount() = appList.size

}


