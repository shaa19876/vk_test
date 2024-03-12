package com.vk.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vk.domain.models.Product
import com.vk.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

  private var _products = MutableStateFlow<PagingData<Product>>(PagingData.empty())
  val products = _products.asStateFlow()

  fun load() {
    viewModelScope.launch {
      getProductsUseCase()
        .cachedIn(viewModelScope)
        .collect {
          _products.value = it
        }
    }
  }

  init {
    load()
  }
}