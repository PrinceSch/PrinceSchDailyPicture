package ru.geeekbrains.princeschdailypicture.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private const val MAIN_FRAGMENT = 0
private const val EPIC_FRAGMENT = 1
private const val INSIGHT_FRAGMENT = 2
private const val MARSROVER_FRAGMENT = 3

class ViewPagerAdapter(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayOf(MainFragment(), EpicFragment(), InsightFragment(), MarsRoverFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> fragments[MAIN_FRAGMENT]
            1 -> fragments[EPIC_FRAGMENT]
            2 -> fragments[INSIGHT_FRAGMENT]
            3 -> fragments[MARSROVER_FRAGMENT]
            else -> fragments[MAIN_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "MAIN_FRAGMENT"
            1 -> "EPIC_FRAGMENT"
            2 -> "INSIGHT_FRAGMENT"
            3 -> "MARSROVER_FRAGMENT"
            else -> "MAIN_FRAGMENT"
        }
    }
}