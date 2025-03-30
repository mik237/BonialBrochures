# Bonial Brochures

Bonial Brochures is a modern Android application built using **Jetpack Compose** to provide a seamless and interactive experience for browsing brochures. The app follows the **Clean Architecture** principles with the **MVVM** pattern, ensuring scalability and maintainability.

## 📌 Features
- **Display a list of brochures** using **Jetpack Compose**, showing the brochure image and retailer name.  
- **Fetch data** from the remote source: [shelf.json](https://mobile-s3-test-assets.aws-sdlc-bonial.com/shelf.json).  
- **Display only** brochures with `contentType` **"brochure"** or **"brochurePremium"**.  
- **Use a placeholder image** when a brochure image is unavailable.
- **Filter brochures** within a **5km range** for improved relevance.  
- **Display "brochurePremium" content in full width**, occupying all columns.  
- **Support landscape layout** with a **three-column grid** for an adaptive experience.  

## 🛠 Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM with Clean Architecture
- **UI:** Jetpack Compose (Material3)
- **Networking:** Retrofit
- **Dependency Injection:** Hilt
- **Image Loading:** Coil
- **Pagination:** Paging3
- **Testing:** MockK

## 🚀 Setup
```bash
# Clone the repository
git clone https://github.com/yourusername/BonialBrochures.git

# Open the project in Android Studio
# Sync dependencies and run the project on an emulator or a physical device
