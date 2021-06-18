package com.seventhstar.films.view.main

import android.app.SearchManager
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seventhstar.films.R
import com.seventhstar.films.viewmodel.MainViewModel


class MainActivity() : AppCompatActivity(), OnSharedPreferenceChangeListener,
    SearchView.OnQueryTextListener {

    private val receiver = MainBroadcastReceiver()
    private var sharedPreferences: SharedPreferences? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_popular, R.id.navigation_favorites, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        //setSupportActionBar(findViewById(R.id.toolbar))
        //getSupportActionBar().setTitle("Comments");

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        val showAdult = sharedPreferences!!.getBoolean("showAdult", false).toString()
        //Toast.makeText(this, "show_adult: $showAdult", Toast.LENGTH_SHORT).show()

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
        handleIntent(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        // for left (back) arrow - from details to main, from settings to main
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val item = menu!!.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(item) as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(
            this,
            item.toString() + " : " + (item.itemId == R.id.settingsFragment),
            Toast.LENGTH_SHORT
        ).show()
        val manager = supportFragmentManager

        if (item.itemId == R.id.settingsFragment) {
            Navigation
                .findNavController(this, R.id.nav_host_fragment)
                .navigate(R.id.action_global_open_settings_fragment)
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        //TODO("Not yet implemented")
        Toast.makeText(this, "Settings changed", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //TODO("Not yet implemented")
        Toast.makeText(this, "Search text: $query", Toast.LENGTH_SHORT).show()
        viewModel.setFilter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //TODO("Not yet implemented")
        return true
    }
}