package com.hammid.RRRplayer.Helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class CheckPermisson(val context:Context,val actvity:Activity) {

    fun checkPer(){
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            actvity.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),50)
        }else{
            return
        }
    }
}