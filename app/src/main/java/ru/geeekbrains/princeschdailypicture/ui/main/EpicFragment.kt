package ru.geeekbrains.princeschdailypicture.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geeekbrains.princeschdailypicture.databinding.FragmentEpicBinding

class EpicFragment : Fragment() {

    var _binding: FragmentEpicBinding? = null
    val binding: FragmentEpicBinding
        get() {
            return _binding!!
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentEpicBinding.inflate(inflater)
        return  binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = EpicFragment()
    }

}
