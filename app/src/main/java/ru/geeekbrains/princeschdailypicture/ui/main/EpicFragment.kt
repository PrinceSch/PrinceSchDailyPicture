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
import ru.geeekbrains.princeschdailypicture.databinding.FragmentEpicBinding
import ru.geeekbrains.princeschdailypicture.viewmodel.EpicViewModel

class EpicFragment : Fragment() {

    private var _binding: FragmentEpicBinding? = null
    private val binding: FragmentEpicBinding
        get() {
            return _binding!!
        }

    private val viewModel: EpicViewModel by lazy {
        ViewModelProvider(this).get(EpicViewModel::class.java)
    }

    private var dateForAPI: String? = null
    private var dateForImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpicBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })

        with(binding) {
            epicButton.setOnClickListener {
                val day = epicDatePicker.dayOfMonth
                val month = epicDatePicker.month + 1
                val year = epicDatePicker.year

                if (month < 10) {
                    dateForAPI = "${year}-0${month}-${day}"
                    dateForImage = "${year}/0${month}/${day}"
                } else {
                    dateForAPI = "${year}-${month}-${day}"
                    dateForImage = "${year}/${month}/${day}"
                }

                viewModel.getEpicDataFromServer(dateForAPI!!)
            }
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Loading -> {
                binding.epicImageView.load(R.drawable.loading_animation)
            }
            is AppState.Error -> {
                with(binding) {
                    epicText.text = data.error.toString()
                    epicText.show()
                }
            }
            is AppState.SuccessEPIC -> {
                if (data.serverResponseDataEPIC.isEmpty()) {
                    with(binding){
                        epicText.text = getString(R.string.empty_array)
                        epicText.show()
                    }
                } else {
                    val serverResponseData = data.serverResponseDataEPIC[0]
                    //TODO обработать все элементы массива
                    val image = serverResponseData.image
                    if (image.isNullOrEmpty()) {
                        with(binding) {
                            epicText.text = getString(R.string.url_null_or_empty)
                            epicText.show()
                        }
                    } else {
                        with(binding) {
                            epicImageView.load("https://epic.gsfc.nasa.gov/archive/natural/${dateForImage}/jpg/${serverResponseData.image}.jpg") {
                                error(R.drawable.ic_no_photo_vector)
                                placeholder(R.drawable.loading_animation)
                            }
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
