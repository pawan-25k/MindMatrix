

# 🍋 Lemonade App

An interactive Android application built using **Kotlin** that simulates the process of making lemonade in a fun and engaging way.

Users tap images to progress through different stages of making lemonade.



## 📱 Preview


<p align="center">

![alt text](<Screenshot 2026-02-25 213335.png  >)

![alt text](<Screenshot 2026-02-25 213357.png>)
</p>


## 🚀 Features

- 🍋 Tap-based interaction
- 🔄 Multiple UI states
- 📱 Image-based transitions
- 🎨 Clean and simple interface
- 🧠 Beginner-friendly Android app



## 🛠️ Tech Stack

- **Language:** Kotlin  
- **IDE:** Android Studio  
- **UI Design:** XML Layout  
- **Min SDK:** (Add your minSdkVersion)  
- **Target SDK:** (Add your targetSdkVersion)


## 🧠 App Flow Logic

The app follows a step-by-step state-based flow:

1. Tap the lemon tree 🌳  
2. Squeeze the lemon 🍋  
3. Drink the lemonade 🥤  
4. Restart  

The state changes are handled using conditional logic (`when` statement) in Kotlin.

Example:

```kotlin
when (currentStep) {
    1 -> // Show lemon tree
    2 -> // Squeeze lemon
    3 -> // Drink lemonade
    4 -> // Restart
}
```
