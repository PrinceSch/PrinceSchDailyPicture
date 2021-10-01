package ru.geeekbrains.princeschdailypicture

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.geeekbrains.princeschdailypicture.databinding.ActivityMainBinding
import ru.geeekbrains.princeschdailypicture.ui.listTODO.ListFragment
import ru.geeekbrains.princeschdailypicture.ui.main.*
import ru.geeekbrains.princeschdailypicture.ui.test.TestFragment

var THEME_ID = R.style.MainTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(THEME_ID)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.bottomAppBar)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.app_bar_list -> {
                binding.viewPager.adapter = null
                binding.tabLayout.hide()
                this@MainActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }

            R.id.app_bar_settings -> {
                binding.viewPager.adapter = null
                binding.tabLayout.hide()
                this@MainActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }
            R.id.app_bar_test -> {
                binding.viewPager.adapter = null
                binding.tabLayout.hide()
                this@MainActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, TestFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(this@MainActivity.supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}