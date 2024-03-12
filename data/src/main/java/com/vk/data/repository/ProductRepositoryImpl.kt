package com.vk.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vk.data.api.ProductApi
import com.vk.data.mapper.toDetailProductDomain
import com.vk.data.paging.ProductPagingSource
import com.vk.domain.models.DetailProduct
import com.vk.domain.models.Product
import com.vk.domain.repository.ProductRepository
import com.vk.domain.repository.RequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class ProductRepositoryImpl @Inject constructor(
  private val api: ProductApi
) : ProductRepository {

  override fun getProducts(): Flow<PagingData<Product>> = Pager(
    PagingConfig(
      pageSize = ProductApi.DEFAULT_PAGE_SIZE
    ),
    pagingSourceFactory = { ProductPagingSource(api) }
  ).flow

  override fun getDetailProduct(id: Int): Flow<RequestResult<DetailProduct>> {
    val start = flowOf(RequestResult.Loading<DetailProduct>())

    val response = flow {
      emit(api.getDetailProduct(id))
    }.map { result ->
      if (result.isSuccess) {
        result.getOrThrow()
          .toDetailProductDomain()
          .let { RequestResult.Success(it) }
      } else {
        RequestResult.Error()
      }
    }
    return merge(start, response)
  }
}