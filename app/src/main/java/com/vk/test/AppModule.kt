package com.vk.test

import com.vk.data.api.ProductApi
import com.vk.data.api.productsApi
import com.vk.data.repository.ProductRepositoryImpl
import com.vk.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  private const val BASE_URL = "https://dummyjson.com/"

  @Provides
  @Singleton
  fun provideProductApi(): ProductApi {
    return productsApi(BASE_URL)
  }

  @Provides
  @Singleton
  fun provideRepository(): ProductRepository {
    return ProductRepositoryImpl(api = provideProductApi())
  }
}