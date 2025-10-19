package com.devsphere.pneumoscan.domain.model

data class TestResult(
    val label: String,
    val confidence: Float,
    val timestamp: Long = System.currentTimeMillis()
)
