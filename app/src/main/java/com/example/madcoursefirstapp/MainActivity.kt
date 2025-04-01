package com.example.madcoursefirstapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    enum class AnswerEnum{YES,NO,MAYBE}

    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button
    private lateinit var inputName: EditText
    private lateinit var inputAge: EditText
    private lateinit var inputEmail: EditText
    private lateinit var textName: TextView
    private lateinit var textAge: TextView
    private lateinit var textEmail: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSubmit = findViewById(R.id.done)
        btnNext = findViewById(R.id.btnNext)
        inputName = findViewById(R.id.editTextName)
        inputAge = findViewById(R.id.editTextAge)
        inputEmail = findViewById(R.id.editTextEmail)
        textName = findViewById(R.id.textViewName)
        textAge = findViewById(R.id.textViewAge)
        textEmail = findViewById(R.id.textViewEmail)


        btnSubmit.setOnClickListener{
            hideKeyboard()
            textName.text = inputName.text
            textAge.text = inputAge.text
            textEmail.text = inputEmail.text
        }

        btnNext.setOnClickListener{
            val intent = Intent(this,SplashActivity::class.java)
            startActivity(intent)
        }





        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                // Handle fling gesture
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })

        btnSubmit.setOnTouchListener { view, motionEvent -> gestureDetector.onTouchEvent(motionEvent) }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val spokenText = results?.get(0)
            }
        }

//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something")
//        resultLauncher.launch(intent)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                // Handle accelerometer data
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }


    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
    fun showKeyboard() {
        val view: View? = this.currentFocus
        view?.let { v ->
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun answer(answerParameter: AnswerEnum){
        when (answerParameter) {
            AnswerEnum.YES -> {

            }
            AnswerEnum.NO -> {

            }
            AnswerEnum.MAYBE -> {

            }
        }
    }


}