data class Artwork(
    val title: String,
    val artist: String,
    val year: Int
)

class ArtGallery(private val artworks: List<Artwork>) {

    private var currentIndex = 0

    fun showCurrentArtwork() {
        val artwork = artworks[currentIndex]
        println("\n--- Current Artwork ---")
        println("Title : ${artwork.title}")
        println("Artist: ${artwork.artist}")
        println("Year  : ${artwork.year}")
        println("-----------------------")
    }

    fun next() {
        currentIndex = (currentIndex + 1) % artworks.size
        showCurrentArtwork()
    }

    fun previous() {
        currentIndex = if (currentIndex - 1 < 0) {
            artworks.size - 1
        } else {
            currentIndex - 1
        }
        showCurrentArtwork()
    }
}

fun main() {

    val gallery = ArtGallery(
        listOf(
            Artwork("Mona Lisa", "Leonardo da Vinci", 1503),
            Artwork("Starry Night", "Vincent van Gogh", 1889),
            Artwork("The Persistence of Memory", "Salvador Dali", 1931),
            Artwork("The Scream", "Edvard Munch", 1893)
        )
    )

    println("=== Welcome to the Art Gallery ===")
    gallery.showCurrentArtwork()

    loop@ while (true) {
        println("\nEnter command: next, prev, exit")

        when (readLine()?.toLowerCase()) {

            "next" -> gallery.next()

            "prev" -> gallery.previous()

            "exit" -> {
                println("Exiting gallery.")
                break@loop
            }

            else -> println("Invalid command.")
        }
    }
}