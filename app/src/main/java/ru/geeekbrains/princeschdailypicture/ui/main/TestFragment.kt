package ru.geeekbrains.princeschdailypicture.ui.main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import ru.geeekbrains.princeschdailypicture.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    var _binding: FragmentTestBinding? = null
    val binding: FragmentTestBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater)
        return binding.root
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

class ViewAppBarBehavior @JvmOverloads constructor(
    context: Context? = null, attrs: AttributeSet? = null
) : CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) = dependency is AppBarLayout

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val appBar = dependency as AppBarLayout
        val currentAppBarHeight = appBar.height + appBar.y
        val parentHeight = parent.height
        val placeHolderHeight = (parentHeight - currentAppBarHeight).toInt()
        appBar.alpha = (currentAppBarHeight/appBar.height)
        child.layoutParams?.height = placeHolderHeight
        child.requestLayout()
        return false
    }
}