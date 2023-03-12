package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel


// Função: É quem lida com manipulação do banco de dados
class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    companion object {
        /*** Singleton explicação
         * É uma forma de garantir que algo seja executado APENAS uma vez
         * Nesse caso na primeira vez que exevutar esse código, será setado a instancia de GuestRepository
         * Mas se for chamado mais uma vez apenas retornará o próprio repository
         *
         *
        private lateinit var repository : GuestRepository
        fun getInstance(): GuestRepository {
        if (!::repository.isInitialized){
        repository = GuestRepository()
        }
        return repository
        }
         *
         * */
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presenca) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            if (values["name"] != "") {
                db.insert("Guest", null, values)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel) {
        val db = guestDataBase.writableDatabase
        val presence = if (guest.presenca) 1 else 0

        val values = ContentValues()

        values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
        values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)

        val selection = DataBaseConstants.GUEST.COLUMNS.ID + ""

        val args = arrayOf(guest.id.toString())

        db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)

    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + "= ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("Range")
    fun getGuests(valueOfPresence: String) : List<GuestModel>{

        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase

            val column = mapOf(
                "id" to DataBaseConstants.GUEST.COLUMNS.ID,
                "name" to DataBaseConstants.GUEST.COLUMNS.NAME,
                "presence" to DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.rawQuery("Select * from Guest where presence =$valueOfPresence", null)
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(column["id"]))
                    val name = cursor.getString(cursor.getColumnIndex(column["name"]))
                    val presence = cursor.getInt(cursor.getColumnIndex(column["presence"]))
                    list.add(GuestModel(id, name, presence.toString() == valueOfPresence))
                }
            }
            cursor.close()
        } catch (e: Exception ){
            return list
        }
        return list
    }

}