# 🎲 Dice Roller App

A simple and interactive Dice Roller Android application built using **Kotlin** in Android Studio.

This app generates a random dice number (1–6) when the user taps the **Roll** button and updates the dice image accordingly.

---

## 📱 Preview

![alt text](<Screenshot 2026-02-25 212314.png>)

---

## 🚀 Features

- 🎲 Random dice number generation
- 🔁 Dynamic ImageView updates
- 🎨 Clean and minimal UI
- 🧠 Beginner-friendly Android project
- 📱 Runs on emulator or physical device

---

## 🛠️ Tech Stack

- **Language:** Kotlin  
- **IDE:** Android Studio  
- **UI Design:** XML Layout  
- **Min SDK:** (Add your minSdkVersion)  
- **Target SDK:** (Add your targetSdkVersion)

---

## 🧠 How It Works

The app generates a random number between 1 and 6:

```kotlin
val randomNumber = (1..6).random()
```

## 📂 Project Structure

```
📦 DiceRollerApp
 ┣ 📂 app
 ┃ ┣ 📂 java/com/example/
 ┃ ┣ 📂 res/layout
 ┃ ┣ 📂 res/drawable (dice images)
 ┃ ┗ AndroidManifest.xml
 ┗ build.gradle

 ```
 ## 🎯 Learning Outcomes

- Through this project, I practiced:
- Button click listeners
- Random number generation in Kotlin
- Updating UI dynamically
- Working with ImageView and drawable resources