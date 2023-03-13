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

    fun update(guest: GuestModel): Boolean {
        return try {
           val db = guestDataBase.writableDatabase
           val presence = if (guest.presenca) 1 else 0

           val contentValues = ContentValues()
           contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
           contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)

           val selection = DataBaseConstants.GUEST.COLUMNS.ID + "= ?"

           val args = arrayOf(guest.id.toString())

           db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)
           true
       } catch (e: Exception) {
            false
        }

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
    fun getAll(valueOfPresence: String): List<GuestModel> {

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
        } catch (e: Exception) {
            return list
        }
        return list
    }

    @SuppressLint("Range")
    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null
        return try {
            val db = guestDataBase.readableDatabase

            // Colunas que serão retornadas
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            // Filtro
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null, null, null
            )

            // Verifica se existem dados no cursor
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name, presence)
            }

            cursor?.close()
            guest
        } catch (e: Exception) {
            guest
        }
    }

    @SuppressLint("Range")
    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            val db = guestDataBase.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    /**
     * Faz a listagem de todos os convidados presentes
     */
    @SuppressLint("Range")
    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            val db = guestDataBase.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

}