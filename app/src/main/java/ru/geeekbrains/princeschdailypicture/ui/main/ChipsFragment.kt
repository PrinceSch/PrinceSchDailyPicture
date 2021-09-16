package ru.geeekbrains.princeschdailypicture.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.geeekbrains.princeschdailypicture.databinding.FragmentChipsBinding

class ChipsFragment: Fragment() {

    var _bindong: FragmentChipsBinding? = null
    val binding: FragmentChipsBinding
        get() {
            return _bindong!!
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindong =  FragmentChipsBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener{childGroup,position->
            Toast.makeText(context,"Click $position",Toast.LENGTH_SHORT).show()
        }
        binding.chipWithClose.setOnCloseIconClickListener {
            Toast.makeText(context,"Click on chipWithClose",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindong = null
    }

    companion object {
        fun newInstance() = ChipsFragment()
    }
}