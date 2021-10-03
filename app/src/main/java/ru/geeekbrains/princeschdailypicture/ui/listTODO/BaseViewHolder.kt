package ru.geeekbrains.princeschdailypicture.ui.listTODO

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.geeekbrains.princeschdailypicture.data.listTODO.Note

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(note: Note)
}