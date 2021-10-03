package ru.geeekbrains.princeschdailypicture.ui.listTODO

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.data.listTODO.Note
import ru.geeekbrains.princeschdailypicture.data.listTODO.OnListItemClickListener
import ru.geeekbrains.princeschdailypicture.databinding.FragmentListBinding
import ru.geeekbrains.princeschdailypicture.ui.main.hide
import ru.geeekbrains.princeschdailypicture.ui.main.show
import ru.geeekbrains.princeschdailypicture.ui.main.showMessage

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() {
            return _binding!!
        }

    val note: MutableList<Note> = ArrayList()

    private val adapter = ListRecyclerAdapter(
        object : OnListItemClickListener {
            override fun onItemClick(note: Note) {
                binding.listRecycler.showMessage(note.header)
            }
        }, note
    )

    private var isAddFragmentVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        note.add(Note("Header", null))
        note.add(Note("Header1", "Body1"))
        note.add(Note("Header2", "Body2"))
        note.add(Note("Header4", "Body3"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            listRecycler.adapter = adapter
            listRecyclerFAB.setOnClickListener {
                isAddFragmentVisible = !isAddFragmentVisible
                if (isAddFragmentVisible) {
                    listRecyclerNote.show()
                    listRecyclerFAB.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_fab)
                    )
                } else {
                    listRecyclerFAB.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_plus_fab)
                    )
                    listRecyclerNote.hide()
                }
            }
            newNoteButton.setOnClickListener {
                note.add(1, Note(newNoteHeader.text.toString(), newNoteBody.text.toString()))
                adapter.notifyDataSetChanged()
                isAddFragmentVisible = !isAddFragmentVisible
                listRecyclerFAB.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_plus_fab)
                )
                listRecyclerNote.hide()
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(listRecycler.windowToken, 0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}