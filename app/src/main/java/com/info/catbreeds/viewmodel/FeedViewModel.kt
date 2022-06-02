package com.info.catbreeds.viewmodel

import android.app.Application
import android.widget.Toast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.info.catbreeds.model.Cat
import com.info.catbreeds.service.CatAPIService
import com.info.catbreeds.service.CatDatabase
import com.info.catbreeds.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val catAPIService = CatAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val cats = MutableLiveData<List<Cat>>()
    val catError = MutableLiveData<Boolean>()
    val catLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        }else {
            getDataFromAPI()
        }
    }

    fun refreshFromAPI() {
        getDataFromAPI()

    }

    private fun getDataFromSQLite() {
        catLoading.value = true
        launch {
            val cats = CatDatabase(getApplication()).catDao().getAllCats()
            showCat(cats)
            Toast.makeText(getApplication(), "Cats From SQLite", Toast.LENGTH_LONG).show()
        }
    }



    private fun getDataFromAPI() {

        catLoading.value = true
        disposable.add(
            catAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Cat>>() {
                    override fun onSuccess(t: List<Cat>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Cats From API", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        catLoading.value = false
                        catError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun showCat(catList : List<Cat>) {
        cats.value = catList
        print(cats)
        catError.value = false
        catLoading.value = false
    }

    private fun storeInSQLite(list: List<Cat>) {

        launch {
            val dao = CatDatabase(getApplication()).catDao()
            dao.deleteAllCats()
            var listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i+1
            }
            showCat(list)
        }

        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }





}