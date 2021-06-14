package com.seventhstar.films.view.main

import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.seventhstar.films.R
import com.seventhstar.films.view.SettingsFragment


class MainActivity() : AppCompatActivity(), OnSharedPreferenceChangeListener{

    private val receiver = MainBroadcastReceiver()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        val showAdult = sharedPreferences!!.getBoolean("showAdult", false).toString()
        //Toast.makeText(this, "show_adult: $showAdult", Toast.LENGTH_SHORT).show()

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show()
        val manager = supportFragmentManager
        manager.beginTransaction()
            .replace(R.id.container, SettingsFragment.newInstance())
            .addToBackStack("")
            .commitAllowingStateLoss()
        return super.onOptionsItemSelected(item)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        TODO("Not yet implemented")
        Toast.makeText(this, "Settings changed", Toast.LENGTH_SHORT).show()
    }
}