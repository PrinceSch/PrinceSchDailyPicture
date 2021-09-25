package ru.geeekbrains.princeschdailypicture.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geeekbrains.princeschdailypicture.databinding.FragmentTestBinding

class TestFragment: Fragment() {

    var _binding: FragmentTestBinding? = null
    val binding: FragmentTestBinding
        get() {
            return _binding!!
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentTestBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = TestFragment()
    }
}