package com.samuelokello.shopspot.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    private var backPressedTime = 0L
    
    fun onBackPressed(
        context: Context,
        activity: ComponentActivity
    ): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - backPressedTime > 2000) {
            backPressedTime = currentTime
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            false
        } else {
            activity.finish()
            true
        }
    }
}