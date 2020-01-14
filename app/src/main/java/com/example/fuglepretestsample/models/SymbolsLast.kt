package com.example.fuglepretestsample.models

/**
 * 股票交易數據
 * */
data class SymbolsLast(
    val symbol: String,
    val price: Float,
    val size: Int,
    val time: Long
)