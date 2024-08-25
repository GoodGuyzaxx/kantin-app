package uningrat.kantin.di

import android.app.Application
import android.content.Context
import uningrat.kantin.data.local.room.KantinDatabase
import uningrat.kantin.data.pref.DataPreferences
import uningrat.kantin.data.pref.dataStore
import uningrat.kantin.data.retrofit.ApiConfig
import uningrat.kantin.repository.KantinRepository

object Injection {
    fun provideRepository(context: Context): KantinRepository {
        val apiService = ApiConfig.getApiService()
        val userPreferences = DataPreferences.getInstance(context.dataStore)
        val database = KantinDatabase.getDatabase(context)
        val mCartDao = database.cartDao()
        val mOrderDao = database.orderDao()
        val applicaton = context.applicationContext as Application
        return KantinRepository.getInstance(apiService,userPreferences,mCartDao, mOrderDao,applicaton)
    }
}