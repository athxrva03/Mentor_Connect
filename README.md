# MentorBridge – Institutional Mentor-Mentee Management System

MentorBridge is a centralized web platform designed for institutional mentorship management. It solves the lack of accountability in mentorship by introducing structured scheduling, assignment tracking, and mentor-based verification.

## 🚀 Project Overview

- **Institutional Scope**: Restricted access via College/Organization ID.
- **Verification-Based Workflow**: All mentorship activities (Meetings, Assignments, Logs) must be verified by a mentor.
- **Role-Based Access**: Specialized dashboards for Admin, Mentors, and Mentees.

## 🛠 Tech Stack

- **Frontend**: HTML5, CSS3, Vanilla JavaScript (Modern Portal Design)
- **Backend**: Java Spring Boot 3, Spring Security, JWT
- **Database**: MySQL

## 📂 Project Structure

```
Mentor_Connect/
├── backend/            # Spring Boot Application (Port 8080)
├── frontend/           # Static Portal Files (Port 8000 recommended)
└── sql/                # Updated Institutional Schema
```

## ⚙️ Setup Instructions

### 1. Database Setup
1. Execute the script in `sql/schema.sql` in your MySQL environment.
2. The database `mentor_connect` will be created with institutional tables.

### 2. Backend Setup
1. Open the `backend` folder in your IDE.
2. Ensure you have **Java 17+** installed.
3. Update `src/main/resources/application.properties` with your database credentials.
4. Run `mvn spring-boot:run`. The server starts on `http://localhost:8080`.
5. **Note**: A default Admin is automatically created:
   - **Email**: `admin@gmail.com`
   - **Password**: `admin123`

### 3. Frontend Setup
1. To avoid CORS issues and enable full functionality, serve the `frontend` folder using a local web server.
2. **Using Python**: `python -m http.server 8000` inside the `frontend` directory.
3. **Using VS Code**: Right-click `index.html` and select "Open with Live Server".
4. Open `http://localhost:8000` in your browser.

## 🎯 Core Features

- **Admin Control**: Manage users and officially map mentors to mentees.
- **Verification Engine**: "Track → Verify → Improve". Mentors validate assignments and logs.
- **Mentorship Log System**: Students maintain verifiable logs of every session.
- **Institutional Identity**: Tracking via verified College IDs.

## 🎨 UI/UX Structure

- **Portal Aesthetic**: Dark/Light mode supported throughout.
- **Fixed Sidebar**: Modern dashboard layout for efficient navigation.
- **Premium Cards**: Glassmorphism and gradient-based data visualization.
