# TechFix 🔧📱💻

TechFix is an Android mobile application designed for a computer and mobile phone repair service company in Sri Lanka. The application helps customers book repair services, track their repair requests, view branches, and manage their repair history. It also provides management features for handling appointments, technicians, spare parts, repair prices, and payments.

## 📱 Project Overview

TechFix aims to improve the repair shop's business processes by providing a centralized mobile platform for both customers and management.

The system supports two main user areas:

* **Customer**
* **Management**

The application is being developed as an Android application using Android Studio.

## ✨ Features

### 👤 Customer Features

* User registration and login
* Customer home dashboard
* View available repair services
* Select device categories

   * Phones
   * PC / Mac
   * Screens
   * Battery
* Book a repair appointment
* View bookings
* View repair history
* View TechFix branches
* Open branch locations using Google Maps
* Customer profile management

### 👨‍💼 Management Features

* Management dashboard
* Manage repair appointments
* Manage technicians
* Manage spare parts
* Manage repair prices
* Manage customer payments
* View and manage branch operations
* Management logout

## 🏢 TechFix Branches

The application currently supports TechFix branches in:

* Colombo
* Galle

Customers can open the branch location directly through Google Maps.

## 🛠️ Technologies Used

| Technology              | Purpose                         |
| ----------------------- | ------------------------------- |
| Kotlin                  | Android application development |
| Android Studio          | Development environment         |
| Android SDK             | Android development             |
| XML                     | User interface layouts          |
| Firebase Authentication | User authentication             |
| Git                     | Version control                 |
| GitHub                  | Source code management          |


<div align="center">
  <table>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/0d417969-c0ff-4bda-b777-22aaeb010b09" width="180" height="350" alt="WhatsApp Image 2026-10-10 at 10 19 18"/></td>
      <td><img src="https://github.com/user-attachments/assets/ab19e2a5-3299-42b3-bdec-7597e5b5251b" width="180" height="350" alt="WhatsApp Image 2026-10-10 at 10 19 20 (1)"/></td>
      <td><img src="https://github.com/user-attachments/assets/b4912d7f-1155-49e6-bbc4-a6ec12657922" width="180" height="350" alt="WhatsApp Image 2026-10-10 at 10 19 19"/></td>
    </tr>
  </table>
</div>



## 📂 Project Structure

```text
TechFix/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/techfix/
│           │       ├── MainActivity.kt
│           │       ├── LoginActivity.kt
│           │       ├── RegisterActivity.kt
│           │       ├── CustomerHomeActivity.kt
│           │       ├── BookRepairActivity.kt
│           │       ├── MyBookingsActivity.kt
│           │       ├── MapActivity.kt
│           │       ├── BranchesActivity.kt
│           │       ├── ManagementDashboardActivity.kt
│           │       ├── ManageAppointmentsActivity.kt
│           │       ├── ManageTechniciansActivity.kt
│           │       ├── ManageSparePartsActivity.kt
│           │       └── ManagePaymentsActivity.kt
│           │
│           └── res/
│               ├── layout/
│               ├── drawable/
│               ├── mipmap/
│               └── values/
│
└── README.md
```

## 🎨 Design

The application follows a TechFix-themed interface using:

* Dark pink as the primary brand color
* White text and backgrounds
* Light neutral backgrounds
* Simple card-based layouts
* Clear navigation buttons
* Customer-friendly interface
* Management dashboard interface

The primary brand color is:

```text
#F13E93
```

## 🔐 Authentication

Firebase Authentication is used to manage user authentication.

The application provides:

* Customer registration
* Customer login
* Management login
* Firebase sign-out functionality

## 🗺️ Google Maps Integration

TechFix uses Android intents to open branch locations in Google Maps.

Supported locations include:

* TechFix Colombo
* TechFix Galle

If Google Maps is unavailable, the application can fall back to opening the location through a web browser.

## 👥 Team Development

This project is developed collaboratively using Git and GitHub.

Team members can:

1. Clone the repository
2. Create a branch for their work
3. Make changes
4. Commit changes
5. Push the branch to GitHub
6. Create a Pull Request
7. Review and merge changes

### Recommended Git Workflow

```text
main
 │
 ├── feature/customer-bookings
 │
 ├── feature/management-payments
 │
 ├── feature/repair-prices
 │
 └── feature/technician-management
```

This helps prevent different team members' changes from interfering with each other.

## 🚀 Getting Started

### Requirements

Before running the project, install:

* Android Studio
* Android SDK
* JDK
* Git
* Android Emulator or a physical Android device

### Clone the Repository

```bash
git clone https://github.com/Pabasara-Ranasinghe/TechFix
```

### Open the Project

1. Open Android Studio.
2. Select **Open**.
3. Select the cloned `TechFix` folder.
4. Allow Gradle to sync.
5. Connect an Android device or start an emulator.
6. Run the application.

## 📌 Current Development Status

The following components have been implemented or are currently being developed:

* [x] Project setup
* [x] Customer login and registration
* [x] Customer home screen
* [x] Repair service navigation
* [x] Repair booking screen
* [x] My Bookings screen
* [x] Branch information
* [x] Google Maps integration
* [x] Management dashboard
* [x] Appointment management
* [x] Technician management
* [x] Spare parts management
* [x] Repair price management
* [x] Payment management
* [ ] Complete backend/database integration
* [ ] Complete payment processing
* [ ] Complete repair tracking
* [ ] Complete customer profile
* [ ] Final testing and bug fixing

## 🔮 Future Improvements

Future versions of TechFix may include:

* Online payment integration
* Real-time repair status tracking
* Push notifications
* Technician assignment notifications
* Customer reviews and ratings
* Repair image uploads
* Detailed payment history
* Advanced management reports
* Improved Google Maps integration
* Backend API integration
* Cloud database integration

## 📄 License

This project is developed for educational and academic purposes.

## 👨‍💻 Development

**Project:** TechFix
**Platform:** Android
**Language:** Kotlin
**Development Environment:** Android Studio
**Version Control:** Git & GitHub
