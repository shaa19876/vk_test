package com.vk.presentation.detail

import com.vk.domain.models.DetailProduct

sealed class State {
  data object None: State()
  class Loading(val product: DetailProduct? = null): State()
  class Error(val product: DetailProduct? = null): State()
  class Success(val product: DetailProduct): State()
}