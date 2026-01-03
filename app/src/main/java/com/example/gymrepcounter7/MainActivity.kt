package com.example.gymrepcounter7

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var repCounter: RepCounter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvReps = findViewById<TextView>(R.id.tvReps)
        val btnReset = findViewById<Button>(R.id.btnReset)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        repCounter = RepCounter { reps ->
            runOnUiThread {
                tvReps.text = reps.toString()
            }
        }

        btnReset.setOnClickListener {
            repCounter.reset()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            repCounter,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(repCounter)
    }
}
