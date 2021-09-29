package ru.geeekbrains.princeschdailypicture.ui.test

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.*
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.databinding.FragmentTestBinding


class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding: FragmentTestBinding
        get() {
            return _binding!!
        }
    private var textAutoIsVisible = false
    private var textSlideIsVisible = false
    private var textFadeIsVisible = false
    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonAuto.setOnClickListener {
                TransitionManager.beginDelayedTransition(transitionsContainer)
                textAutoIsVisible = !textAutoIsVisible
                textAuto.visibility = if (textAutoIsVisible) View.VISIBLE else View.INVISIBLE
            }

            buttonSlide.setOnClickListener {
                TransitionManager.beginDelayedTransition(transitionsContainer, Slide(Gravity.END))
                textSlideIsVisible = !textSlideIsVisible
                textSlide.visibility = if (textSlideIsVisible) View.VISIBLE else View.INVISIBLE
            }

            buttonFade.setOnClickListener {
                TransitionManager.beginDelayedTransition(transitionsContainer, Fade())
                textFadeIsVisible = !textFadeIsVisible
                textFade.visibility = if (textFadeIsVisible) View.VISIBLE else View.INVISIBLE
            }

            buttonExplode.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ExplodeTestFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }

            expandImageView.setOnClickListener {
                isExpanded = !isExpanded
                val set = TransitionSet()
                set.addTransition(ChangeBounds())
                set.addTransition(ChangeImageTransform())

                TransitionManager.beginDelayedTransition(transitionsContainer, set)
                expandImageView.scaleType = if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            }

            buttonShuffle.setOnClickListener {
                shuffle()
            }
        }
    }

    private fun shuffle() {
        val titles:MutableList<String> = ArrayList()
        for(i in 0..4){
            titles.add("Item $i")
        }

        with(binding){
            TransitionManager.beginDelayedTransition(containerShuffle, ChangeBounds())
            containerShuffle.removeAllViews()
            titles.shuffle()
            for (title in titles){
                containerShuffle.addView(TextView(context).apply {
                    text = title
                    ViewCompat.setTransitionName(this, title)
                    gravity = Gravity.CENTER_HORIZONTAL
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = TestFragment()
    }
}