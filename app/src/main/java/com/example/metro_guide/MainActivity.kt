package com.example.metro_guide

import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var startStationSpinner:Spinner
    lateinit var arrivalStationSpinner:Spinner
    lateinit var startButton:Button
    lateinit var resultTextView:TextView
    val start=Start()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startStationSpinner=findViewById(R.id.startStationSpinner)
        arrivalStationSpinner=findViewById(R.id.arrivalStationSpinner)
        startButton=findViewById(R.id.startButton)
        resultTextView=findViewById(R.id.resultTextView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun search(view: View) {
        val startStation=startStationSpinner.selectedItem.toString()
        val arrivalStation=arrivalStationSpinner.selectedItem.toString()
        if (arrivalStation == startStation) {
            Toast.makeText(this,"Please enter different stations",Toast.LENGTH_LONG).show()
            return
        }
        val routes=start.search(startStation,arrivalStation)
        //println(routes)
        resultTextView.setHorizontallyScrolling(true)
        resultTextView.movementMethod=ScrollingMovementMethod()
        resultTextView.text=routes
    }
}