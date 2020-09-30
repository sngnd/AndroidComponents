package com.example.androidcomponents

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

public const val TAG = "BaranovaAV"
public const val CALCULATE_ACTION = "calculate"

class MainActivity: AppCompatActivity() {
    private var counter = 0

    private val broadcastReceiver : BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive (context: Context, intent: Intent) {
            if (intent.action == CALCULATE_ACTION) {
                var result = intent.getStringExtra("pi").toString()
                Log.d (TAG, result)
                showResult(result)
            }
        }
    }
    private val filter: IntentFilter = IntentFilter(CALCULATE_ACTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle){
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("N", counter)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("N") + 1
        Log.w(TAG, "Counter = $counter")
        val intent: Intent = Intent (this, CalculationService::class.java)
        intent.putExtra("N", counter)
        startService(intent)
    }

    private fun showResult(result: String) {
        val message = "Pi = $result"
        resultTextView.text = message
    }
}