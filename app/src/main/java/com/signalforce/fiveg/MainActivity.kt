package com.signalforce.fiveg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.signalforce.fiveg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        updateNetworkTelemetry()
    }

    private fun setupListeners() {
        // Force 5G circular hero button
        binding.btnOpenRadioInfo.setOnClickListener {
            val launched = NetworkHelper.launchRadioConfig(this)
            if (!launched) {
                Toast.makeText(
                    this,
                    "Unable to launch radio menu automatically. Please dial *#*#4636#*#*.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // Speed test button — opens fast.com
        binding.btnSpeedTest.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://fast.com"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Could not open browser for speed test.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Band / Network Info Dialog
        binding.btnDeviceInfo.setOnClickListener {
            val state = NetworkHelper.getCurrentNetworkState(this)
            AlertDialog.Builder(this)
                .setTitle("Cellular Info")
                .setMessage(
                    "Carrier: ${state.carrierName}\n" +
                    "Mode: ${state.connectionType}\n" +
                    "5G Status: ${if (state.is5G) "Connected (NR)" else "Standby / LTE"}\n\n" +
                    "To lock NR only:\n" +
                    "Tap FORCE 5G -> 'Set Preferred Network Type' -> select 'NR only'."
                )
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun updateNetworkTelemetry() {
        val state = NetworkHelper.getCurrentNetworkState(this)
        binding.tvCarrierName.text = state.carrierName
        binding.tvNetworkType.text = state.connectionType

        if (state.is5G) {
            binding.tv5GBadge.text = "5G SA"
            binding.tv5GBadge.alpha = 1.0f
            binding.circleRing.visibility = View.VISIBLE
            binding.tvCircleStatus.text = "● 5G SA ACTIVE"
            binding.tvCircleStatus.setTextColor(getColor(R.color.emerald_active))
        } else {
            binding.tv5GBadge.text = "LTE"
            binding.tv5GBadge.alpha = 0.75f
            binding.circleRing.visibility = View.INVISIBLE
            binding.tvCircleStatus.text = "TAP TO ENABLE NR-ONLY"
            binding.tvCircleStatus.setTextColor(getColor(R.color.text_muted))
        }
    }
}
