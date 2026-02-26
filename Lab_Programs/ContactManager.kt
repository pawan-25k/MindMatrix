// Data class representing a Contact
data class Contact(
    val name: String,
    val phone: String?,
    val email: String?,
    val isFavorite: Boolean
)

// Function to display a single contact
fun displayContact(contact: Contact) {
    println("Name     : ${contact.name}")
    println("Phone    : ${contact.phone ?: "Not Available"}")
    println("Email    : ${contact.email ?: "Not Available"}")
    println("Favorite : ${if (contact.isFavorite) "Yes" else "No"}")
    println("----------------------------------")
}

// Function to display a list of contacts
fun displayContacts(contacts: List<Contact>) {
    if (contacts.isEmpty()) {
        println("No contacts found.")
        return
    }
    contacts.forEach { displayContact(it) }
}

// Higher-order function to filter contacts
fun filterContacts(
    contacts: List<Contact>,
    predicate: (Contact) -> Boolean
): List<Contact> {
    return contacts.filter(predicate)
}

fun main() {

    val contacts = listOf(
        Contact("Ada Lovelace", "1234567890", "ada@email.com", true),
        Contact("Alan Turing", null, "alan@email.com", false),
        Contact("Katherine Johnson", "9876543210", null, true),
        Contact("Dennis Ritchie", null, null, false)
    )

    println("=== ALL CONTACTS ===")
    displayContacts(contacts)

    println("\n=== FAVORITE CONTACTS ===")
    val favoriteContacts = filterContacts(contacts) { it.isFavorite }
    displayContacts(favoriteContacts)

    println("\n=== CONTACTS WITH EMAIL ===")
    val emailContacts = filterContacts(contacts) { it.email != null }
    displayContacts(emailContacts)
}