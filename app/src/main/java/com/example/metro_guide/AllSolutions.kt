package com.example.metro_guide

import android.os.Build // ******
import androidx.annotation.RequiresApi // *****
import kotlin.math.abs

class AllSolutions {
    val line1Stations = listOf(
        "helwan","ain helwan","helwan university","wadi hof","hadayek helwan","el-maasara",
        "tora el-asmant","kozzika","tora el-balad","thakanat el-maadi","maadi","hadayek el-maadi",
        "dar el-salam","el-zahraa","mar girgis","el-malek el-saleh","sayeda zeinab","saad zaghloul",
        "el-sadat","gamal abdel-nasser","orabi","el-shohadaa","ghamra","el-demerdash","manshiet el-sadr",
        "kobri el-kobba","hammamat el-kobba","saray el-kobba","hadayek el-zeitoun","helmeyet el-zeitoun",
        "el-matareyya","ain shams","ezbet el-nakhl","el-marg","new el-marg"
    )
    val line2Stations = listOf(
        "el-monib","sakiat mekki","om el-masryeen","giza","faisal","cairo university","el-bohooth","dokki",
        "opera","el-sadat","mohamed naguib","attaba","el-shohadaa","massara","rod el-farag","st. teresa",
        "el-khalafawy","el-mezallat","faculty of agriculture","shubra el-kheima"
    )
    val line3Stations=listOf(
        "adly mansour","el-haykestep","omar ibn el-khattab","qobaa","hesham barakat","el-nozha",
        "nadi el-shams","alf maskan","heliopolis","haroun","al-ahram","koleyet el-banat","stadium","fair zone",
        "abbassia","abdou pasha","el-geish","bab el-shaaria","attaba","gamal abdel-nasser","maspero","safaa hegazy",
        "kit kat","sudan","imbaba","el-bohy","el-qawmia","ring road","rod el-farag corr"
    )
    val line4Stations=listOf(
        "adly mansour","el-haykestep","omar ibn el-khattab","qobaa","hesham barakat","el-nozha",
        "nadi el-shams","alf maskan","heliopolis","haroun","al-ahram","koleyet el-banat","stadium","fair zone",
        "abbassia","abdou pasha","el-geish","bab el-shaaria","attaba","gamal abdel-nasser","maspero","safaa hegazy",
        "kit kat","el-tawfikia","wadi el nile","gamet el dowel","bulaq el-dakrour","cairo university"
    )
    val tempSolutions = mutableListOf<MutableList<String>>()
    val allSolutions = mutableListOf<MutableList<String>>()
    val allSolutionsClassified = mutableListOf<MutableList<String>>()
    val separatedSolutions = mutableListOf<MutableList<String>>()
    var minIndex=0
    @RequiresApi(Build.VERSION_CODES.N)
    fun clean(){
        for (i in 0 until tempSolutions.size){
            for (j in 0 until tempSolutions[i].size-1){
                if (tempSolutions[i][j]==tempSolutions[i][j+1])
                    tempSolutions[i][j]=""
            }
            tempSolutions[i].removeIf { element->element=="" }  // *****
        }
    }

    // [[helwan, el-sadat, el-monib], [helwan, gamal abdel-nasser, attaba, el-monib, helwan, gamal abdel-nasser, cairo university, el-monib], [helwan, el-shohadaa, el-monib]]
    fun classifySolutions(startStation:String,arrivalStation:String){
        for(i in 0 until allSolutions.size){
            allSolutionsClassified.add(mutableListOf())
            for (j in 0 until allSolutions[i].size){
                if(allSolutions[i][j]==startStation){
                    allSolutionsClassified[allSolutionsClassified.lastIndex].addAll(allSolutions[i].subList(j,allSolutions[i].indexOf(arrivalStation)+1))
                    allSolutions[i][allSolutions[i].indexOf(arrivalStation)]=""
                }
            }
        }
    }

    // [[el-monib, cairo university, attaba, el-sadat, helwan, el-monib, cairo university, attaba, el-shohadaa, helwan, el-monib, cairo university, gamal abdel-nasser, helwan], [el-monib, el-sadat, helwan], [el-monib, attaba, gamal abdel-nasser, helwan, el-monib, attaba, cairo university, el-sadat, helwan, el-monib, attaba, cairo university, el-shohadaa, helwan], [el-monib, el-shohadaa, helwan]]
    fun separateSolutions(startStation:String,arrivalStation:String){
        for(i in 0 until allSolutionsClassified.size){
            for(j in 0 until allSolutionsClassified[i].size){
                if(allSolutionsClassified[i][j]==startStation){
                    separatedSolutions.add(mutableListOf())
                    separatedSolutions[separatedSolutions.lastIndex].addAll(allSolutionsClassified[i].subList(j,allSolutionsClassified[i].indexOf(arrivalStation)+1))
                    allSolutionsClassified[i][allSolutionsClassified[i].indexOf(arrivalStation)]=""
                }
            }
        }
    }

