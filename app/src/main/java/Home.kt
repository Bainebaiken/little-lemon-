package com.heaven.littlelemon1

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Home(
    navController: NavHostController
) {

    val context = navController.context

    var menuItems by remember {
        mutableStateOf<List<MenuItemEntity>>(emptyList())
    }

    // Search phrase
    var searchPhrase by remember {
        mutableStateOf("")
    }

    // Selected category
    var selectedCategory by remember {
        mutableStateOf<String?>(null)
    }

    // Get menu from Room database
    LaunchedEffect(Unit) {

        val database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "little_lemon_database"
        ).build()

        menuItems = withContext(Dispatchers.IO) {
            database.menuDao().getAll()
        }
    }

    // Get unique category names
    val categories = menuItems
        .map { it.category }
        .distinct()

    // Filter menu items
    val filteredMenuItems = menuItems.filter { menuItem ->

        val matchesSearch =
            searchPhrase.isBlank() ||
                    menuItem.title.contains(
                        searchPhrase,
                        ignoreCase = true
                    ) ||
                    menuItem.description.contains(
                        searchPhrase,
                        ignoreCase = true
                    )

        val matchesCategory =
            selectedCategory == null ||
                    menuItem.category.equals(
                        selectedCategory,
                        ignoreCase = true
                    )

        matchesSearch && matchesCategory
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp)
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.logo
                ),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(160.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.profile
                ),
                contentDescription = "Profile",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(45.dp)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            // Hero section
            item {

                HeroSection(
                    searchPhrase = searchPhrase,
                    onSearchPhraseChange = {
                        searchPhrase = it
                    }
                )
            }

            // Menu breakdown
            item {

                CategorySection(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->

                        selectedCategory =
                            if (selectedCategory == category) {
                                null
                            } else {
                                category
                            }
                    }
                )
            }

            // Menu
            item {

                Text(
                    text = "Menu",
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            items(filteredMenuItems) { menuItem ->

                MenuItem(
                    menuItem = menuItem
                )
            }
        }
    }
}