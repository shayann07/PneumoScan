# PneumoScan

A native Kotlin Android **MVP shell** for a future pneumonia X-ray screening app — `applicationId = com.devsphere.pneumoscan`. Today it ships:

- Firebase Auth email/password sign-up + sign-in (with `users` profile docs in Firestore).
- A Material 3 dashboard + static informational screens (Disease, Causes, Symptoms, Medication, About).
- A `TestingFragment` that captures or picks an X-ray image and runs an "analysis" — currently a **hardcoded placeholder** that always returns `Normal` at 0.86 confidence (`data/repository/PneumoRepositoryImpl.kt:69-73`).

The architecture (Clean Architecture + MVVM with Hilt DI) is real and well-organised; the AI itself is not yet wired. ~48 Kotlin files.

## Status

- Working tree clean on `main`. Recent commits: `d8b4b1a` "Ui changes", `d7bc447` "UI Update", `66e60ab` "Ui Update", `e555157` "UI Changes", `e7b1b78` "Add README for PneumoScan app", `7bd3a71` "ui refactor", `1bb3bcd` "gitignore setup", `97dc2ee` "Initial Android project commit", `cf7eef3` "Initial commit".
- Remote: `https://github.com/shayann07/PneumoScan.git`.
- The previous README oversold the AI feature and listed Coil / LiveData / StateFlow as in-use when they aren't. This rewrite preserves the architecture description and is straight about what's implemented vs. roadmap.

## ⚠ What is real today vs. what is roadmap

| Claim | Today | Notes |
| --- | --- | --- |
| Firebase Auth + Firestore | ✅ Real | `SplashActivity` routes by `FirebaseAuth.currentUser`; `RegisterFragment` writes `firstName`/`lastName`/`email` to the `users` collection. |
| Clean Architecture + MVVM + Hilt DI | ✅ Real | `@HiltAndroidApp` on `PneumoScanApp.kt`, `@AndroidEntryPoint` on activities + fragments, `@HiltViewModel` on the auth + testing ViewModels. `di/AppModule` provides `FirebaseAuth` + `FirebaseFirestore` and binds `PneumoRepository`. |
| Material 3 | ✅ Real | `Theme.Material3.DayNight.NoActionBar`, `MaterialCardView` / `MaterialButton` / `TextInputLayout`. |
| Coroutines | ✅ Real | `lifecycleScope.launch` + `Dispatchers.IO`. |
| **AI-based lung screening** | ❌ Placeholder | `PneumoRepositoryImpl.kt:69-73` always returns `TestResult("Normal", 0.86f)`. The TODO comment in the file says *"Placeholder. Later integrate TFLite or ML Kit here."* |
| Coil image loading | ❌ Unused | Dependency is declared but no `ImageView.load(...)` call site exists. |
| LiveData / StateFlow | ❌ Not used | No ViewModel exposes either; UI updates run inline inside `lifecycleScope.launch`. |
| Retrofit + Gson "(Future)" | ⚠ Scaffolding only | Deps are present, but `PneumoApiService` is empty and `di/NetworkModule` is an empty `@Module`. |
| Room "(Future)" | ⚠ Scaffolding only | `UserEntity` / `UserDao` are empty stubs and there is no `@Database` class. |
| MIT licence | ✅ Real | `LICENSE` at the repo root carries the MIT text. |

## How it works

### Auth flow

`SplashActivity` (`@AndroidEntryPoint`) inspects `FirebaseAuth.currentUser`. If null, it launches the auth flow inside `MainActivity` (Jetpack Navigation graph: `LoginFragment` ↔ `RegisterFragment`); otherwise it goes straight to `HomeFragment`. `AuthViewModel` (`@HiltViewModel`) drives both sign-up and sign-in via `LoginUserUseCase` / `RegisterUserUseCase`, which call `PneumoRepositoryImpl.login(...)` / `register(...)` against Firebase Auth and write a profile doc to the `users` Firestore collection.

### Dashboard + info screens

`HomeFragment` is the dashboard. It navigates to `TestingFragment`, `DiseaseFragment`, `CausesFragment`, `SymptomsFragment`, `MedicationFragment`, and `AboutUsFragment`. The information screens read static, hardcoded content from `PneumoRepositoryImpl.kt:47-67` — no remote fetch yet.

### Testing screen (placeholder analysis)

`TestingFragment` lets the user capture or pick an X-ray image, displays it, and calls `AnalyzeImageUseCase`. Today the use case forwards to a placeholder repository function that returns a fixed `TestResult("Normal", 0.86f)`. Wiring a real classifier is the headline roadmap item.

