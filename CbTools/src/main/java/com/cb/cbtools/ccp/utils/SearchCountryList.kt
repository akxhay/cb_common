package com.cb.cbtools.ccp.utils

import android.content.Context
import com.cb.cbtools.ccp.data.CountryData
import com.cb.cbtools.ccp.data.utils.getCountryName

fun List<CountryData>.searchCountry(key: String, context: Context): MutableList<CountryData> {
    val tempList = mutableListOf<CountryData>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase()
                .contains(key.lowercase()) || it.countryPhoneCode
                .contains(key)
        ) {
            tempList.add(it)
        }
    }
    return tempList
}