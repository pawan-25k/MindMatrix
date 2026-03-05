data class Theme(
 val name: String,
 val backgroundLabel: String,
 val textLabel: String
)
fun renderCard(title: String, subtitle: String, theme: Theme): String {
 return "[Theme: ${theme.name} | BG: ${theme.backgroundLabel} | Text: ${theme.textLabel}]\n" +
 "Card: $title (sub: $subtitle)"
}
fun animateProgress(totalSteps: Int, width: Int): List<String> {
 val frames = mutableListOf<String>()
 for (step in 0 until totalSteps) {
 val filled = ((step + 1) * width) / totalSteps
 val empty = width - filled
 val bar = "[" + "#".repeat(filled) + "-".repeat(empty) + "]"
 val percent = ((step + 1) * 100) / totalSteps
 frames.add("$bar $percent%")
 }
 return frames
}
fun applyThemeAndAnimate(
 title: String,
 subtitle: String,
 themes: List<Theme>,
 steps: Int,
 width: Int
) {
 for (theme in themes) {
 println("\n--- Applying: ${theme.name} ---")
 println(renderCard(title, subtitle, theme))
 val frames = animateProgress(steps, width)
 for (frame in frames) {
 println(frame)
 }
 }
}
fun main() {
 val lightTheme = Theme("Light", "SoftWhite", "DarkGray")
 val darkTheme = Theme("Dark", "DeepBlack", "LightGray")
 applyThemeAndAnimate(
 "Welcome Learn theming",
 "Simulate Material",
 listOf(lightTheme, darkTheme),
 10,
 20
 )
}