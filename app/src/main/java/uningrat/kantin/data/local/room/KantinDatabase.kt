package uningrat.kantin.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uningrat.kantin.data.local.entity.CartEntity
import uningrat.kantin.data.local.entity.OrderEntity

@Database(entities = [CartEntity::class, OrderEntity::class], version = 3, exportSchema = false)
abstract class KantinDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: KantinDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): KantinDatabase{
            if (INSTANCE == null){
                synchronized(KantinDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        KantinDatabase::class.java,"kantin_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as KantinDatabase
        }
    }
}