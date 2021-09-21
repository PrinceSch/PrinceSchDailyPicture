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
    val binding: FragmentEpicBinding
        get() {
            return _binding!!
        }

    private val viewModel: EpicViewModel by lazy {
        ViewModelProvider(this).get(EpicViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentEpicBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
        viewModel.getEpicDataFromServer("2021-09-17")
    }

    private fun renderData(data: AppState) {
        when (data){

            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                with(binding) {
                    rootEpicFragment.showMessage(data.error.toString())
                }
            }

            is AppState.SuccessEPIC -> {
                val serverResponseData = data.serverResponseDataEPIC[0]
                val image = serverResponseData.image
                if (image.isNullOrEmpty()){
                    with(binding) {
                        rootEpicFragment.showMessage(getString(R.string.url_null_or_empty))
                    }
                } else {
                    with(binding){
                        loadingLayout.hide()
                        epicDatePicker.updateDate(2021,9,17)
                        epicImageView.load("https://epic.gsfc.nasa.gov/archive/natural/2021/09/17/jpg/${serverResponseData.image}.jpg"){
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

    companion object {
        fun newInstance() = EpicFragment()
    }

}
