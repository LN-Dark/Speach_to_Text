package com.blackmoon.speachtotext

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.speech.RecognizerIntent
import android.view.View
import android.widget.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var txvResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnSpeak: ImageView = findViewById(R.id.btnSpeak)
        val btn_limpar: Button = findViewById(R.id.btn_limpar)
        txvResult = findViewById(R.id.txvResult)
        txvResult!!.setSelectAllOnFocus(true)
        btnSpeak.setOnClickListener(View.OnClickListener {
            getSpeechInput()
        })
        btn_limpar.setOnClickListener {
            txvResult!!.text = ""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val txvResult: TextView = findViewById(R.id.txvResult)
        when (requestCode) {
            10 -> if (resultCode == RESULT_OK &&
                data != null)
            {
                val result =
                    data.
                    getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS)
                txvResult.text = txvResult.text.toString() + " " + result!![0]
            }
        }
    }

    private fun getSpeechInput()
    {
        val intent = Intent(
            RecognizerIntent
            .ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault())

        if (intent.resolveActivity(packageManager) != null)
        {
            startActivityForResult(intent, 10)
        } else
        {
            Toast.makeText(this,
                "Your Device Doesn't Support Speech Input",
                Toast.LENGTH_SHORT)
                .show()
        }
    }
}