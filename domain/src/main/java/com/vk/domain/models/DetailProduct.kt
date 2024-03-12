package com.vk.domain.models

data class DetailProduct(
  val id: Int,
  val title: String,
  val description: String,
  val price: Double,
  val discountPercentage: Double,
  val rating: Double,
  val stock: Int,
  val brand: String,
  val category: String,
  val thumbnail: String,
  val images: List<String>,
)
