package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Spinner
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var dropDown:Spinner

    private var Started: Boolean = false

    private val timeTicks = 1000L

    private val Time = arrayOf<Long>(
        1800000L,
        3600000L,
        5400000L,
        7200000L
    )
    private var timeToCountDownInMs = Time[0]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dropDown = findViewById<View>(R.id.dropdown_menuTimes) as Spinner
        val Arraya = ArrayAdapter(this, android.R.layout.simple_spinner_item, Time)
        Arraya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(dropDown) {
            adapter = Arraya
            setSelection(0, false)
            onItemSelectedListener = this@MainActivity
            prompt = "Pleaes select interval"
            gravity = Gravity.CENTER
        }

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }
       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    private fun startCountDown(v: View){
        if (Started) {
            Toast.makeText(this@MainActivity, "Timer has allready been started", Toast.LENGTH_SHORT).show()
            return
        }


        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Timer is done", Toast.LENGTH_SHORT).show()
                Started = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
        Started = true
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (Started) {
            timer.cancel()
            Started = false
        }

        Toast.makeText(this@MainActivity, "Timer for ${(Time[position] / (1000 * 60))} minutes", Toast.LENGTH_SHORT).show()
        timeToCountDownInMs = Time[position]
        updateCountDownDisplay(timeToCountDownInMs)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}