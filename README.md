# 🌟 Crovian Attendance & Payroll App

> An intelligent and robust Employee Attendance & Payroll Management System built with **Kotlin**, **Firebase**, and **Jetpack Architecture**. Designed exclusively for employees to log their daily attendance, track their advances, and seamlessly generate monthly payroll reports.

![GitHub Release](https://img.shields.io/github/v/release/yourusername/AttendanceApp)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue?logo=kotlin)
![Firebase](https://img.shields.io/badge/Firebase-Firestore%20%7C%20Auth-orange?logo=firebase)

---

## 🎯 Project Goal

The **Crovian Attendance App** serves as the employee-side portal for tracking daily attendance and payroll details. It eliminates manual bookkeeping, providing a centralized platform with clean architecture.

### ✨ Key Features

- **Google Sign-In Authentication**: Secure login mechanism via Firebase Auth.
- **Attendance Tracking**: Log daily attendance (Full Day, Half Day, Absent) alongside overtime hours.
- **Advance Payments**: Record advance payment requests securely.
- **Monthly Summary & Stats**: Keep track of days worked, gross earnings, net payables, and visually analyze attendance via charts.
- **Exporting Options**: Generate professional-grade Monthly Payroll Reports in **PDF** and **CSV** formats instantly.
- **Multi-language Support**: Toggle between English and Hindi effortlessly.
- **Dark Mode**: Built-in support for a seamless dark-theme experience.

---

## 🚀 Tech Stack

- **Language**: Kotlin 1.9.22
- **Backend & Database**: Firebase Firestore
- **Authentication**: Firebase Authentication (Google Sign-In)
- **Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern
- **UI Components**: ViewBinding, Material Design 3, Shimmer Loaders
- **Charts**: MPAndroidChart
- **PDF Generation**: iText7
- **CI/CD Pipeline**: Automated APK release generation using **GitHub Actions**.

---

## 🛠️ Setup & Installation

### 1. Firebase Setup

1. Create a Firebase project named `crovian-2ae30`.
2. Add an Android app with the package name `com.crovian.ai`.
3. Enable **Google Sign-In** under Authentication.
4. Download the `google-services.json` file and place it in the `app/` directory of this project.

### 2. Gradle & Dependencies

Sync the project with Gradle. All essential libraries including Jetpack libraries, Firebase BoM, MPAndroidChart, and iText7 are configured within `app/build.gradle.kts`.

### 3. CI/CD: Automated APK Release via GitHub Actions

To build the APK automatically upon pushing a new tag, configure the following secrets in your GitHub repository (`Settings -> Secrets and variables -> Actions`):

- `GOOGLE_SERVICES_JSON`: Base64 encoded content of your `google-services.json` file.
- `KEYSTORE_BASE64`: Base64 encoded `.jks` keystore file.
- `KEYSTORE_PASSWORD`: Keystore password.
- `KEY_ALIAS`: Alias of your generated key.
- `KEY_PASSWORD`: Key password.

**Triggering a Release:**
```bash
git add .
git commit -m "feat: your new feature"
git tag v1.0.0
git push origin v1.0.0
```

---

## 📱 Screenshots & Previews
*(Add relevant app screenshots here showcasing Dashboard, Calendar, and Report generated screens).*

---

## 🤝 Contributing
Contributions, issues, and feature requests are welcome!

---

Developed with ❤️ for streamlined Employee Attendance tracking.
