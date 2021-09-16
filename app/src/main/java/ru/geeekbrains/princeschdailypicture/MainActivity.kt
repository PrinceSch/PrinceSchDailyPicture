package ru.geeekbrains.princeschdailypicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geeekbrains.princeschdailypicture.ui.main.MainFragment

var THEME_ID = R.style.MainTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTheme(THEME_ID)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}