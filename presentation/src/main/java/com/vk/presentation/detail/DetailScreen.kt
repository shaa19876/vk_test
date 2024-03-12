package com.vk.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.vk.domain.models.DetailProduct
import com.vk.presentation.R

@Composable
fun DetailScreen(vm: DetailViewModel, id: Int) {

  vm.setId(id)

  val state = vm.state.collectAsState()

  when (val currentState = state.value) {
    is State.Loading -> DrawLoading()
    is State.Error -> DrawError(vm)
    is State.Success -> DrawDetailScreen(currentState.product)
    else -> {}
  }
}

@Composable
fun DrawDetailScreen(product: DetailProduct) {
  Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
    with(product) {
      TitleSection(title = title)
      BrandSection(brand = brand, rating = rating, category = category)
      ImageSection(images = images)
      PriceSection(price = price, discount = discountPercentage, stock = stock)
      DescriptionSection(description = description)
    }
  }
}

@Composable
fun TitleSection(title: String) {
  Card(
    modifier = Modifier
      .padding(5.dp)
      .fillMaxWidth(),
    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer)
  ) {
    Text(
      text = title,
      modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.headlineLarge
    )
  }
}

@Composable
fun BrandSection(brand: String, rating: Double, category: String) {
  Text(
    text = "Category: $category",
    Modifier
      .fillMaxWidth()
      .padding(5.dp),
    style = MaterialTheme.typography.titleMedium
  )
  Box(
    modifier = Modifier
      .padding(5.dp)
      .fillMaxWidth()
  ) {
    Text(text = "Brand: $brand", style = MaterialTheme.typography.titleLarge)
    Text(
      text = "Rating: $rating",
      Modifier.fillMaxWidth(),
      style = MaterialTheme.typography.titleLarge,
      textAlign = TextAlign.End
    )
  }
}

@Composable
fun ImageSection(images: List<String>) {
  LazyRow(horizontalArrangement = Arrangement.Absolute.Center) {
    items(images) { url ->
      Card(border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer)) {
        SubcomposeAsyncImage(
          model = url,
          loading = { CircularProgressIndicator() },
          error = {
            Image(
              painter = painterResource(id = R.drawable.no_image),
              contentDescription = null
            )
          },
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(300.dp)
            .clip(RoundedCornerShape(16.dp))

        )
      }
    }
  }
}

@Composable
fun PriceSection(price: Double, discount: Double, stock: Int) {
  Box(
    modifier = Modifier
      .padding(5.dp)
      .fillMaxWidth()
  ) {
    Row {
      Text(
        text = "Old price: ",
        style = MaterialTheme.typography.titleMedium
      )
      Text(
        text = String.format("%.2f $", price * (1 + discount / 100)),
        textDecoration = TextDecoration.LineThrough,
        style = MaterialTheme.typography.titleMedium
      )
    }
    Text(
      text = "Stock: $stock",
      Modifier.fillMaxWidth(),
      style = MaterialTheme.typography.titleMedium,
      textAlign = TextAlign.End
    )
  }
  Box(
    modifier = Modifier
      .padding(5.dp)
      .fillMaxWidth()
  ) {
    Text(text = "Price: $price $", style = MaterialTheme.typography.titleLarge)
    Text(
      text = "Discount: $discount %",
      Modifier.fillMaxWidth(),
      style = MaterialTheme.typography.titleLarge,
      textAlign = TextAlign.End
    )
  }
}

@Composable
fun DescriptionSection(description: String) {
  Box(
    modifier = Modifier
      .padding(5.dp)
      .fillMaxWidth()
  ) {
    Card(
      modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(),
      border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer)
    ) {
      Column(Modifier.padding(5.dp)) {
        Text(
          text = "Description:",
          style = MaterialTheme.typography.titleLarge
        )
        Text(
          text = description,
          minLines = 5,
          maxLines = 8
        )
      }
    }
  }
}

@Composable
fun DrawLoading() {
  Box(Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
  }
}

@Composable
private fun DrawError(vm: DetailViewModel) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(5.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Cannot loading data from network",
      modifier = Modifier
        .background(color = MaterialTheme.colorScheme.error),
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onError
    )
    Button(onClick = { vm.load() }) {
      Text(text = "Try again")
    }
  }
}