package com.signalforce.fiveg

import android.os.Bundle
import android.widget.Toast
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
        binding.btnOpenRadioInfo.setOnClickListener {
            val launched = NetworkHelper.launchRadioConfig(this)
            if (!launched) {
                Toast.makeText(
                    this,
                    "Unable to launch hidden radio menu automatically. Please dial *#*#4636#*#*.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateNetworkTelemetry() {
        val state = NetworkHelper.getCurrentNetworkState(this)
        binding.tvCarrierName.text = "Carrier: ${state.carrierName}"
        binding.tvNetworkType.text = state.connectionType

        if (state.is5G) {
            binding.tv5GBadge.text = "5G ACTIVE"
            binding.tv5GBadge.alpha = 1.0f
        } else {
            binding.tv5GBadge.text = "STANDBY"
            binding.tv5GBadge.alpha = 0.6f
        }
    }
}
