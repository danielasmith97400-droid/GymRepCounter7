package com.example.gymrepcounter7

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class RepCounter(
    private val onRep: (Int) -> Unit
) : SensorEventListener {

    private var lastZ = 0f
    private var goingUp = false
    private var reps = 0
    private val threshold = 2.2f

    override fun onSensorChanged(event: SensorEvent) {
        val z = event.values[2]
        val delta = z - lastZ

        if (delta > threshold) {
            goingUp = true
        }

        if (delta < -threshold && goingUp) {
            reps++
            onRep(reps)
            goingUp = false
        }

        lastZ = z
    }

    fun reset() {
        reps = 0
        onRep(reps)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
