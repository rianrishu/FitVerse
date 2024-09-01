package com.rianrishu.fitverse.data.model.remote

data class SQLModel(
    val sql: String,
    val operation: String = "sql"
)