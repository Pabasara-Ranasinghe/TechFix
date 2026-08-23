package com.example.techfix

/*
 * SparePart
 *
 * Data model for a TechFix spare part.
 *
 * Spare parts are stored in the Firestore
 * "spareParts" collection.
 */

data class SparePart(

    // Firestore document ID
    var id: String = "",

    // Spare part name
    var name: String = "",

    // Device/category the part belongs to
    var category: String = "",

    // Available quantity
    var quantity: Int = 0,

    // Price of one spare part
    var price: Double = 0.0
)