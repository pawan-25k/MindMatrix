package com.mindmatrix.lostfound.model

/**
 * Represents the role of the logged-in user.
 * Extend this enum when adding more roles (e.g., SECURITY_STAFF).
 */
enum class UserRole {
    STUDENT,
    ADMIN
}

/**
 * Holds the minimal authentication state for the current session.
 * Replace with a real auth model (Firebase Auth User, JWT claims, etc.)
 * when integrating a backend.
 *
 * @property email       The user's email address.
 * @property role        The selected role for this session.
 * @property displayName Human-readable name shown on the Home screen.
 */
data class User(
    val email: String,
    val role: UserRole,
    val displayName: String = email.substringBefore("@").replaceFirstChar { it.uppercaseChar() }
)
