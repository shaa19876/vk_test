package com.vk.domain.models

data class Product(
  val id: Int,
  val title: String,
  val description: String,
  val thumbnail: String,
  val price: Double,
)
