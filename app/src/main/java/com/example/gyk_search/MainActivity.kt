package com.example.gyk_search


import android.Manifest.permission.READ_CONTACTS
import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.t9search.util.T9Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {

    private val appList = ArrayList<AppInfo>()

    private val contactList=ArrayList<ContactInfo>()

    private val appListAdapter by lazy { AppAdapter() }

    private val contactListAdapter by lazy{ContactAdapter()}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        check()
        getPackages(appList)
        getContacts(contactList)
        initApp()
    }

    private fun check() {
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this@MainActivity, READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(READ_CONTACTS),
                201
            )
        } else {
            initView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 201) {
            initView()
        } else {
            return
        }
    }

    private fun initApp(){
        val applayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = applayoutManager
        recyclerView.adapter = appListAdapter
    }

    private fun initContact(){
        val contactlayoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=contactlayoutManager
        recyclerView.adapter = contactListAdapter
    }

    private fun initView() {

        dial_input_edit_text.doOnTextChanged { text, _, _, _ ->
            val newList = appList.filter { T9Util.match(it.t9SearchUnit, text.toString()) }
            appListAdapter.updateList(newList)
            val contactNewList=contactList.filter { T9Util.match(it.t9ContactName, text.toString())&&T9Util.match(it.t9ContactNum, text.toString()) }
            contactListAdapter.updateList(contactNewList)
        }

        dial_delete_btn.setOnClickListener {
            dial_input_edit_text.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
        }

        switchbutton.isSelected=true
        switchbutton.setOnClickListener{
            if (!switchbutton.isSelected){
                initApp()
            } else {
               initContact()
            }
            switchbutton.isSelected=!switchbutton.isSelected

        }


        fab.isSelected = true
        fab.setOnClickListener {
            fab.isSelected = !fab.isSelected
            frag.isVisible = !fab.isSelected
        }
        setNumClickListener(
            dialNum0, dialNum1, dialNum2, dialNum3, dialNum4,
            dialNum5, dialNum6, dialNum7, dialNum8, dialNum9
        )
    }

    private fun setNumClickListener(vararg views: TextView) {
        views.forEach { text ->
            text.setOnClickListener {
                dial_input_edit_text.append(text.tag.toString())
            }
        }
    }

    private fun getPackages(applist: ArrayList<AppInfo>) {
        val packages = packageManager.getInstalledPackages(0)
        packages.forEach {
            val isSystem = it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
            if (isSystem) {
                val appName = it.applicationInfo.loadLabel(packageManager)
                val appImage = it.applicationInfo.loadIcon(packageManager)
                val pkgName = it.applicationInfo.packageName
                val appInfo = AppInfo(appImage, appName as String, pkgName)
                applist.add(appInfo)
            }
        }
        appListAdapter.updateList(applist)
    }

    fun getContacts(contactList:ArrayList<ContactInfo>) {
        val resolver=getContentResolver();
        val uri:Uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = resolver.query(uri,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME), null, null, null)
            //查找数据表

        while (cursor!!.moveToNext()){
            var data:String=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            var phonenum:String=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            if (data==""){
                data="没名字"
            }
            if (phonenum==""){
                phonenum="没电话"
            }
            Log.w("cdm", "MainActivity.getContacts -> $data+$phonenum")
            val contactInfo=ContactInfo(data,phonenum)
            contactList.add(contactInfo)
        }
        contactListAdapter.updateList(contactList)
    }

}

