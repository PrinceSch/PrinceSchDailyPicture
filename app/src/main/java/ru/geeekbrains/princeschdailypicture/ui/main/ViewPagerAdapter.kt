package ru.geeekbrains.princeschdailypicture.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.geeekbrains.princeschdailypicture.R

private const val MAIN_FRAGMENT = 0
private const val EPIC_FRAGMENT = 1
private const val MARSROVER_FRAGMENT = 2
private const val MAIN_TITLE = "Daily Picture"
private const val EPIC_TITLE = "EPIC"
private const val MARS_TITLE = "Mars Rover Picture"

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayOf(MainFragment(), EpicFragment(), MarsRoverFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[MAIN_FRAGMENT]
            1 -> fragments[EPIC_FRAGMENT]
            2 -> fragments[MARSROVER_FRAGMENT]
            else -> fragments[MAIN_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> MAIN_TITLE
            1 -> EPIC_TITLE
            2 -> MARS_TITLE
            else -> MAIN_TITLE
        }
    }
}