package com.example.fuglepretestsample.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuglepretestsample.models.Stock
import com.example.fuglepretestsample.models.Symbols
import com.example.fuglepretestsample.models.SymbolsLast
import com.example.fuglepretestsample.repositories.StocksRepository
import com.example.lib_network.interfaces.IApiCallback
import java.util.*


class MainViewModel : ViewModel() {

    companion object {
        private const val RANDOM_COUNT = 30
    }

    val isLoadingLiveData: LiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val stocksLiveData: LiveData<List<Stock>> by lazy { MutableLiveData<List<Stock>>() }

    private lateinit var repository: StocksRepository

    /**
     * 注入依賴庫
     * */
    fun injectRepository(repository: StocksRepository) {
        this.repository = repository
    }

    /**
     * 請求 API 呼叫
     * */
    fun requestApi() {
        getStocks()
    }

    private fun getStocks() {
        repository.getSymbols(object : IApiCallback<List<Symbols>> {
            override fun onStart() {
                (isLoadingLiveData as MutableLiveData).postValue(true)
            }

            override fun onSuccess(data: List<Symbols>) {
                getStocksLast(getRandomSymbols(data, RANDOM_COUNT))
            }

            override fun onFail(throwable: Throwable) {
                (isLoadingLiveData as MutableLiveData).postValue(false)
            }

            override fun onCompleted() {
            }
        })
    }

    private fun getStocksLast(symbolsList: List<Symbols>) {
        repository.getSymbolsLast(object : IApiCallback<List<SymbolsLast>> {
            override fun onStart() {
                (isLoadingLiveData as MutableLiveData).postValue(true)
            }

            override fun onSuccess(data: List<SymbolsLast>) {
                // 合併數據並發送
                (stocksLiveData as MutableLiveData).postValue(
                    mergeData(symbolsList, data)
                )
            }

            override fun onFail(throwable: Throwable) {
            }

            override fun onCompleted() {
                (isLoadingLiveData as MutableLiveData).postValue(false)
            }
        }, *symbolsList.map { it.symbol }.toTypedArray())
    }

    /**
     * 從list裡，取得隨機item列表(不重複)
     * */
    private fun <T> getRandomSymbols(data: List<T>, @Suppress("SameParameterValue") times: Int): List<T> {
        val dataList = data.toMutableList()
        val randomList = mutableListOf<T>()
        val rand = Random()
        for (i in 0..times) {
            if (dataList.isEmpty())
                break
            val randomIndex = rand.nextInt(dataList.size)
            randomList.add(dataList.removeAt(randomIndex))
        }

        return randomList
    }

    /**
     * 合併數據
     * */
    private fun mergeData(
        symbolsList: List<Symbols>,
        symbolsLastList: List<SymbolsLast>
    ): List<Stock> {

        // 將 list 轉為 map, key 為股票代號
        val symbolsMap: Map<String, SymbolsLast> =
            symbolsLastList.associateBy({ it.symbol }, { it })
        // 將 symbolsLastList 轉為 stocksList
        // 合併資料
        return symbolsList.map {
            val symbolsLast = symbolsMap[it.symbol]

            Stock(
                name = it.name,
                symbols = it.symbol,
                price = symbolsLast?.price ?: 0.0f,
                time = symbolsLast?.time ?: 0L
            )
        }
    }
}