package com.vk.domain.repository

import androidx.paging.PagingData
import com.vk.domain.models.DetailProduct
import com.vk.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
  fun getProducts(): Flow<PagingData<Product>>

  fun getDetailProduct(id: Int): Flow<RequestResult<DetailProduct>>
}