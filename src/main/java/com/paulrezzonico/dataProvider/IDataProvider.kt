package com.paulrezzonico.dataProvider

interface IDataProvider {
    fun getData(path: String): List<String>
}
