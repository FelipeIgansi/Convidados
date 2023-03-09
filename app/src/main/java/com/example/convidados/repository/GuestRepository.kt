package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel


// Função: É quem lida com manipulação do banco de dados
class GuestRepository  private constructor(context: Context){

    private val guestDataBase = GuestDataBase(context)

    companion object{
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
        private lateinit var repository : GuestRepository
        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }
    fun insert(guest: GuestModel): Boolean{
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presenca) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            db.insert("Guest", null, values)
            true
        }catch (e:Exception){
            false
        }
    }
    fun update(){}
}