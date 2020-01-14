package com.example.fuglepretestsample.models

/**
 * 股票代號
 * */
data class Symbols(
    val symbol: String,
    val name: String,
    val date: String,
    val isEnabled: Boolean,
    val type: String,
    val iexId: String
)