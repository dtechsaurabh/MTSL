package com.example.mtsl.utils

import android.app.Application

class Myapp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        // Initialize required utilities
        SharedPreferenceUtils.init(this)
    }

    companion object {
        @JvmStatic
        lateinit var instance: Myapp
            private set
    }
}

