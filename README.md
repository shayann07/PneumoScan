# 🩺 PneumoScan — Fast Lung Screening App

**PneumoScan** is an Android application designed to assist in early lung condition screening using AI-powered analysis of chest X-rays.
It follows **Clean Architecture with MVVM**, is built in **Kotlin**, and integrates **Firebase Authentication**, **Firestore**, and **Material Design 3** for a modern, clinical-grade experience.

---

## 🌟 Features

* 🔐 **Firebase Authentication** — Register, Login, and Secure Access
* 🏠 **Home Dashboard** — Clean navigation to all major modules
* 🧠 **AI-based Lung Screening** — Upload or capture X-ray images for analysis
* 💊 **Medication & Disease Info** — Informational modules for users
* ⚙️ **MVVM + Clean Architecture** — Scalable, testable, and maintainable structure
* 🎨 **Material 3 UI** — Modern, accessible, and professional interface
* 🌐 **Firestore Integration** — Cloud-stored user profiles and records
* 🧾 **Theming Support** — Reusable light/clinical color palette

---

## 🧩 Architecture Overview

```
com.devsphere.pneumoscan/
│
├── data/            # Data sources (local/remote/prefs)
│   ├── local/dao/
│   ├── remote/api/
│   ├── repository/
│   └── prefs/
│
├── domain/          # Business logic (use cases, models, repo interfaces)
│   ├── model/
│   ├── repository/
│   └── usecase/
│
├── presentation/    # UI layer (Activities, Fragments, ViewModels)
│   ├── auth/
│   ├── home/
│   ├── testing/
│   ├── disease/
│   └── common/
│
├── di/              # Hilt dependency injection modules
│
├── utils/           # Constants, extensions, helpers
│
└── PneumoScanApp.kt # Application class with Hilt setup
```

---

## 🧱 Tech Stack

| Layer                    | Libraries / Tools                              |
| ------------------------ | ---------------------------------------------- |
| **UI / UX**              | Material Design 3, ConstraintLayout, CardView  |
| **Architecture**         | MVVM, Clean Architecture, LiveData / StateFlow |
| **Dependency Injection** | Hilt (Dagger)                                  |
| **Firebase**             | Auth, Firestore                                |
| **Image Loading**        | Coil                                           |
| **Networking (Future)**  | Retrofit + Gson                                |
| **Persistence (Future)** | Room Database                                  |
| **Coroutines**           | Kotlinx.coroutines for async tasks             |

---

## 🔧 Project Setup

### Prerequisites

* Android Studio **Giraffe or newer**
* JDK 17+
* Kotlin **2.0.21**
* Gradle Plugin **8.3+**

### Steps to Run

```bash
git clone https://github.com/shayann07/PneumoScan.git
```

1. Open in **Android Studio**.
2. Add your Firebase `google-services.json` file to:

   ```
   app/google-services.json
   ```
3. Sync Gradle.
4. Run the app on an emulator or physical device.

---

## 🔒 Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/).
2. Add a new Android app with your package name:

   ```
   com.devsphere.pneumoscan
   ```
3. Download and place `google-services.json` into `/app`.
4. Enable:

   * Authentication → Email/Password
   * Firestore Database (test mode for dev)
   * Cloud Storage (optional for future AI uploads)

---

## 🖌️ Design System

* **Primary Color:** Teal `#00BFA6`
* **Accent Color:** Blue `#3A86FF`
* **Background Gradient:** `#E6FAF8 → #FFFFFF`
* **Typography:** Material baseline
* **Theme Mode:** Material 3 light theme (night theme coming soon)

---

## 🚀 Future Roadmap

* 🤖 Integrate AI Model for X-ray Classification
* 💾 Local Storage for Offline Reports
* 🩸 Enhanced Data Visualization (charts & insights)
* 🌙 Dark Mode Theme
* 🧬 Doctor/Clinic Mode with Multi-User Profiles
* 🔔 Push Notifications (Firebase Cloud Messaging)

---

## 🧑‍💻 Contributors

| Name       | Role                       | GitHub                                     |
| ---------- | -------------------------- | ------------------------------------------ |
| **Shayan** | Lead Developer / Architect | [@shayann07](https://github.com/shayann07) |

---

## 📄 License

```
MIT License

Copyright (c) 2025
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.
```

---

## 🫁 About PneumoScan

PneumoScan is a medical AI initiative aiming to empower clinicians and users with early-stage detection tools for lung conditions like pneumonia.
This MVP represents the foundation of a scalable diagnostic companion app built on clean software architecture and modern design.
