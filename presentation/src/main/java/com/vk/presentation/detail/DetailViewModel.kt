package com.vk.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.domain.models.DetailProduct
import com.vk.domain.repository.RequestResult
import com.vk.domain.usecase.GetDetailProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  val getDetailProductUseCase: GetDetailProductUseCase,
) : ViewModel() {

  private var id = 0

  private var _state = MutableStateFlow<State>(State.None)
  val state = _state.asStateFlow()

  fun load() {
    viewModelScope.launch{
      getDetailProductUseCase(id)
        .collect {
          _state.value = it.toState()
        }
    }
  }

  fun setId(newId: Int) {
    if (newId != id) {
      id = newId
      load()
    }
  }
}

private fun RequestResult<DetailProduct>.toState(): State {
  return when (this) {
    is RequestResult.Error -> State.Error()
    is RequestResult.Loading -> State.Loading()
    is RequestResult.Success -> State.Success(data)
  }
}