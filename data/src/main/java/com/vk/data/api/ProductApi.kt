package com.vk.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import com.vk.data.models.ProductDTO
import com.vk.data.models.ProductResponseDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ProductApi {

  @GET("products")
  suspend fun getAllProducts(
    @Query("skip") skip: Int = 0,
    @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
  ): Result<ProductResponseDTO>

  @GET("products/{id}")
  suspend fun getDetailProduct(
    @Path("id") id: Int,
  ): Result<ProductDTO>

  companion object {
    const val DEFAULT_PAGE_SIZE = 20
  }
}

fun productsApi(baseUrl: String): ProductApi = retrofit(baseUrl).create()

private fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
  .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
  .addCallAdapterFactory(ResultCallAdapterFactory.create())
  .baseUrl(baseUrl)
  .client(okHttpClient())
  .build()

private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
  .addInterceptor(
    HttpLoggingInterceptor()
      .apply { level = HttpLoggingInterceptor.Level.BODY }
  )
  .callTimeout(10, TimeUnit.SECONDS)
  .build()