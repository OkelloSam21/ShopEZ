package com.samuelokello.shopspot

import android.app.Application
import com.samuelokello.shopspot.data.DefaultAppContainer
import com.samuelokello.shopspot.data.ShopSpotContainer

class ShopSpotApplication : Application(){
    lateinit var container: ShopSpotContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}