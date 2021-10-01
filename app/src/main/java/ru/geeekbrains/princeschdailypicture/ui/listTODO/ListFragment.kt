package ru.geeekbrains.princeschdailypicture.ui.listTODO

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geeekbrains.princeschdailypicture.data.listTODO.ListRecyclerAdapter
import ru.geeekbrains.princeschdailypicture.data.listTODO.Note
import ru.geeekbrains.princeschdailypicture.data.listTODO.OnListItemClickListener
import ru.geeekbrains.princeschdailypicture.databinding.FragmentListBinding
import ru.geeekbrains.princeschdailypicture.ui.main.showMessage

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() {
            return _binding!!
        }

    val note = listOf(
        Note("Header", null, null),
        Note("Header1", "Body1", null),
        Note("Header2", "Body2", null),
        Note("Header3", null, "https://data.whicdn.com/images/322158565/original.jpg"),
        Note("Header4", "Body3", null),
        Note("Header5", null, "https://fwcdn.pl/fcp/96/91/199691/11252.1.jpg"),
        Note("Header6", null, "https://pbs.twimg.com/profile_images/887133241307746304/QN2o4BKE.jpg")
    )

    private val adapter = ListRecyclerAdapter(
        object : OnListItemClickListener{
            override fun onItemClick(note: Note) {
                binding.listRecycler.showMessage(note.header)
            }
        }, note
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listRecycler.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}