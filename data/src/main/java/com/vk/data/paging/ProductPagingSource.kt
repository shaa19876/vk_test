package com.vk.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vk.data.api.ProductApi
import com.vk.data.mapper.toProductDomain
import com.vk.domain.models.Product
import jakarta.inject.Inject

class ProductPagingSource @Inject constructor(
  private val api: ProductApi
) : PagingSource<Int, Product>() {

  override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
    val anchorPosition = state.anchorPosition ?: return null
    val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
    return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
    try {
      val pageNumber = params.key ?: 0
      val pageSize = params.loadSize.coerceAtMost(ProductApi.DEFAULT_PAGE_SIZE)
      val response =
        api.getAllProducts(skip = pageNumber * ProductApi.DEFAULT_PAGE_SIZE, limit = pageSize)

      return if (response.isSuccess) {

        val results = response.getOrThrow().products
          .map { productDTO -> productDTO.toProductDomain() }

        val nextPageNumber = if (results.isEmpty()) null else pageNumber + 1
        val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null

        LoadResult.Page(results, prevPageNumber, nextPageNumber)
      } else {
        LoadResult.Error(Exception())
      }
    } catch (e: Exception) {
      return LoadResult.Error(e)
    }
  }

}