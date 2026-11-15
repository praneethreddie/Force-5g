package com.signalforce.fiveg

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager

object NetworkHelper {

    data class NetworkState(
        val carrierName: String,
        val connectionType: String,
        val is5G: Boolean
    )

    fun getCurrentNetworkState(context: Context): NetworkState {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        val carrier = telephonyManager?.networkOperatorName.takeIf { !it.isNullOrBlank() } ?: "Cellular Provider"

        val activeNetwork = connectivityManager?.activeNetwork
        val caps = connectivityManager?.getNetworkCapabilities(activeNetwork)

        if (caps == null) {
            return NetworkState(carrier, "No Active Connection", false)
        }

        if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return NetworkState(carrier, "Connected via Wi-Fi", false)
        }

        if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return NetworkState(carrier, "Cellular Connected (NR/LTE)", true)
        }

        return NetworkState(carrier, "Active Connection", false)
    }

    /**
     * Executes a 4-tier fallback launch chain to ensure compatibility across
     * Samsung, Pixel, OnePlus, Xiaomi, and stock Android devices.
     */
    fun launchRadioConfig(context: Context): Boolean {
        // Attempt 1: Standard Android RadioInfo
        try {
            val intent1 = Intent(Intent.ACTION_MAIN).apply {
                component = ComponentName("com.android.settings", "com.android.settings.RadioInfo")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent1)
            return true
        } catch (_: Exception) {}

        // Attempt 2: Settings Testing Menu
        try {
            val intent2 = Intent(Intent.ACTION_MAIN).apply {
                component = ComponentName("com.android.settings", "com.android.settings.TestingSettings")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent2)
            return true
        } catch (_: Exception) {}

        // Attempt 3: Phone package RadioInfo variant
        try {
            val intent3 = Intent().apply {
                setClassName("com.android.phone", "com.android.phone.settings.RadioInfo")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent3)
            return true
        } catch (_: Exception) {}

        // Attempt 4: Samsung ServiceModeApp
        try {
            val intent4 = Intent().apply {
                component = ComponentName("com.sec.android.app.servicemodeapp", "com.sec.android.app.servicemodeapp.ServiceModeApp")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent4)
            return true
        } catch (_: Exception) {}

        // Fallback 5: Launch Dialer with testing code
        try {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:" + Uri.encode("*#*#4636#*#*"))
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(dialIntent)
            return true
        } catch (_: Exception) {}

        return false
    }
}
