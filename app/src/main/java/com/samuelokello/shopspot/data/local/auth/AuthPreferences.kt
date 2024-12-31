package com.samuelokello.shopspot.data.local.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.samuelokello.shopspot.data.local.auth.Constants.USER_DATA
import com.samuelokello.shopspot.data.local.auth.Constants.AUTH_KEY
import com.samuelokello.shopspot.data.network.auth.dto.UserResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class AuthPreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(AUTH_KEY.toString())] = accessToken
        }
    }

    val getAccessToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(AUTH_KEY.toString())] ?: ""
    }

    suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(AUTH_KEY.toString())] = ""
        }
    }

    suspend fun saveUserdata(user: UserResponseDto) {
        val jsonString = Json.encodeToString(user)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(USER_DATA.toString())] = jsonString
        }
    }

    val getUserData: Flow<UserResponseDto?> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(USER_DATA.toString())]?.let { jsonString ->
            Json.decodeFromString<UserResponseDto>(jsonString)
        }
    }
}


object Constants {
    const val SPLASH_SCREEN_DURATION = 1000L
    val AUTH_KEY = stringPreferencesKey(name = "auth_key")
    const val AUTH_PREFERENCES = "AUTH_PREFERENCES"
    val USER_DATA = stringPreferencesKey("user_data")
}