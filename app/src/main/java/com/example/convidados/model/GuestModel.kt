package com.example.convidados.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Isso é uma especie de construtor da tabela
@Entity(tableName = "Guest")
class GuestModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")// name é opcional
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "presence")
    var presenca: Boolean = false
}