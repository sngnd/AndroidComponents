package com.example.androidcomponents

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.math.pow

const val COR = 10.0

class CalculationService: Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var N = intent?.getIntExtra("N", 1)
        var pi = N?.let { calculation(it) }.toString()
        Log.d(TAG, pi)
        sendBroadcast(Intent(CALCULATE_ACTION).putExtra("pi", pi))
        return super.onStartCommand(intent, flags, startId)
    }

    private fun calculation (N: Int) : String {
        var pi = 0.0
        var sign = 1 // определение знака
        val eps = COR.pow(-7) // точность/
        var i = 0
        do
        {
            pi += sign/ (2.0 * i + 1.0) // ряд Лейбница
            sign =- sign; // изменяем знак
            i++
        } while(1.0/ (2.0 * i + 1.0)>eps)
        pi *= 4
        return "%.${N}f".format(pi)
    }

}