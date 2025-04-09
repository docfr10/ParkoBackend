package com.example.utils

class Constants {
    object Error {
        const val GENERAL = "Something went wrong!"
        const val WRONG_EMAIL = "Wrong email address!"
        const val INCORRECT_PASSWORD = "Incorrect password!"
        const val MISSING_FIELDS = "Missing some fields"
    }

    object Success {
        const val USER_REGISTERED = "User successfully registered!"

        const val PARKING_ADDED = "Parking added successfully!"
        const val PARKING_UPDATED = "Parking updated successfully!"
        const val PARKING_DELETED = "Parking deleted successfully!"

        const val FAVORITE_PARKING_ADDED = "Favorite parking added successfully!"
        const val FAVORITE_PARKING_UPDATED = "Favorite parking updated successfully!"
        const val FAVORITE_PARKING_DELETED = "Favorite parking deleted successfully!"
    }
}