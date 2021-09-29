package ru.geeekbrains.princeschdailypicture.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.data.AppState
import ru.geeekbrains.princeschdailypicture.databinding.FragmentMainBinding
import ru.geeekbrains.princeschdailypicture.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var isHD = false
    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.sendServerRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        with(binding) {
            chipHd.setOnClickListener {
                isHD = !isHD
                viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
                viewModel.sendServerRequest()
            }

            imageView.setOnClickListener {
                isExpanded = !isExpanded
                val set = TransitionSet()
                set.addTransition(ChangeBounds())
                set.addTransition(ChangeImageTransform())

                TransitionManager.beginDelayedTransition(main, set)
                imageView.scaleType = if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            }
        }

    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessPOD -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val hdurl = serverResponseData.hdurl
                if (url.isNullOrEmpty() && hdurl.isNullOrEmpty()) {
                    with(binding) {
                        main.showMessage(getString(R.string.url_null_or_empty))
                    }
                } else setData(data)
            }
            is AppState.Loading -> {
                binding.includedLayout.loadingLayout.show()
            }
            is AppState.Error -> {
                with(binding) {
                    main.showMessage(data.toString())
                }
            }
        }
    }

    private fun setData(dataPOD: AppState.SuccessPOD){
        val url = dataPOD.serverResponseData.url
        val hdurl = dataPOD.serverResponseData.hdurl
        if (!url.isNullOrEmpty() && hdurl.isNullOrEmpty()){
            with(binding){
                imageView.hide()
                videoURL.show()
                videoURL.text = getString(R.string.dailyVideo)
                videoURL.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    })
                }
            }
        } else {
            with(binding) {
                includedLayout.loadingLayout.hide()
                descriptionHeader.text =
                    dataPOD.serverResponseData.title
                description.text =
                    dataPOD.serverResponseData.explanation
                if (isHD) {
                    imageView.load(hdurl) {
                        error(R.drawable.ic_no_photo_vector)
                        placeholder(R.drawable.loading_animation)
                    }
                } else {
                    imageView.load(url) {
                        error(R.drawable.ic_no_photo_vector)
                        placeholder(R.drawable.loading_animation)
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
        fun newInstance() = MainFragment()
    }

}