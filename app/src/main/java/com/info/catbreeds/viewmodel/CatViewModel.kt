package com.info.catbreeds.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.info.catbreeds.model.Cat
import com.info.catbreeds.service.CatDatabase
import kotlinx.coroutines.launch
import java.util.*

class CatViewModel(application: Application) : BaseViewModel(application) {

    val catLiveData = MutableLiveData<Cat>()

    fun getDataFromRoom(uuid: Int) {
        launch {

            val dao = CatDatabase(getApplication()).catDao()
            val cat = dao.getCat(uuid)
            catLiveData.value = cat
        }
    }
}