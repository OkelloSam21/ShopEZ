package com.samuelokello.shopspot.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.samuelokello.shopspot.domain.Product
import com.samuelokello.shopspot.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel= viewModel(factory = AppViewModelProvider.Factory),
    navigateToItemDetails: (product: Product) -> Unit,
) {
    val searchUiState by viewModel.searchUiState.collectAsState()
    val recentSearches by viewModel.getRecentSearches().collectAsState()

    var searchText by rememberSaveable { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var showSuggestions by remember { mutableStateOf(false) }

    // Handle suggestion logic dynamically
    LaunchedEffect(searchText) {
        suggestions = viewModel.getSuggestions(query = searchText)
        showSuggestions = suggestions.isNotEmpty()
    }

    Column(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        // Search bar at the top
        DockedSearchBar(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchText,
                    onQueryChange = { query ->
                        searchText = query
                        viewModel.search(query = query)
                    },
                    onSearch = {
                        viewModel.addToHistory(searchText)
                        viewModel.search(query = searchText)
                    },
                    placeholder = { Text("Search products...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    expanded = false,
                    onExpandedChange = {}
                )
            },
            expanded = false,
            onExpandedChange = { /* No need to expand dropdown */ },
            content = {}
        )

        // Suggestions box (Static dropdown)
        if (suggestions.isNotEmpty()) {
            SuggestionBox(
                suggestions = suggestions,
                onSuggestionClicked = { query ->
                    searchText = query
                    viewModel.search(query = query)
                }
            )

            HorizontalDivider(modifier.padding(vertical = 8.dp, horizontal = 12.dp))
        }

        if(recentSearches.isNotEmpty()) {
            RecentSearchHistory(
                recentSearches = recentSearches,
                onDeleteItem = { query ->
                    viewModel.removeFromHistory(query)
                },
                onItemClick = { query ->
                    searchText = query
                    viewModel.search(query = query)
                }
            )
            HorizontalDivider(modifier.padding(vertical = 8.dp, horizontal = 12.dp))
        }
        // Search results container - LazyColumn for scroll only here
        SearchResultsContainer(
            searchUiState = searchUiState,
            navigateToItemDetails = navigateToItemDetails
        )
    }
}

@Composable
fun SuggestionBox(
    suggestions: List<String>,
    onSuggestionClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(suggestions) { suggestion ->
            Text(
                text = suggestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClicked(suggestion) }
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

@Composable
fun RecentSearchHistory(
    recentSearches: List<String>,
    onDeleteItem: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Recent Searches",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )

        recentSearches.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item)
                IconButton(
                    onClick = { onDeleteItem(item) }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete recent search")
                }
            }
        }
    }
}

@Composable
fun SearchResultsContainer(
    searchUiState: SearchUiState,
    navigateToItemDetails: (product: Product) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        when (searchUiState) {
            is SearchUiState.Loading -> {
                item {
                    CircularProgressIndicator(
                        Modifier
//                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
            is SearchUiState.Error -> {
                item {
                    Text(
                        text = searchUiState.message,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is SearchUiState.Success -> {
                items(searchUiState.products) { product ->
                    ProductItem(
                        product = product,
                        navigateToItemDetails = navigateToItemDetails
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    navigateToItemDetails: (product: Product) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable (enabled = true, onClick = {navigateToItemDetails(product)}),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product image
        AsyncImage(
            model = product.image,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
            Text(
                text = "${product.category} - $${product.price}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 1
            )
        }
    }
}
