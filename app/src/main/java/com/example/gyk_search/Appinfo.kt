package com.example.gyk_search

import android.graphics.drawable.Drawable
import com.t9search.model.PinyinSearchUnit
import com.t9search.util.PinyinUtil


//class AppInfo(appimage:Drawable,appname:String,packagename:String) {
//
//      var mappimage: Drawable? =appimage//软件图标
//      var mappname:String  = appname //软件名
//      var mpackagename:String  = packagename //包名
//
//    //构造方法
//
//
//}

// 使用 data class
data class AppInfo(val appImage: Drawable, val appName: String, val pkgName: String) {

    val t9SearchUnit by lazy {
        PinyinSearchUnit(appName).also { PinyinUtil.parse(it) }
        // 等价于这样写
//        val pinyinSearchUnit = PinyinSearchUnit(appName)
//        PinyinUtil.parse(pinyinSearchUnit)
//        pinyinSearchUnit
    }
}
