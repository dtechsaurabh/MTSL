package com.example.mtsl.utils

import android.app.Application

class Myapp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        /*DbManager.init(this);*/
        SharedPreferenceUtils.init(this)
    }

    companion object {
        var instance: Myapp? = null
            private set
    }
}

