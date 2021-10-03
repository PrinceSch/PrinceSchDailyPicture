package ru.geeekbrains.princeschdailypicture.ui.listTODO

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geeekbrains.princeschdailypicture.data.listTODO.Note
import ru.geeekbrains.princeschdailypicture.data.listTODO.OnListItemClickListener
import ru.geeekbrains.princeschdailypicture.databinding.RecyclerHeaderBinding
import ru.geeekbrains.princeschdailypicture.databinding.RecyclerItemNoteBinding

class ListRecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var note: MutableList<Note>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_NOTE -> {
                val binding: RecyclerItemNoteBinding = RecyclerItemNoteBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                NoteViewHolder(binding.root)
            }
            else -> {
                val binding: RecyclerHeaderBinding = RecyclerHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                HeaderViewHolder(binding.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_NOTE
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_NOTE -> {
                (holder as NoteViewHolder).bind(note[position])
            }
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind(note[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return note.size
    }


    inner class NoteViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(note: Note) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                RecyclerItemNoteBinding.bind(itemView).apply {
                    noteHeader.text = note.header
                    noteBody.text = note.body
                    noteAdd.setOnClickListener { addNote() }
                    noteDelete.setOnClickListener { removeItem() }
                }
            }
        }

        private fun removeItem() {
            note.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun addNote() {
            note.add(layoutPosition, Note("HeaderNew", "BodyNew"))
            notifyItemInserted(layoutPosition)
        }
    }


    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(note: Note) {
            RecyclerHeaderBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(note)
                }
            }
        }
    }


    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_NOTE = 2
    }

}