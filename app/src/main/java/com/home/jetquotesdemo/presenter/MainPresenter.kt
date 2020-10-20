package com.home.jetquotesdemo.presenter

import androidx.compose.runtime.Composable
import com.home.jetquotesdemo.model.MainBean
import com.home.jetquotesdemo.model.MainModel

class MainPresenter(private val model: MainModel = MainModel()) {

    @Composable
    fun getMainBeanList(): List<MainBean>? {
        return model.getMainBeanList()
    }
}