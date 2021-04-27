package com.paulduck.permissionlib

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object PermissionHandler {

    interface PermissionCallback {
        fun OnResultReturned(granted:Boolean)
    }

    private var callback: PermissionCallback? = null

    fun requirePermission(activity: Activity, permissions:ArrayList<String>, callback: PermissionCallback){
        PermissionHandler.callback = callback
        val intent = Intent(activity, PermissionRequestActivity::class.java)
        intent.putStringArrayListExtra("permissions", permissions)
        activity.startActivity(intent)
    }

    private fun OnResultReturned(granted:Boolean){
        callback?.OnResultReturned(granted)
    }

    class PermissionRequestActivity : AppCompatActivity() {

        private val REQUEST_PERMISSION = 1416
        private var isInRequest = false

        private fun requestPermissions() {
            isInRequest = true
            for (permission in intent.extras!!.getStringArrayList("permissions")!!) {
                if (packageManager.checkPermission(permission, packageName) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, intent.extras!!.getStringArrayList("permissions")!!.toTypedArray(), REQUEST_PERMISSION)
                    return
                }
            }

            returnResult()
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
            if (requestCode == REQUEST_PERMISSION) {
//                for (i in grantResults.indices) {
//                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//
//                    }
//                }
                returnResult()
                finish()
            }
        }


        private fun returnResult(){
            for (permission in intent.extras!!.getStringArrayList("permissions")!!) {
                if (packageManager.checkPermission(permission, packageName) != PackageManager.PERMISSION_GRANTED) {
                    OnResultReturned(
                        false
                    )
                    finish()
                    return
                }
            }

            OnResultReturned(true)
            finish()
        }

        override fun onResume() {
            super.onResume()
            if (!isInRequest) {
                requestPermissions()
            }
            else {
                returnResult()
            }
        }
    }

}

