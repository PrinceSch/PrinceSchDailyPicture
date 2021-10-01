package ru.geeekbrains.princeschdailypicture.data.listTODO

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ru.geeekbrains.princeschdailypicture.R
import ru.geeekbrains.princeschdailypicture.databinding.RecyclerHeaderBinding
import ru.geeekbrains.princeschdailypicture.databinding.RecyclerItemNoteBinding
import ru.geeekbrains.princeschdailypicture.databinding.RecyclerItemPhotoBinding

class ListRecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var note: List<Note>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NOTE -> {
                val binding: RecyclerItemNoteBinding = RecyclerItemNoteBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                NoteViewHolder(binding.root)
            }
            TYPE_PHOTO -> {
                val binding: RecyclerItemPhotoBinding = RecyclerItemPhotoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                PhotoViewHolder(binding.root)
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
        if (position == 0) return TYPE_HEADER
        return if (note[position].body.isNullOrBlank()) TYPE_PHOTO else TYPE_NOTE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_NOTE -> {
                (holder as NoteViewHolder).bind(note[position])
            }
            TYPE_PHOTO -> {
                (holder as PhotoViewHolder).bind(note[position])
            }
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind(note[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return note.size
    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                RecyclerItemNoteBinding.bind(itemView).apply {
                    noteHeader.text = note.header
                    noteBody.text = note.body
                    root.setOnClickListener {
                        onListItemClickListener.onItemClick(note)
                    }
                }
            }
        }
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                RecyclerItemPhotoBinding.bind(itemView).apply {
                    photoHeader.text = note.header
                    photoBody.load(note.photoURL) {
                        error(R.drawable.ic_no_photo_vector)
                        placeholder(R.drawable.loading_animation)
                    }
                    root.setOnClickListener {
                        onListItemClickListener.onItemClick(note)
                    }
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            RecyclerHeaderBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(note)
                }
            }
        }
    }


    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_PHOTO = 1
        private const val TYPE_NOTE = 2
    }

}