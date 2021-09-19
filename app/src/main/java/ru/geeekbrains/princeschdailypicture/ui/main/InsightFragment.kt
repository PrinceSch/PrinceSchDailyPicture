package ru.geeekbrains.princeschdailypicture.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geeekbrains.princeschdailypicture.databinding.FragmentInsightBinding

class InsightFragment : Fragment() {

    var _binding: FragmentInsightBinding? = null
    val binding: FragmentInsightBinding
        get() {
            return _binding!!
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentInsightBinding.inflate(inflater)
        return  binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = InsightFragment()
    }
}