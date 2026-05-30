# LUXE STAY - Premium Hotel Booking System

A comprehensive web application built with **Spring Boot** that allows users to search for hotels, view available rooms, and make bookings. Featuring a premium, state-of-the-art UI with glassmorphism and smooth animations.

## ✨ Features

- **User Module**: Registration and login with hashed passwords and role-based access (USER/ADMIN).
- **Hotel Module**: Full CRUD operations for hotels (Admin only).
- **Booking Module**: Real-time room availability checks and booking history.
- **Search Module**: Find hotels by city and date range.
- **Payment Module**: Simulated Stripe payment processing.
- **Admin Dashboard**: Consolidated view of system activity and statistics.

## 🛠 Tech Stack

- **Backend**: Spring Boot 3, Spring Security, Spring Data JPA.
- **Database**: MySQL.
- **Frontend**: Thymeleaf, Vanilla CSS (Premium Glassmorphism Design).
- **Payments**: Stripe Simulation.

## 🚀 Getting Started

### Prerequisites

- Java 21+
- MySQL Server

### Installation

1. Clone the repository.
2. Update `src/main/resources/application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the application at `http://localhost:8081`.

## 🎨 Design Aesthetics

The application uses a **Premium Design System**:
- **Glassmorphism**: Semi-transparent UI elements with backdrop blur.
- **Dark Mode**: Sleek navy background with vibrant gradients.
- **Micro-animations**: Smooth transitions and hover effects.
- **Responsive**: Fully optimized for mobile and desktop.

## 🧪 Testing

Run JUnit tests using:
```bash
mvn test
```

## 📚 For Learners
If you are using this project to learn Java Fullstack development, please check out the [LEARNING_GUIDE.md](LEARNING_GUIDE.md) for a detailed walkthrough of the code and architecture.

---
*Created with ❤️ by Antigravity*
