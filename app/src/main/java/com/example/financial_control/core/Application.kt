package com.example.financial_control.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FinancialControlApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}