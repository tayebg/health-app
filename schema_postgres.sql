-- PostgreSQL Schema for E-Health / Tabib App

-- 1. Table Admin
CREATE TABLE IF NOT EXISTS admin (
    id_admin SERIAL PRIMARY KEY,
    user_admin VARCHAR(50) UNIQUE NOT NULL,
    pass_admin VARCHAR(50) NOT NULL
);

INSERT INTO admin (user_admin, pass_admin) 
VALUES ('admin', 'admin')
ON CONFLICT (user_admin) DO NOTHING;

-- 2. Table Med (Doctors)
CREATE TABLE IF NOT EXISTS med (
    id_med SERIAL PRIMARY KEY,
    user_med VARCHAR(50) UNIQUE NOT NULL,
    name_med VARCHAR(80) NOT NULL,
    email_med VARCHAR(100) UNIQUE NOT NULL,
    phone_med VARCHAR(20),
    pass_med VARCHAR(50) NOT NULL,
    gender_med VARCHAR(20),
    id_admin INT DEFAULT 1 REFERENCES admin(id_admin) ON DELETE SET NULL
);

INSERT INTO med (user_med, name_med, email_med, phone_med, pass_med, gender_med, id_admin)
VALUES 
('med', 'Dr. Smith', 'med@email.com', '0551234567', '123', 'Female', 1),
('doc', 'Dr. Jane Doe', 'doc@email.com', '0559876543', '123', 'Female', 1)
ON CONFLICT (user_med) DO NOTHING;

-- 3. Table Patient
CREATE TABLE IF NOT EXISTS patient (
    id_patient SERIAL PRIMARY KEY,
    user_pat VARCHAR(50) UNIQUE NOT NULL,
    name_pat VARCHAR(80) NOT NULL,
    email_pat VARCHAR(100) UNIQUE NOT NULL,
    phone_pat VARCHAR(20),
    pass_pat VARCHAR(50) NOT NULL,
    gender_pat VARCHAR(20),
    id_med INT REFERENCES med(id_med) ON DELETE SET NULL,
    id_admin INT DEFAULT 1 REFERENCES admin(id_admin) ON DELETE SET NULL
);

INSERT INTO patient (user_pat, name_pat, email_pat, phone_pat, pass_pat, gender_pat, id_med, id_admin)
VALUES 
('patient1', 'Alice Martin', 'alice@email.com', '0551122334', '123', 'Female', 1, 1),
('ahmed', 'Ahmed Ben', 'ahmed@email.com', '0559988776', '123', 'Male', 1, 1)
ON CONFLICT (user_pat) DO NOTHING;

-- 4. Table Status (Health Metrics)
CREATE TABLE IF NOT EXISTS status (
    id_status SERIAL PRIMARY KEY,
    sugar INT DEFAULT 0,
    temp INT NOT NULL,
    weight INT NOT NULL,
    tension INT NOT NULL,
    day INT NOT NULL,
    week INT NOT NULL,
    id_patient INT REFERENCES patient(id_patient) ON DELETE CASCADE
);

INSERT INTO status (temp, weight, tension, day, week, id_patient)
VALUES 
(37, 68, 120, 1, 1, 1),
(38, 69, 125, 2, 1, 1),
(37, 68, 118, 3, 1, 1),
(36, 67, 120, 4, 1, 1);
