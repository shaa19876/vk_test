package com.vk.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDTO(
  val products: List<ProductDTO>,
  val total: Int,
  val skip: Int,
  val limit: Int,
)

@Serializable
data class ProductDTO(
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