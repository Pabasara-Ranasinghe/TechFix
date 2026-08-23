package com.example.techfix

data class RepairPrice(
    var id: String = "",
    var serviceName: String = "",
    var deviceType: String = "",
    var description: String = "",
    var price: Double = 0.0
)