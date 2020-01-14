package com.example.fuglepretestsample.api

import com.example.fuglepretestsample.models.Symbols
import com.example.fuglepretestsample.models.SymbolsLast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StocksSevice {

    @GET("ref-data/symbols")
    fun getSymbols(): Observable<List<Symbols>>

    @GET("tops/last")
    fun getLast(@Query("symbols") symbols: String): Observable<List<SymbolsLast>>
}