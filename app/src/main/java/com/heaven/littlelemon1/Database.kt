package com.heaven.littlelemon1

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "menu")
data class MenuItemEntity(
    @PrimaryKey
    val id: Int,

    val title: String,

    val description: String,

    val price: String,

    val image: String,

    val category: String
)

@Dao
interface MenuDao {

    @Query("SELECT * FROM menu")
    suspend fun getAll(): List<MenuItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        menuItems: List<MenuItemEntity>
    )
}

@Database(
    entities = [MenuItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun menuDao(): MenuDao
}