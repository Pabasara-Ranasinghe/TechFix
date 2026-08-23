package com.example.techfix

/*
 * Appointment
 *
 * Data model for a TechFix repair appointment.
 *
 * These fields match the information stored
 * inside the Firestore "appointments" collection.
 */

data class Appointment(

    // Firestore document ID
    var id: String = "",

    // Firebase UID of the customer
    var customerId: String = "",

    // Type of device
    var deviceType: String = "",

    // Device model
    var deviceModel: String = "",

    // Description of the repair problem
    var problem: String = "",

    // Customer's preferred repair date
    var preferredDate: String = "",

    // Current repair status
    var status: String = "",

    // Time when appointment was created
    var createdAt: Long = 0L
)