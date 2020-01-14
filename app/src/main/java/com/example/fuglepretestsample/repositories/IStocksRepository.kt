package com.example.fuglepretestsample.repositories

import com.example.fuglepretestsample.models.Symbols
import com.example.fuglepretestsample.models.SymbolsLast
import com.example.lib_network.interfaces.IApiCallback

interface IStocksRepository {
    /**
     * 取得股票清單
     * */
    fun getSymbols(callback: IApiCallback<List<Symbols>>)

    /**
     * 取得股票股價及更新時間
     * @param symbols 股票代號
     * */
    fun getSymbolsLast(callback: IApiCallback<List<SymbolsLast>>, vararg symbols: String)

    fun destroyAllEvent()
}