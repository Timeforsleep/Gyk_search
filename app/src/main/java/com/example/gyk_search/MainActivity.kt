package com.example.gyk_search



import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    private val applist = ArrayList<AppInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPackages(applist)
        val layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        val recyclerView:RecyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = AppAdapter(applist)
        recyclerView.adapter = adapter
    }

    private fun getPackages(applist:ArrayList<AppInfo>) {
        val packages = packageManager.getInstalledPackages(0)
        packages.forEach {
            val isSystem = it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 //获取非系统应用
            if (isSystem) {
                val appname = it.applicationInfo.loadLabel(packageManager)
                val appimage = it.applicationInfo.loadIcon(packageManager)
                val vercode = it.versionCode
                val verName = it.versionName
                val packagename = it.packageName //获取包名，可以跳转应用
                var appinfo = AppInfo(appimage, appname as String, vercode)
                applist.add(appinfo)
            }
        }
        }
    }