    // [[el-monib, cairo university, attaba, el-sadat, helwan], [el-monib, cairo university, attaba, el-shohadaa, helwan], [el-monib, cairo university, gamal abdel-nasser, helwan], [el-monib, el-sadat, helwan], [el-monib, attaba, gamal abdel-nasser, helwan], [el-monib, attaba, cairo university, el-sadat, helwan], [el-monib, attaba, cairo university, el-shohadaa, helwan], [el-monib, el-shohadaa, helwan]]
    // direction= shubra el-kheima
    //number of stations= 12
    //stations: [el-monib, sakiat mekki, om el-masryeen, giza, faisal, cairo university, el-bohooth, dokki, opera, el-sadat, mohamed naguib, attaba, el-shohadaa]
    //direction= helwan
    //number of stations= 21
    //stations: [el-shohadaa, orabi, gamal abdel-nasser, el-sadat, saad zaghloul, sayeda zeinab, el-malek el-saleh, mar girgis, el-zahraa, dar el-salam, hadayek el-maadi, maadi, thakanat el-maadi, tora el-balad, kozzika, tora el-asmant, el-maasara, hadayek helwan, wadi hof, helwan university, ain helwan, helwan]
    // total number of stations: 33
    // estimated time: ~ 66 min
    // ticket price : 20
    fun solutionsDetails():String{
        var result=""
        var currentLine:List<String>
        var previousLine=listOf("")
        var min=100
        for (i in 0 until separatedSolutions.size){
            var totalStationNumber=0
            result+="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-------- Route (${i+1}) --------\n"
            for(j in 0 until separatedSolutions[i].size-1){
                val startStation=separatedSolutions[i][j]
                val arrivalStation= separatedSolutions[i][j+1]
                if(line1Stations.contains(startStation) && line1Stations.contains(arrivalStation))
                    currentLine=line1Stations
                else if(line2Stations.contains(startStation) && line2Stations.contains(arrivalStation))
                    currentLine=line2Stations
                else if(line3Stations.contains(startStation) && line3Stations.contains(arrivalStation))
                    currentLine=line3Stations
                else
                    currentLine=line4Stations
                if( j!=0 && currentLine==previousLine ){
                    currentLine=changeLine(previousLine,startStation,arrivalStation)
                }
                val startIndex=currentLine.indexOf(startStation)
                val endIndex=currentLine.indexOf(arrivalStation)
                val numberOfStations= abs(endIndex - startIndex)
                if (endIndex > startIndex) {
                    result+="Line ${getLineNumber(currentLine)} -> direction: ${currentLine.last()}\n"
                }
                else{
                    result+="Line ${getLineNumber(currentLine)} -> direction: ${currentLine.first()}\n"
                }
                result+="number of stations= $numberOfStations \n"
                result+="stations: [$startStation -> $arrivalStation] \n"
                totalStationNumber+=numberOfStations
                previousLine=currentLine
            }
            if (totalStationNumber<min){
                min=totalStationNumber
                minIndex=i
            }
            //println("Total Number of stations= $totalStationNumber")
            result+="Total Number of stations= $totalStationNumber\n"
            when (totalStationNumber) {
                in 1..9 -> result+="Ticket Price= 8 EGP ( 4 EGP for people at 60 or older , military) \n  (5 EGP for Disability)\n"
                in 10..16 -> result+="Ticket Price= 10 EGP ( 5 EGP for people at 60 or older , military) \n  (5 EGP for Disability)\n"
                in 17..23 -> result+="Ticket Price= 15 EGP ( 8 EGP for people at 60 or older , military) \n ( 5 EGP for Disability)\n"
                else -> result+="Ticket Price= 20 EGP ( 10 EGP for people at 60 or older , military) \n  (5 EGP for Disability)\n"
            }
            //println("Estimated Time: ~${totalStationNumber*2} minutes" )
            result+="Estimated Time: ~${totalStationNumber*2} minutes\n \n"
        }
        return result
    }

