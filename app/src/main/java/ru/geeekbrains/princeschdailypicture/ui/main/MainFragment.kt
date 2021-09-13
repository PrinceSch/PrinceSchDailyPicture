package ru.geeekbrains.princeschdailypicture.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.databinding.FragmentMainBinding
import ru.geeekbrains.princeschdailypicture.repository.PictureOfTheDayData

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.sendServerRequest()
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    // TODO Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                        binding.imageView.load(data.serverResponseData.hdurl)
                        {
                            error(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                // TODO Отобразите загрузку
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                // TODO Отобразите ошибку
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newInstance()= MainFragment()
    }

}