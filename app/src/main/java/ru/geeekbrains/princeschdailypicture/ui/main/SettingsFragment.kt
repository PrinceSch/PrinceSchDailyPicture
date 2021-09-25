package ru.geeekbrains.princeschdailypicture.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import ru.geeekbrains.princeschdailypicture.MainActivity
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.THEME_ID
import ru.geeekbrains.princeschdailypicture.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            changeThemeMain.setOnClickListener {
                activity?.let {
                    it as MainActivity
                    THEME_ID = R.style.MainTheme
                    it.supportFragmentManager.popBackStack()
                    it.recreate()
                }
            }

            changeThemeMars.setOnClickListener {
                activity?.let {
                    it as MainActivity
                    THEME_ID = R.style.MarsTheme
                    it.supportFragmentManager.popBackStack()
                    it.recreate()
                }
            }

            changeThemeVenus.setOnClickListener {
                activity?.let {
                    it as MainActivity
                    THEME_ID = R.style.VenusTheme
                    it.supportFragmentManager.popBackStack()
                    it.recreate()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}