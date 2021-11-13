package com.example.gyk_search

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(val Applist: List<AppInfo>) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {


    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val appimage: ImageView = view.findViewById(R.id.appimage)
        val appname: TextView = view.findViewById(R.id.appname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appinfo_item,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appInfo=Applist[position]
        holder.appimage.setImageDrawable(appInfo.mappimage)
        holder.appname.text=appInfo.mappname
    }

    override fun getItemCount()=Applist.size

}

