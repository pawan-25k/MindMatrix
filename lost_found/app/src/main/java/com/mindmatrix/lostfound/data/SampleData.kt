package com.mindmatrix.lostfound.data

import com.mindmatrix.lostfound.model.FoundItem
import com.mindmatrix.lostfound.model.ItemCategory
import com.mindmatrix.lostfound.model.ItemSummary
import com.mindmatrix.lostfound.model.LostItem
import com.mindmatrix.lostfound.model.toSummary

/**
 * SampleData – static placeholder data used while there is no backend.
 *
 * Replace this object with repository calls (Firestore, Retrofit, Room, etc.)
 * once Firebase or another API is integrated. All screens read from
 * [allItems] via the ViewModel, so swapping the data source only
 * requires changing [LostFoundRepository].
 */
object SampleData {

    val lostItems: List<LostItem> = listOf(
        LostItem(
            id = "l1",
            name = "Samsung Galaxy S24",
            description = "Black Samsung Galaxy S24 with a cracked screen protector. Lost near the library reading hall.",
            category = ItemCategory.ELECTRONICS,
            location = "Central Library – 2nd Floor",
            dateLost = "2026-03-10",
            imageUrl = "https://images.unsplash.com/photo-1598965675045-45c5e72c7d05?w=400",
            contactEmail = "ravi.sharma@university.edu",
            contactPhone = "+91 98765 43210",
            reportedBy = "Ravi Sharma"
        ),
        LostItem(
            id = "l2",
            name = "Blue Denim Jacket",
            description = "Dark-blue Levi's denim jacket, size M. Left on a chair in the cafeteria during lunch.",
            category = ItemCategory.CLOTHING,
            location = "Main Cafeteria – Block B",
            dateLost = "2026-03-11",
            imageUrl = "https://images.unsplash.com/photo-1591047139829-d91aecb6caea?w=400",
            contactEmail = "priya.nair@university.edu",
            contactPhone = "+91 87654 32109",
            reportedBy = "Priya Nair"
        ),
        LostItem(
            id = "l3",
            name = "Student ID Card",
            description = "ID card belonging to student ID 2024CS042. Please return to the admin office.",
            category = ItemCategory.DOCUMENTS,
            location = "Computer Science Block – Corridor",
            dateLost = "2026-03-12",
            imageUrl = null,
            contactEmail = "arjun.mehta@university.edu",
            contactPhone = "+91 76543 21098",
            reportedBy = "Arjun Mehta"
        ),
        LostItem(
            id = "l4",
            name = "Silver Bracelet",
            description = "Thin silver bracelet with an engraved heart charm. Sentimental value – reward offered.",
            category = ItemCategory.ACCESSORIES,
            location = "Girls Hostel – Common Room",
            dateLost = "2026-03-12",
            imageUrl = "https://images.unsplash.com/photo-1611085583191-a3b181a88401?w=400",
            contactEmail = "sneha.kapoor@university.edu",
            contactPhone = "+91 65432 10987",
            reportedBy = "Sneha Kapoor"
        ),
        LostItem(
            id = "l5",
            name = "Engineering Mathematics Textbook",
            description = "5th edition Kreyszig, has handwritten notes throughout. Name written inside front cover.",
            category = ItemCategory.BOOKS,
            location = "Lecture Hall 301 – Engineering Block",
            dateLost = "2026-03-13",
            imageUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400",
            contactEmail = "karan.singh@university.edu",
            contactPhone = "+91 54321 09876",
            reportedBy = "Karan Singh"
        )
    )

    val foundItems: List<FoundItem> = listOf(
        FoundItem(
            id = "f1",
            name = "Apple AirPods Pro",
            description = "White AirPods Pro case (2nd gen) found near the sports complex entrance. Still has charge.",
            category = ItemCategory.ELECTRONICS,
            location = "Sports Complex – Main Gate",
            dateFound = "2026-03-11",
            imageUrl = "https://images.unsplash.com/photo-1588423771073-b8903fead85b?w=400",
            contactEmail = "divya.rao@university.edu",
            contactPhone = "+91 43210 98765",
            reportedBy = "Divya Rao",
            handedToAdmin = false
        ),
        FoundItem(
            id = "f2",
            name = "Bunch of Keys",
            description = "4 keys on a small red keyring. Found on a bench in front of the admin building.",
            category = ItemCategory.KEYS,
            location = "Admin Building – Front Bench",
            dateFound = "2026-03-12",
            imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400",
            contactEmail = "amit.verma@university.edu",
            contactPhone = "+91 32109 87654",
            reportedBy = "Amit Verma",
            handedToAdmin = true
        ),
        FoundItem(
            id = "f3",
            name = "Black Leather Wallet",
            description = "Black faux-leather wallet containing a bus pass and some cash. Handed to security.",
            category = ItemCategory.WALLET,
            location = "Parking Lot – Zone C",
            dateFound = "2026-03-13",
            imageUrl = "https://images.unsplash.com/photo-1627123424574-724758594e93?w=400",
            contactEmail = "meera.pillai@university.edu",
            contactPhone = "+91 21098 76543",
            reportedBy = "Meera Pillai",
            handedToAdmin = true
        ),
        FoundItem(
            id = "f4",
            name = "Prescription Glasses",
            description = "Round-frame prescription glasses in a black case. Found in the chemistry lab.",
            category = ItemCategory.ACCESSORIES,
            location = "Chemistry Lab – Block D",
            dateFound = "2026-03-13",
            imageUrl = "https://images.unsplash.com/photo-1574258495973-f010dfbb5371?w=400",
            contactEmail = "rohit.joshi@university.edu",
            contactPhone = "+91 10987 65432",
            reportedBy = "Rohit Joshi",
            handedToAdmin = false
        )
    )

    /**
     * Combined list of all items (lost + found) sorted by date descending.
     * This is the primary data source for the Item List screen.
     */
    val allItems: List<ItemSummary> by lazy {
        (lostItems.map { it.toSummary() } + foundItems.map { it.toSummary() })
            .sortedByDescending { it.date }
    }
}
