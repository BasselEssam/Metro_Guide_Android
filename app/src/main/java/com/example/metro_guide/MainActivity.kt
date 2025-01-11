package com.example.metro_guide

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var startStationSpinner:Spinner
    lateinit var arrivalStationSpinner:Spinner
    lateinit var startButton:Button
    lateinit var resultTextView:TextView
    lateinit var file:SharedPreferences
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
        val allStations= listOf("Please Select","helwan","ain helwan","helwan university","wadi hof","hadayek helwan","el-maasara",
            "tora el-asmant","kozzika","tora el-balad","thakanat el-maadi","maadi","hadayek el-maadi",
            "dar el-salam","el-zahraa","mar girgis","el-malek el-saleh","sayeda zeinab","saad zaghloul",
            "el-sadat","gamal abdel-nasser","orabi","el-shohadaa","ghamra","el-demerdash","manshiet el-sadr",
            "kobri el-kobba","hammamat el-kobba","saray el-kobba","hadayek el-zeitoun","helmeyet el-zeitoun",
            "el-matareyya","ain shams","ezbet el-nakhl","el-marg","new el-marg","el-monib","sakiat mekki","om el-masryeen","giza",
            "faisal","cairo university","el-bohooth","dokki", "opera","mohamed naguib","attaba","massara","rod el-farag","st. teresa",
            "el-khalafawy","el-mezallat","faculty of agriculture","shubra el-kheima","adly mansour","el-haykestep","omar ibn el-khattab",
            "qobaa","hesham barakat","el-nozha", "nadi el-shams","alf maskan","heliopolis","haroun","al-ahram","koleyet el-banat","stadium",
            "fair zone", "abbassia","abdou pasha","el-geish","bab el-shaaria","maspero","safaa hegazy", "kit kat","sudan","imbaba",
            "el-bohy","el-qawmia","ring road","rod el-farag corr","el-tawfikia","wadi el nile","gamet el dowel","bulaq el-dakrour","cairo university")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allStations)
        startStationSpinner.adapter=adapter
        arrivalStationSpinner.adapter=adapter
        file=getSharedPreferences("data", MODE_PRIVATE)
        val startStationPosition=file.getInt("startStation",0)
        startStationSpinner.setSelection(startStationPosition)
        val arrivalStationPosition=file.getInt("arrivalStation",0)
        arrivalStationSpinner.setSelection(arrivalStationPosition)
        val result=file.getString("result","")
        resultTextView.text=result
    }

    override fun onDestroy() {
        file.edit {
            putInt("startStation",startStationSpinner.selectedItemPosition)
            putInt("arrivalStation",arrivalStationSpinner.selectedItemPosition)
            putString("result",resultTextView.text.toString())
        }
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun search(view: View) {
        val startStation=startStationSpinner.selectedItem.toString()
        val arrivalStation=arrivalStationSpinner.selectedItem.toString()
        if(startStation == "Please Select" || arrivalStation=="Please Select"){
            Toast.makeText(this,"Please select stations",Toast.LENGTH_LONG).show()
            return
        }
        if (arrivalStation == startStation) {
            Toast.makeText(this,"Please enter different stations",Toast.LENGTH_LONG).show()
            return
        }
        val routes=start.search(startStation,arrivalStation)
        resultTextView.setHorizontallyScrolling(true)
        resultTextView.movementMethod=ScrollingMovementMethod()
        resultTextView.text=routes
    }
}