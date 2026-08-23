package com.example.techfix

/*
 * RepairHistory data class
 *
 * This class represents a completed repair record
 * stored in the Firestore "appointments" collection.
 *
 * We use the same Firestore appointment fields,
 * but only display records whose status is "Completed".
 */

data class RepairHistory(

    // Firebase UID of the customer
    val customerId: String = "",

    // Type of repaired device
    val deviceType: String = "",

    // Model of the repaired device
    val deviceModel: String = "",

    // Description of the reported problem
    val problem: String = "",

    // Date selected for the repair
    val preferredDate: String = "",

    // Current status of the repair
    val status: String = "",

    // Time when the appointment was created
    val createdAt: Long = 0L
)