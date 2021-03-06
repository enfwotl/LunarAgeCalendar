/* Lunar Age Calendar - Moon Api
* Kim Hyun
* Api (Service, Client)
* Send request, return instance
* */
package com.example.lunaragecalendar.api

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("getLunPhInfo")
    fun getInfo(@Query("serviceKey")serviceKey: String,
                @Query("solYear")year: String,
                @Query("solMonth")month:String,
                @Query("solDay")day:String): Call<XmlResponse>
}

object RetrofitClient {
    private const val serviceKey: String = "yv9hVU93f4PPNexTn2n3fVhmnhCEgcp85Om7WNIDuok/wFMX9L4ZlmHB8mI0zCqdPFYB3K5hDgaARJ2d4ko3cQ=="
    private const val baseUrl: String = "http://apis.data.go.kr/B090041/openapi/service/LunPhInfoService/"
    private var instance: Retrofit? = null

    fun getInstance(): Retrofit {
        if (instance == null)
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .callFactory(OkHttpClient.Builder().build())
                .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
                .build()
        return instance!!
    }

    fun getServiceKey(): String {
        return serviceKey
    }
}