    fun shortestRouteDetails():String{
        var result=""
        var currentLine:List<String>
        var previousLine=listOf("")
        var totalStationNumber=0
        //println("--------- Shortest Route ---------")
        result+="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t--------- Shortest Route ---------\n"
        for(i in 0 until separatedSolutions[minIndex].size-1){
            val startStation=separatedSolutions[minIndex][i]
            val arrivalStation= separatedSolutions[minIndex][i+1]
            if(line1Stations.contains(startStation) && line1Stations.contains(arrivalStation))
                currentLine=line1Stations
            else if(line2Stations.contains(startStation) && line2Stations.contains(arrivalStation))
                currentLine=line2Stations
            else if(line3Stations.contains(startStation) && line3Stations.contains(arrivalStation))
                currentLine=line3Stations
            else
                currentLine=line4Stations
            if( i!=0 && currentLine==previousLine ){
                currentLine=changeLine(previousLine,startStation,arrivalStation)
            }
            val startIndex=currentLine.indexOf(startStation)
            val endIndex=currentLine.indexOf(arrivalStation)
            val numberOfStations= abs(endIndex - startIndex)
            if (endIndex > startIndex) {
                //println("Line ${getLineNumber(currentLine)} -> direction: ${currentLine.last()}")
                result+="Line ${getLineNumber(currentLine)} -> direction: ${currentLine.last()} \n"
                //println("number of stations= $numberOfStations")
                result+="number of stations= $numberOfStations \n"
                //println("stations: ${currentLine.slice(startIndex..endIndex)} \n")
                result+="stations: ${currentLine.slice(startIndex..endIndex)} \n"
            }
            else{
                //println("Line ${getLineNumber(currentLine)} -> direction: ${currentLine.first()}")
                result+="Line ${getLineNumber(currentLine)} -> direction: ${currentLine.first()}\n"
                //println("number of stations= $numberOfStations")
                result+="number of stations= $numberOfStations \n"
                //println("stations: ${currentLine.slice(endIndex..startIndex).reversed()} ")
                result+="stations: ${currentLine.slice(endIndex..startIndex).reversed()} \n"
            }

            totalStationNumber+=numberOfStations
            previousLine=currentLine
        }
        //println("Total Number of stations= $totalStationNumber")
        result+="Total Number of stations= $totalStationNumber \n"
        when (totalStationNumber) {
            in 1..9 -> result+="Ticket Price= 8 EGP ( 4 EGP for people at 60 or older , military) \n  (5 EGP for Disability)\n"
            in 10..16 -> result+="Ticket Price= 10 EGP ( 5 EGP for people at 60 or older , military) \n  (5 EGP for Disability)\n"
            in 17..23 -> result+="Ticket Price= 15 EGP ( 8 EGP for people at 60 or older , military) \n ( 5 EGP for Disability)\n"
            else -> result+="Ticket Price= 20 EGP ( 10 EGP for people at 60 or older , military) \n  (5 EGP for Disability)\n"
        }
        //println("Estimated Time: ~${totalStationNumber*2} minutes" )
        result+="Estimated Time: ~${totalStationNumber*2} minutes\n \n"
        return result
    }

    fun changeLine(previousLine: List<String>, currentStation:String,nextStation: String):List<String>{
        if (previousLine.contains("helwan") && (currentStation=="el-sadat" || currentStation=="el-shohadaa"))
            return line2Stations
        else if (previousLine.contains("helwan") && currentStation=="gamal abdel-nasser")
            if(line3Stations.contains(nextStation))
                return line3Stations
            else return line4Stations
        else if(previousLine.contains("el-monib") && (currentStation=="el-sadat" || currentStation=="el-shohadaa"))
            return line1Stations
        else if(previousLine.contains("el-monib") && currentStation=="cairo university" )
            return line4Stations
        else if(previousLine.contains("el-monib") && currentStation=="attaba")
            if(line3Stations.contains(nextStation))
                return line3Stations
            else return line4Stations
        else if(previousLine.contains("rod el-farag corr") && currentStation=="attaba" )
            return line2Stations
        else if(previousLine.contains("rod el-farag corr") && currentStation=="gamal abdel-nasser" )
            return line1Stations
        else if(previousLine.contains("bulaq el-dakrour") && (currentStation=="attaba" || currentStation=="cairo university"))
            return line2Stations
        else                        // in line 4 and transition at nasser
            return line1Stations
    }

    fun getLineNumber(currentLine: List<String>):Int{
        if (currentLine[0]=="helwan") return 1
        else if (currentLine[0]=="el-monib") return 2
        else return 3
    }

}