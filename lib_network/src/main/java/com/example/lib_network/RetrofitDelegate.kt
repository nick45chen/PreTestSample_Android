package com.example.lib_network

import android.util.Log
import com.example.lib_network.interfaces.IRetrofitDelegate
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Retrofit 網路請求代理封裝
 * */
object RetrofitDelegate : IRetrofitDelegate {

    private const val TAG = "NetWorkHandler"
    private const val BASE_URL = "https://api.iextrading.com/1.0/"
    private const val CONNECT_TIME: Long = 20000


    private val retrofit: Retrofit by lazy {
        createRetrofit(createClient(headers))
    }
    private val headers by lazy { HashMap<String, String>() }

    override fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    override fun setHeader(headers: HashMap<String, String>) {
        this.headers.putAll(headers)
    }

    private fun createClient(headers: Map<String, String>): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
            .readTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
            .writeTimeout(CONNECT_TIME, TimeUnit.MILLISECONDS)
            .addInterceptor(getLogInterceptor())
            .addInterceptor(HttpHeaderInterceptor(headers))
            .build()
    }

    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    // 添加日誌攔截器
    private fun getLogInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                Log.d(TAG, "==========$message")
            }
        ).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    class HttpHeaderInterceptor internal constructor(private val headers: Map<String, String>) :
        Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val build = addHeader(request.newBuilder(), headers)
                .build()
            return chain.proceed(build)
        }

        private fun addHeader(
            request: Request.Builder,
            headers: Map<String, String>?
        ): Request.Builder {
            //添加header
            if (headers != null && headers.isNotEmpty()) {
                for (key in headers.keys) {
                    val value = headers[key]
                    if (value != null)
                        request.addHeader(key, value)
                }
            }
            return request
        }
    }

}