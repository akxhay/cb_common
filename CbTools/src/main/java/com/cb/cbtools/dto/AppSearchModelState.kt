package com.cb.cbtools.dto


data class AppSearchModelState(
    val searchText: String = "",
    val apps: List<AppListInfo> = arrayListOf(),
    val progressBar: Boolean = true
    ) {

    companion object {
        val Empty = AppSearchModelState()
    }

}