package uningrat.kantin.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Data")
class DataPreferences private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveSession(user: UserModel){
        dataStore.edit { preferences ->
            preferences[USER_ID] = user.id_konsumen
            preferences[NAME_KEY] = user.nama_konsumen
            preferences[EMAIL_KEY] = user.email
            preferences[TELP_KEY] = user.no_telp
            preferences[STATUS_KEY] = user.status
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel>{
        return dataStore.data.map { preferences ->
            UserModel(
            preferences[USER_ID] ?: "",
            preferences[NAME_KEY] ?: "",
            preferences[EMAIL_KEY] ?: "",
            preferences[TELP_KEY] ?: "",
                preferences[STATUS_KEY] ?: "",
            preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun saveKantinSession(kantin: KantinModel) {
        dataStore.edit { pref ->
            pref[ID_KANTIN_KEY] = kantin.id
            pref[EMAIL_ADMIN_KEY] = kantin.email
            pref[NAMA_KANTIN_KEY] = kantin.nama_kantin
        }
    }

    fun getKantinSession(): Flow<KantinModel>{
        return dataStore.data.map { pref ->
            KantinModel(
                pref[ID_KANTIN_KEY] ?: "",
                pref[EMAIL_ADMIN_KEY] ?: "",
                pref[NAMA_KANTIN_KEY] ?: "",
            )
        }
    }

    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: DataPreferences? = null

        private val USER_ID = stringPreferencesKey("id_konsumen")
        private val NAME_KEY = stringPreferencesKey("nama_konsumen")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TELP_KEY = stringPreferencesKey("no_telp")
        private val STATUS_KEY = stringPreferencesKey("status")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        private val ID_KANTIN_KEY = stringPreferencesKey("id_kantin")
        private val EMAIL_ADMIN_KEY = stringPreferencesKey("email_admin")
        private val NAMA_KANTIN_KEY = stringPreferencesKey("nama_kantin")

        fun getInstance(dataStore: DataStore<Preferences>): DataPreferences {
            return INSTANCE ?: synchronized(this){
                val instance = DataPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}