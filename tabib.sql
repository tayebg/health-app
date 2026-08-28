-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2024 at 06:06 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tabib`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL,
  `user_admin` varchar(50) NOT NULL,
  `pass_admin` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `user_admin`, `pass_admin`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `med`
--

CREATE TABLE `med` (
  `ID_med` int(11) NOT NULL,
  `user_med` varchar(50) NOT NULL,
  `name_med` varchar(80) NOT NULL,
  `email_med` varchar(100) NOT NULL,
  `phone_med` varchar(15) NOT NULL,
  `pass_med` varchar(20) NOT NULL,
  `gender_med` varchar(20) NOT NULL,
  `id_admin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `med`
--

INSERT INTO `med` (`ID_med`, `user_med`, `name_med`, `email_med`, `phone_med`, `pass_med`, `gender_med`, `id_admin`) VALUES
(2, 'med', 'medic', 'med@email.com', '1234', '123', 'Female', 1),
(3, 'DOC', 'DOC', 'mp', '0231', '1234l', 'Female', 1);

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `ID_patient` int(11) NOT NULL,
  `User_pat` varchar(50) NOT NULL,
  `Name_pat` varchar(80) NOT NULL,
  `Email_pat` varchar(100) NOT NULL,
  `phone_pat` varchar(15) NOT NULL,
  `Pass_pat` varchar(50) NOT NULL,
  `ID_med` int(11) NOT NULL,
  `ID_admin` int(11) NOT NULL,
  `Gender_pat` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`ID_patient`, `User_pat`, `Name_pat`, `Email_pat`, `phone_pat`, `Pass_pat`, `ID_med`, `ID_admin`, `Gender_pat`) VALUES
(3, 'ra', 'ra', 'a', '1', '123', 2, 1, 'Female'),
(5, 'ahmed', 'ahmed', 'ahmed', '0212345', '123', 2, 1, 'Male'),
(6, 'oussama', 'oussama meghazi', 'poihffvy@gmail.com', '058965472', '102504', 2, 1, 'Male'),
(7, 'aaa', 'aaa', 'aaa', 'aaa', 'aaaa', 3, 1, 'Male'),
(8, 'ari', 'ari', 'ari', '123', 'ari', 2, 1, 'Male');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `id_sattus` int(11) NOT NULL,
  `Sugar` int(11) NOT NULL,
  `Temp` int(11) NOT NULL,
  `Weight` int(11) NOT NULL,
  `Tension` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `week` int(11) NOT NULL,
  `ID_patient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`id_sattus`, `Sugar`, `Temp`, `Weight`, `Tension`, `day`, `week`, `ID_patient`) VALUES
(13, 20, 40, 30, 60, 1, 1, 5),
(14, 20, 20, 20, 20, 2, 2, 3),
(15, 20, 13, 30, 58, 4, 2, 6),
(16, 30, 37, 80, 1, 1, 1, 3),
(17, 20, 45, 20, 10, 2, 1, 3),
(18, 10, 20, 10, 10, 3, 1, 3),
(19, 30, 28, 30, 1, 3, 1, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `user_admin` (`user_admin`),
  ADD UNIQUE KEY `pass_admin` (`pass_admin`);

--
-- Indexes for table `med`
--
ALTER TABLE `med`
  ADD PRIMARY KEY (`ID_med`),
  ADD UNIQUE KEY `user_med` (`user_med`),
  ADD UNIQUE KEY `email_med` (`email_med`),
  ADD UNIQUE KEY `phone_med` (`phone_med`),
  ADD KEY `ID_idmin` (`id_admin`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`ID_patient`),
  ADD UNIQUE KEY `User` (`User_pat`),
  ADD UNIQUE KEY `phone` (`phone_pat`),
  ADD UNIQUE KEY `Email_pat` (`Email_pat`),
  ADD KEY `ID_med` (`ID_med`),
  ADD KEY `ID_admin` (`ID_admin`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id_sattus`),
  ADD KEY `ID_pat` (`ID_patient`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `med`
--
ALTER TABLE `med`
  MODIFY `ID_med` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `ID_patient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `id_sattus` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `med`
--
ALTER TABLE `med`
  ADD CONSTRAINT `ID_idmin` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `ID_admin` FOREIGN KEY (`ID_admin`) REFERENCES `admin` (`id_admin`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ID_med` FOREIGN KEY (`ID_med`) REFERENCES `med` (`ID_med`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `status`
--
ALTER TABLE `status`
  ADD CONSTRAINT `ID_pat` FOREIGN KEY (`ID_patient`) REFERENCES `patient` (`ID_patient`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
