CREATE DATABASE IF NOT EXISTS mentor_connect;
USE mentor_connect;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('MENTOR', 'MENTEE', 'ADMIN') NOT NULL,
    college_id VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Mentor-Mentee Mapping
CREATE TABLE IF NOT EXISTS mentor_mentee_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mentor_id BIGINT NOT NULL,
    mentee_id BIGINT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (mentee_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE(mentee_id) -- A mentee usually has one main mentor
);

-- Mentor Specific Data
CREATE TABLE IF NOT EXISTS mentor_profiles (
    user_id BIGINT PRIMARY KEY,
    skills TEXT,
    experience VARCHAR(255),
    bio TEXT,
    profile_picture VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Mentee Specific Data
CREATE TABLE IF NOT EXISTS mentee_profiles (
    user_id BIGINT PRIMARY KEY,
    interests TEXT,
    goals TEXT,
    education VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Meetings Table
CREATE TABLE IF NOT EXISTS meetings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mentor_id BIGINT NOT NULL,
    mentee_id BIGINT NOT NULL,
    topic VARCHAR(255) NOT NULL,
    meeting_date DATE NOT NULL,
    meeting_time TIME NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    link VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (mentee_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Assignments
CREATE TABLE IF NOT EXISTS assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    file_path VARCHAR(500),
    submission_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('SUBMITTED', 'VERIFIED') DEFAULT 'SUBMITTED',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Mentorship Logs
CREATE TABLE IF NOT EXISTS logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mentor_id BIGINT NOT NULL,
    mentee_id BIGINT NOT NULL,
    topic VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (mentee_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Quizzes (Optional but kept for functionality)
CREATE TABLE IF NOT EXISTS quizzes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mentor_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    total_marks INT DEFAULT 100,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Quiz Results
CREATE TABLE IF NOT EXISTS quiz_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quiz_id BIGINT NOT NULL,
    mentee_id BIGINT NOT NULL,
    score INT NOT NULL,
    taken_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE,
    FOREIGN KEY (mentee_id) REFERENCES users(id) ON DELETE CASCADE
);
