package com.paulduck.example

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.paulduck.permissionlib.PermissionHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionHandler.requirePermission(this, arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE), object:
                PermissionHandler.PermissionCallback {
            override fun OnResultReturned(granted: Boolean) {
                Log.d("KINNNNNN", "Granted: $granted")

            }
        })

    }
}