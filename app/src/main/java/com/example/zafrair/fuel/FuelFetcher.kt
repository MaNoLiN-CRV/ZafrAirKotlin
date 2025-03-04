package com.example.zafrair.fuel

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FuelFetcher private constructor(val baseUrl: String) {

    companion object {
        @Volatile
        private var instance: FuelFetcher? = null

        fun getInstance(baseUrl: String): FuelFetcher {
            return instance ?: synchronized(this) {
                instance ?: FuelFetcher(baseUrl).also { instance = it }
            }
        }
    }

    suspend inline fun <reified T : Any> fetch(endpoint: String): Result<T, Exception> {
        return withContext(Dispatchers.IO) {
            try {
                Fuel.get("$baseUrl$endpoint")
                    .responseObject<T>()
            } catch (e: Exception) {
                Result.error(e)
            }
        } as Result<T, Exception>
    }
}