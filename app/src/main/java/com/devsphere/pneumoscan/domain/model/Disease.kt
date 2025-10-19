package com.devsphere.pneumoscan.domain.model

data class Disease(
    val id: String,
    val title: String,
    val description: String,
    val moreUrl: String? = null
)
