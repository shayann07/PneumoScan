# PneumoScan (AI-Assisted Pneumonia Screening & Kiosk Architecture)

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)]()
[![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> Sign in and read up on pneumonia â€” symptoms, causes, treatment, and prevention. AI-driven lung screening from chest X-rays is the next milestone.

---

## 📖 Overview

Sign in and read up on pneumonia â€” symptoms, causes, treatment, and prevention. AI-driven lung screening from chest X-rays is the next milestone.

---

## ✨ Key Features

- **Clean Architecture Separation**: Pure Kotlin domain layer decoupled from presentation and data frameworks for high testability.
- **Dependency Injection**: Full Dagger Hilt integration (`@HiltAndroidApp`, `@AndroidEntryPoint`, `@Inject`) for repository and service bindings.
- **Firebase Authentication & Firestore**: Secure user sign-up, sign-in, and persistent profile state management.
- **Material 3 Design System**: Modern Day/Night themes, dynamic bottom navigation bars, and fluid transitions.
- **Extensible Screening Pipeline**: Modular `AnalyzeImageUseCase` and `TestingViewModel` with camera capture and gallery selection integrations.

---

---

## 🛠️ Technology Stack

| Component / Layer | Technology |
|---|---|
| **Platform** | Android |
| **Primary Language** | Kotlin |
| **Architecture** | MVVM / Clean Architecture |
| **License** | Open Source (MIT) |

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17 / 21
- Android SDK 34 / 35

### Build & Run
1. Clone the repository:
   ```bash
   git clone https://github.com/shayann07/PneumoScan.git
   cd PneumoScan
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle dependencies and run on an emulator or physical device.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE) — Copyright (c) 2026 [shayann07](https://github.com/shayann07).
