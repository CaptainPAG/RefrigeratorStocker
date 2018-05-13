package com.pag.n_satou.refrigeratorstocker

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Created by N-Satou on 2018/05/12.
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}