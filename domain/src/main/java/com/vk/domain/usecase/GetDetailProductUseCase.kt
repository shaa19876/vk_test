package com.vk.domain.usecase

import com.vk.domain.models.DetailProduct
import com.vk.domain.repository.ProductRepository
import com.vk.domain.repository.RequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDetailProductUseCase @Inject constructor(
  private val repository: ProductRepository
) {
  operator fun invoke(id: Int): Flow<RequestResult<DetailProduct>> = repository.getDetailProduct(id)
}