### DI

`di/AppModule` provides Firebase Auth/Firestore as singletons and binds `PneumoRepository → PneumoRepositoryImpl`. `di/RepositoryModule` and `di/NetworkModule` exist as empty placeholders for the future Retrofit + Room work.

## Tech stack

- **Build:** AGP 8.13.0, Kotlin 2.0.21, Java 11, View Binding enabled, KAPT (Hilt), KSP (Room — declared but unwired).
- **App config:** `applicationId = com.devsphere.pneumoscan`, `compileSdk = 36`, `minSdk = 24`, `targetSdk = 36`, `versionCode = 1`, `versionName = "1.0"`.
- **DI:** Hilt 2.57.2.
- **Firebase:** firebase-auth 24.0.1, firebase-firestore 26.0.2.
- **AndroidX / Jetpack:** appcompat 1.7.1, lifecycle 2.9.4, navigation 2.9.5, material 1.13.0 (Material 3).
- **Networking (declared, unwired):** Retrofit 3.0.0 + Gson.
- **Persistence (declared, unwired):** Room 2.8.2.
- **Image loading (declared, unused):** Coil 2.7.0.
- **Async:** kotlinx-coroutines 1.10.2.
- **Permissions:** `INTERNET`, `CAMERA` (`android:required="false"`), `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`.

## Project layout

```
com.devsphere.pneumoscan/
├── PneumoScanApp.kt                 @HiltAndroidApp
├── data/
│   ├── local/{dao,entity}/          empty stubs (Room not wired)
│   ├── remote/api/PneumoApiService  empty stub (Retrofit not wired)
│   ├── prefs/                       empty stub
│   └── repository/PneumoRepositoryImpl   placeholder analyse() + static disease info
├── domain/
│   ├── model/                       User, Disease, TestResult
│   ├── repository/PneumoRepository
│   └── usecase/                     Login, Register, Analyze, GetDiseaseInfo
├── presentation/
│   ├── auth/                        Splash, Login, Register, AuthViewModel
│   ├── home/, testing/, disease/, common/
│   └── 9 fragments + MainActivity + SplashActivity
├── di/
│   ├── AppModule.kt                 Firebase + repository binding
│   ├── RepositoryModule.kt          empty placeholder
│   └── NetworkModule.kt             empty placeholder
└── utils/                           Constants, Extensions, NetworkHelper, Resource, ValidationUtils
```

`LICENSE` (MIT) at the repo root. `.gitignore` correctly excludes `google-services.json`, `local.properties`, `build/`, and `*.jks` / `*.keystore`. No secrets are tracked.

## Setup / run

1. Open in Android Studio (AGP 8.13.0 / Gradle 8.x) and run on Android 7.0+ (`minSdk = 24`).
2. Provide your own `app/google-services.json`. The Firebase project must have **Email/Password Authentication** and **Cloud Firestore** enabled. The package id is `com.devsphere.pneumoscan`.
3. Build and run.

The "AI" screen will return Normal / 0.86 confidence regardless of the input image — that is the placeholder behaviour, not a bug.

## Roadmap (real, not "completed")

- 🤖 Wire a chest-X-ray classifier (TFLite or ML Kit) into `PneumoRepositoryImpl.analyzeImage` so it stops returning a fixed result.
- 💾 Implement `UserEntity` / `UserDao` and stand up a `@Database` so reports cache locally (Room dependency is already declared).
- 🌐 Implement `PneumoApiService` and `di/NetworkModule` so the disease information stops being a hardcoded dictionary.
- 🖼️ Replace the manual `ImageView` plumbing with Coil (`imageView.load(uri)`) — the dependency is already declared.
- 🔄 Surface state from each ViewModel via `StateFlow` (claimed in the previous README but not yet implemented).
- 🌙 Dark mode, push notifications, doctor/clinic mode.

## Honest limitations

- **The lung-screening result is hardcoded.** No ML model, no inference call.
- **`SplashActivity`** trusts `FirebaseAuth.currentUser` without `currentUser.reload()`; an account that has been disabled or deleted server-side will still bounce to home until the next forced sign-in.
- **`WRITE_EXTERNAL_STORAGE`** is declared but unnecessary on Android 10+ when you go through the Storage Access Framework. Trim before release.
- **`Coil`, `Retrofit`, `Room`, `LiveData`/`StateFlow` are all advertised in the previous README but are not actually used in the current build.** Roadmap items, not features.
- **No tests** beyond the default `ExampleUnitTest`.

## License

MIT — see [`LICENSE`](LICENSE).
