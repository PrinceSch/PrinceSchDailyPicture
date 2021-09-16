package ru.geeekbrains.princeschdailypicture.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.geeekbrains.princeschdailypicture.MainActivity
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.databinding.FragmentMainBinding
import ru.geeekbrains.princeschdailypicture.repository.AppState

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var isMain = true
    private var isHD = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        setActionBar()
        return binding.root
    }

    private fun setActionBar() {
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            with(binding) {
                if (isMain) {
                    isMain = false
                    bottomAppBar.navigationIcon = null // лучше придумать замену бургеру
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_back_fab
                        )
                    )
                    binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                } else {
                    isMain = true
                    binding.bottomAppBar.navigationIcon =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_menu_24
                        )
                    binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    binding.fab.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_plus_fab
                        )
                    )
                    binding.bottomAppBar.replaceMenu(R.menu.menu_app_bar)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.sendServerRequest()

        bottomSheetBehavior = BottomSheetBehavior.from(binding.includeLayout.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

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
        }

    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    with(binding) {
                        main.showMessage(getString(R.string.url_null_or_empty))
                    }
                } else {
                    with(binding) {
                        loadingLayout.hide()
                        includeLayout.bottomSheetDescriptionHeader.text =
                            data.serverResponseData.title
                        includeLayout.bottomSheetDescription.text =
                            data.serverResponseData.explanation
                        if (isHD) {
                            imageView.load(data.serverResponseData.hdurl) {
                                error(R.drawable.ic_no_photo_vector)
                            }
                        } else {
                            imageView.load(data.serverResponseData.url) {
                                error(R.drawable.ic_no_photo_vector)
                            }
                        }
                    }
                }
            }
            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                with(binding) {
                    main.showMessage(data.toString())
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, getString(R.string.favourite), Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ChipsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}