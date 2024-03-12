package com.vk.domain.usecase

import androidx.paging.PagingData
import com.vk.domain.models.Product
import com.vk.domain.repository.ProductRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase @Inject constructor(
  private val repository: ProductRepository
) {

  operator fun invoke(): Flow<PagingData<Product>> = repository.getProducts()
}