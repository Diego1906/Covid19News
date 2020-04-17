package br.com.covid19news.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.covid19news.R
import br.com.covid19news.application.CovidApplication

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CovidApplication.setContext(applicationContext)
    }
}
