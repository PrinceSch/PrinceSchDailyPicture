package ru.geeekbrains.princeschdailypicture.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.data.AppState
import ru.geeekbrains.princeschdailypicture.databinding.FragmentMarsroverBinding
import ru.geeekbrains.princeschdailypicture.viewmodel.MarsRoverViewModel

class MarsRoverFragment : Fragment() {

    private var _binding: FragmentMarsroverBinding? = null
    private val binding: FragmentMarsroverBinding
        get() {
            return _binding!!
        }

    private val viewModel: MarsRoverViewModel by lazy {
        ViewModelProvider(this).get(MarsRoverViewModel::class.java)
    }

    private var dateForAPI: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentMarsroverBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})

        with(binding){
            marsButton.setOnClickListener {
                val day = marsRoverDatePicker.dayOfMonth
                val month = marsRoverDatePicker.month+1
                val year = marsRoverDatePicker.year

                dateForAPI = if (month < 10) {
                    "${year}-0${month}-${day}"
                } else {
                    "${year}-${month}-${day}"
                }
                viewModel.getMarsRoverPhotosFromServer(dateForAPI!!)
            }
        }

    }

    private fun renderData(data: AppState) {

        when(data){
            is AppState.Loading -> {
                binding.loadingLayout.loadingLayout.show()
            }
            is AppState.Error -> {
                with(binding) {
                    rootMarsFragment.showMessage(data.error.toString())
                }
            }
            is AppState.SuccessMarsRover -> {
                val serverResponseData = data.serverResponseDataMR.photos
                val image = serverResponseData[0].imgSrc
                //TODO обработать все элементы массива. доработать разные камеры, чтоб не падало при отсутвии фото с curiosity в выбранный день
                if (image.isNullOrEmpty()) {
                    with(binding) {
                        rootMarsFragment.showMessage(getString(R.string.url_null_or_empty))
                    }
                } else {
                    with(binding){
                        loadingLayout.loadingLayout.hide()
                        marsRoverImageView.load(image){
                            error(R.drawable.ic_no_photo_vector)
                        }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}