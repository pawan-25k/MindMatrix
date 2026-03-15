package com.mindmatrix.lostfound.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mindmatrix.lostfound.model.User
import com.mindmatrix.lostfound.model.UserRole
import com.google.firebase.auth.FirebaseAuth
/**
 * LoginViewModel – manages authentication state for the Login screen.
 *
 * Currently performs stub validation (any non-empty email/password succeeds).
 * To integrate Firebase Auth:
 *   1. Add the Firebase Auth dependency.
 *   2. Replace [login] body with FirebaseAuth.signInWithEmailAndPassword().
 *   3. Read the FirebaseUser to populate [currentUser].
 */
// Import Firebase


class LoginViewModel : ViewModel() {
    val currentUser: User?
    private val auth = FirebaseAuth.getInstance()

    // ... (keep your existing email/password/role states)

    fun login(onSuccess: (User) -> Unit) {
        errorMessage = null
        // (Keep your existing validation logic here)

        isLoading = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    // Map Firebase user to your custom app User model
                    val user = User(
                        email = firebaseUser.email ?: email,
                        role = selectedRole,
                        displayName = firebaseUser.displayName ?: email.substringBefore("@")
                    )
                    currentUser = user
                    isLoading = false
                    onSuccess(user)
                }
            }
            .addOnFailureListener { exception ->
                isLoading = false
                errorMessage = exception.localizedMessage ?: "Authentication failed."
            }
    }

    fun logout() {
        auth.signOut() // Sign out from Firebase session
        currentUser = null
        email = ""
        password = ""
    }

    fun register(onSuccess: (User) -> Unit) {
        errorMessage = null
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "All fields are required."
            return
        }
        isLoading = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val user = User(email = email, role = selectedRole)
                currentUser = user
                isLoading = false
                onSuccess(user)
            }
            .addOnFailureListener {
                isLoading = false
                errorMessage = it.localizedMessage
            }
    }
}