package com.seventhstar.films.view
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.seventhstar.films.R

class SettingsFragment : PreferenceFragmentCompat()  {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
//        val sp = preferenceScreen.sharedPreferences
//        val editTextPref: EditTextPreference? = findPreference("showAdult") as EditTextPreference?
//        editTextPref.setSummary(sp.getString("thePrefKey", "Some Default Text"))

    }



//    onCha

    companion object {
        fun newInstance() = SettingsFragment()
    }

}