package com.example.convidados.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listner.OnGuestListner

class GuestViewHolder(private val bind: RowGuestBinding, private val listner: OnGuestListner) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name
        bind.textName.setOnClickListener {
            listner.onClick(guest.id)
        }
        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de convidado")
                .setMessage("Tem certeza que deseja remover? ")
                .setPositiveButton("Sim") { dialog, which ->
                    listner.onDelete(guest.id)
                }
                .setNegativeButton("Não", null)
                .create()
                .show()
            true
        }
    }
}