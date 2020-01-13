package com.example.lib_network

import java.util.HashMap

/**
 * RetrofitDelegate 對外接口
 * */
interface IRetrofitDelegate {

    /**
     * 創建網路請求Service
     * */
    fun <T> create(service: Class<T>): T

    /**
     * 設置 Header 請求頭
     * */
    fun setHeader(headers: HashMap<String, String>)
}