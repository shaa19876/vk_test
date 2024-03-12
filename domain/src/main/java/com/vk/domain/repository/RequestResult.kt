package com.vk.domain.repository

sealed class RequestResult<T>(open val data: T? = null) {
  class Success<T>(override val data: T) : RequestResult<T>(data)
  class Loading<T> : RequestResult<T>()
  class Error<T> : RequestResult<T>()
}
