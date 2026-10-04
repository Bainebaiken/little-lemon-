package com.heaven.littlelemon1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.heaven.littlelemon1.ui.theme.LittleLemon1Theme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "little_lemon_database"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val menuNetworkData: MenuNetworkdata =
                    httpClient.get(
                        "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
                    ).body()

                val menuItems = menuNetworkData.menu.map { item ->
                    MenuItemEntity(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        price = item.price,
                        image = item.image,
                        category = item.category
                    )
                }

                database.menuDao().insertAll(menuItems)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setContent {
            LittleLemon1Theme {

                val navController = rememberNavController()

                MyNavigation(navController)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        httpClient.close()
    }
}