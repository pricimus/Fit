package com.greydog.fitapp

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.splash_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val hasToken = prefs.getString("token", "") != ""

        if (hasToken) {
            findNavController().navigate(R.id.action_splashfragment_to_stepsfragment)
        }
        else {
            findNavController().navigate(R.id.action_splashfragment_to_loginfragment)
        }
    }
}