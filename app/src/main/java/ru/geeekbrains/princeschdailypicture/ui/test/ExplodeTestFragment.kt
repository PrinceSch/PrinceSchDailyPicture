package ru.geeekbrains.princeschdailypicture.ui.test

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.databinding.FragmentTestExplodeBinding
import ru.geeekbrains.princeschdailypicture.databinding.ItemTestExplodeBinding

class ExplodeTestFragment : Fragment() {

    private var _binding: FragmentTestExplodeBinding? = null
    private val binding: FragmentTestExplodeBinding
        get() {
            return _binding!!
        }

    private val adapter = MyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestExplodeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.explodeRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ExplodeTestFragment()
    }

    fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView, true)
        explode.duration = 3000

        TransitionManager.beginDelayedTransition(binding.explodeRecyclerView, explode)
        binding.explodeRecyclerView.adapter = null
    }

    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val bind = ItemTestExplodeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return MyViewHolder(bind)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }

        inner class MyViewHolder(val bind: ItemTestExplodeBinding) : RecyclerView.ViewHolder(bind.root)
    }

}