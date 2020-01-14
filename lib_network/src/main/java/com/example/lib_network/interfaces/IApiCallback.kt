package com.example.lib_network.interfaces

interface IApiCallback<in T> {
    // 開始請求API，可以在此開啟Loading View
    fun onStart()

    // 成功後回調
    fun onSuccess(data: T)

    // 失敗後回調
    fun onFail(throwable: Throwable)

    // 完成請求後回調，不論成功或失敗，可以在此關閉Loading View
    fun onCompleted()
}