package com.vk.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.vk.domain.models.Product
import com.vk.presentation.R

@Composable
fun MainScreen(vm: MainViewModel, navController: NavHostController) {

  val products = vm.products.collectAsLazyPagingItems()

  LazyColumn(
    Modifier.padding(10.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    items(products.itemCount) { index ->
      products[index]?.let { ProductItem(it, navController) }
    }
    item {
      when (products.loadState.refresh) {
        is LoadState.Error -> DrawError(vm)
        is LoadState.Loading -> DrawLoading()
        is LoadState.NotLoading -> {}
      }
    }
  }
}

@Composable
private fun DrawLoading() {
  Box(Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
  }
}

@Composable
private fun DrawError(vm: MainViewModel) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = MaterialTheme.colorScheme.error),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Cannot loading data from network",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onError
    )
  }
  Button(onClick = { vm.load() }) {
    Text(text = "Try again")
  }
}

@Composable
private fun ProductItem(product: Product, navController: NavHostController) {
  Card(
    onClick = { navController.navigate("detailScreen?id=${product.id}") },
    Modifier
      .fillMaxWidth()
      .height(120.dp)
  ) {
    Row {
      Column(Modifier.padding(10.dp)) {
        SubcomposeAsyncImage(
          model = product.thumbnail,
          loading = { CircularProgressIndicator() },
          error = {
            Image(
              painter = painterResource(id = R.drawable.no_image),
              contentDescription = null
            )
          },
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier.size(100.dp)
        )
      }
      Column(Modifier.padding(10.dp).fillMaxWidth()) {
        Text(
          text = product.title,
          style = MaterialTheme.typography.titleMedium,
          maxLines = 1
        )
        Text(
          text = product.description,
          style = MaterialTheme.typography.bodySmall,
          maxLines = 3,
          minLines = 3
        )
        Text(
          text = String.format("Price: %.2f", product.price),
          modifier = Modifier.align(Alignment.End),
          style = MaterialTheme.typography.titleMedium
        )
      }
    }
  }
}

