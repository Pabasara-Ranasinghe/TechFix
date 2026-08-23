package com.example.techfix

/*
 * Technician
 *
 * Data model for a TechFix technician.
 *
 * The information is stored in the Firestore
 * "technicians" collection.
 */

data class Technician(

    // Firestore document ID
    var id: String = "",

    // Technician's name
    var name: String = "",

    // Technician's area of expertise
    var specialization: String = "",

    // Technician's phone number
    var phone: String = "",

    // Technician availability
    var availability: String = "Available"
)