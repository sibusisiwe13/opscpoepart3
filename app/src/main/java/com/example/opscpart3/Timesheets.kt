package com.example.opscpart3


data class Timesheets(
    val startTime: String = "",
    val endTime: String = "",
    val category: String = "",
    val description: String = "",
    val date: String = "",
    val hoursWorked: Long = 0L,
    val photoUrl: String? = null
)

