package com.seventhstar.films.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seventhstar.films.R

class MainActivity : AppCompatActivity() {

    private val receiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        //registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}