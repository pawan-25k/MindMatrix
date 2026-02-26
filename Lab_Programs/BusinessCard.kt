data class Profile(val name: String, val title: String, val bio: String)
fun renderAvatar(width: Int, height: Int): String {
 val builder = StringBuilder()
 val border = "+" + "-".repeat(width - 2) + "+\n"
 builder.append(border)
 for (i in 1..height) {
 val content = if (i == height / 2 + 1) {
 val text = "Avatar ${width}x$height"
 val padding = (width - 2 - text.length) / 2
 " ".repeat(padding) + text +
 " ".repeat(width - 2 - padding - text.length)
 } else {
 " ".repeat(width - 2)
 }
 builder.append("|$content|\n")
 }
 builder.append(border)
 return builder.toString()
}
fun renderTextBlock(label: String, text: String, width: Int): String {
 val maxWidth = width - 4
 val trimmed = if (text.length > maxWidth) {
 text.substring(0, maxWidth)
 } else text
 val line = "$label: $trimmed"
 val padded = line.padEnd(width - 2, ' ')
 return "| $padded|\n"
}
fun renderProfile(profile: Profile, width: Int = 30, showAvatar: Boolean = true): String {
 val builder = StringBuilder()
 val border = "+" + "-".repeat(width - 2) + "+\n"
 builder.append(border)
 if (showAvatar) {
 val avatar = renderAvatar(width, 3)
 builder.append(avatar.removeSuffix(border))
 }
 builder.append(renderTextBlock("Name", profile.name, width))
 builder.append(renderTextBlock("Title", profile.title, width))
 builder.append(renderTextBlock("Bio", profile.bio, width))
 builder.append(border)
 return builder.toString()
}
fun main() {
 val profile1 = Profile("Ada Lovelace", "Software Dev", "Early computing pioneer")
 val profile2 = Profile("Alan Turing", "Mathematician", "Father of theoretical CS")
 val profile3 = Profile("Katherine Johnson", "NASA Scientist", "Orbital mechanics expert")
 println(renderProfile(profile1, 30, true))
 println(renderProfile(profile2, 30, false))
 println(renderProfile(profile3, 30, true))
}