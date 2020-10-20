package com.home.jetquotesdemo.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainModel {

    @Composable
    fun getMainBeanList(): List<MainBean>? {
        val context = ContextAmbient.current
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(
            List::class.java, MainBean::class.java
        )
        val adapter: JsonAdapter<List<MainBean>> = moshi.adapter(type)
        val file = "main_response.json"
        val json = context.assets.open(file).bufferedReader().use { it.readText() }
        return adapter.fromJson(json)
    }
}