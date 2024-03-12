package com.vk.data.mapper

import com.vk.data.models.ProductDTO
import com.vk.domain.models.DetailProduct
import com.vk.domain.models.Product

fun ProductDTO.toProductDomain() = Product(
  id = id,
  title = title,
  description = description,
  thumbnail = thumbnail,
  price = price
)

fun ProductDTO.toDetailProductDomain() = DetailProduct(
  id = id,
  title = title,
  description = description,
  thumbnail = thumbnail,
  price = price,
  discountPercentage = discountPercentage,
  rating = rating ,
  stock = stock ,
  brand = brand ,
  category = category ,
  images = images,
)