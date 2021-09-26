package ru.geeekbrains.princeschdailypicture

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.geeekbrains.princeschdailypicture.databinding.ActivityMainBinding
import ru.geeekbrains.princeschdailypicture.ui.main.MainFragment
import ru.geeekbrains.princeschdailypicture.ui.main.ViewPagerAdapter

var THEME_ID = R.style.MainTheme

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTheme(THEME_ID)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}