package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class AllGuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val listAllGuests = MutableLiveData<List<GuestModel>>()
    val guests: LiveData<List<GuestModel>> = listAllGuests
    private val repository = GuestRepository.getInstance(application.applicationContext)

    fun getAll() {
        listAllGuests.value = repository.getGuests(DataBaseConstants.GUEST.COLUMNS.PRESENCE)
    }

    fun delete(id:Int) {
        repository.delete(id)
    }

}