# 👁️ FaceGuard – Face Recognition Attendance System

**FaceGuard** is a smart attendance system that uses face recognition to track employee entry and exit times. It calculates daily working hours and automates payroll — ideal for offices, retail stores, educational institutions, and more.

---

## 🛠 Tech Stack

### 🔙 Backend (API)
- **Java Spring Boot** – Core REST API
- **JWT (Bearer Token)** – Authentication & Authorization
- **MySQL/PostgreSQL** – Database
- **Flask + face_recognition** – Facial recognition service

### 🌐 Frontend
- **React** – Modern UI for admins and users
- **Axios** – API communication
- **React Router** – Routing
- **Bootstrap / Tailwind** – Styling (customizable)

### 📱 Hardware
- Uses **mobile or webcam** to capture employee faces
- Future support for **external IP camera integration**

---

## 📦 Features

- ✅ User registration using **phone number**
- 📷 Face registration with webcam
- 🔐 Secure login with **JWT**
- 🕒 Real-time check-in/check-out
- 🧮 Auto calculation of worked hours & pay
- 📊 Admin dashboard for attendance reports
- 👥 Role-based access: Admin / Employee
- 📁 Daily summary logs per user
- 🔁 API integrated Python-based facial recognition microservice
- 🔍 Optional filtering by date, user, or department

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/sirliboyev-uz/faceguard.git
cd faceguard
```

### 2. Backend – Spring Boot

```bash
cd backend
./mvnw spring-boot:run
```

- Set your DB connection in `application.properties`
- API will run at: `http://localhost:8080`

### 3. Face Recognition – Flask API

```bash
cd face-recognition-api
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
python app.py
```

- Runs on: `http://localhost:5000`

### 4. Frontend – React

```bash
cd frontend
npm install
npm start
```

- App runs at: `http://localhost:3000`

---

## 🧪 API Overview

| Endpoint               | Method | Description                      |
|------------------------|--------|----------------------------------|
| `/api/auth/login`      | POST   | User login with phone number     |
| `/api/auth/register`   | POST   | Register user + face encoding    |
| `/api/attendance/in`   | POST   | Log check-in with image          |
| `/api/attendance/out`  | POST   | Log check-out with image         |
| `/api/admin/reports`   | GET    | Get daily/monthly reports        |

---

## 📸 Face Registration Flow

1. User registers via frontend form
2. Face image sent to Flask API
3. Face encoding is stored on server
4. For attendance, live face is compared with encoding

---

## 🔐 JWT Auth Header Format

```
Authorization: Bearer <your_token_here>
```

---

## 📅 Future Plans

- 📡 IP camera integration
- 🔔 Push notifications for late entry
- 📤 Excel/PDF export of reports
- 📱 Mobile app (Flutter or React Native)
- 🧠 AI-based performance analysis

---

## 🤝 Contributing

Pull requests and feedback are welcome! Please open issues for bugs or feature requests.

---

## ✨ Author

Made with ❤️ by Umurzak Sirliboev  
Telegram: [sirliboyevuz](https://t.me/sirliboyevuz)
