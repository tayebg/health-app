package ehealth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AppTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     E-HEALTH SYSTEM INTEGRATION TEST SUITE       ");
        System.out.println("==================================================");

        testDatabaseConnection();
        testTableSchemas();
        testAdminAuthentication();
        testPatientRegistrationAndLogin();
        testDoctorManagement();
        testHealthMetricsPersistence();
        testUserModels();

        System.out.println("==================================================");
        System.out.printf("TEST SUMMARY: %d Passed, %d Failed%n", passed, failed);
        System.out.println("==================================================");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testDatabaseConnection() {
        try (Connection conn = DBConnection.getConnection()) {
            assertNotNull("Connection should not be null", conn);
            String dbProduct = conn.getMetaData().getDatabaseProductName();
            assertTrue("Database product name should not be empty", dbProduct != null && !dbProduct.isEmpty());
            pass("testDatabaseConnection (" + dbProduct + " connected)");
        } catch (Exception e) {
            fail("testDatabaseConnection: " + e.getMessage());
        }
    }

    private static void testTableSchemas() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            // Verify core tables can be queried
            stmt.executeQuery("SELECT count(*) FROM admin").close();
            stmt.executeQuery("SELECT count(*) FROM med").close();
            stmt.executeQuery("SELECT count(*) FROM patient").close();
            stmt.executeQuery("SELECT count(*) FROM status").close();
            pass("testTableSchemas (admin, med, patient, status exist)");
        } catch (Exception e) {
            fail("testTableSchemas: " + e.getMessage());
        }
    }

    private static void testAdminAuthentication() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM admin WHERE user_admin = ? AND pass_admin = ?")) {
            pstmt.setString(1, "admin");
            pstmt.setString(2, "admin");
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue("Default admin credentials should exist", rs.next());
                pass("testAdminAuthentication (admin login valid)");
            }
        } catch (Exception e) {
            fail("testAdminAuthentication: " + e.getMessage());
        }
    }

    private static void testPatientRegistrationAndLogin() {
        String testUser = "test_patient_" + System.currentTimeMillis();
        String testEmail = testUser + "@test.com";
        String testPass = "testpass123";

        try (Connection conn = DBConnection.getConnection()) {
            // Insert patient
            String insertSql = "INSERT INTO patient (User_pat, Name_pat, Email_pat, phone_pat, Pass_pat, Gender_pat, ID_med, ID_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, testUser);
                pstmt.setString(2, "Test Patient");
                pstmt.setString(3, testEmail);
                pstmt.setString(4, "0550000000");
                pstmt.setString(5, testPass);
                pstmt.setString(6, "Male");
                pstmt.setInt(7, 1);
                pstmt.setInt(8, 1);
                int affected = pstmt.executeUpdate();
                assertTrue("Patient insert should succeed", affected > 0);
            }

            // Verify login
            String loginSql = "SELECT * FROM patient WHERE (Email_pat = ? OR User_pat = ?) AND Pass_pat = ?";
            int patId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(loginSql)) {
                pstmt.setString(1, testEmail);
                pstmt.setString(2, testEmail);
                pstmt.setString(3, testPass);
                try (ResultSet rs = pstmt.executeQuery()) {
                    assertTrue("Patient should authenticate successfully", rs.next());
                    patId = rs.getInt("ID_patient");
                }
            }

            // Clean up test patient
            if (patId > 0) {
                try (PreparedStatement delStmt = conn.prepareStatement("DELETE FROM patient WHERE ID_patient = ?")) {
                    delStmt.setInt(1, patId);
                    delStmt.executeUpdate();
                }
            }

            pass("testPatientRegistrationAndLogin (create, verify login, clean up)");
        } catch (Exception e) {
            fail("testPatientRegistrationAndLogin: " + e.getMessage());
        }
    }

    private static void testDoctorManagement() {
        String testMedUser = "test_doctor_" + System.currentTimeMillis();
        String testMedEmail = testMedUser + "@hospital.com";

        try (Connection conn = DBConnection.getConnection()) {
            // 1. Add doctor
            String insertSql = "INSERT INTO med (user_med, name_med, email_med, phone_med, pass_med, gender_med, id_admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, testMedUser);
                pstmt.setString(2, "Dr. Test Specialist");
                pstmt.setString(3, testMedEmail);
                pstmt.setString(4, "0551112233");
                pstmt.setString(5, "docpass");
                pstmt.setString(6, "Female");
                pstmt.setInt(7, 1);
                pstmt.executeUpdate();
            }

            // 2. Fetch doctor ID
            int medId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT ID_med FROM med WHERE user_med = ?")) {
                pstmt.setString(1, testMedUser);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        medId = rs.getInt("ID_med");
                    }
                }
            }
            assertTrue("Doctor ID should be found", medId > 0);

            // 3. Update doctor
            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE med SET name_med = ? WHERE ID_med = ?")) {
                pstmt.setString(1, "Dr. Test Updated");
                pstmt.setInt(2, medId);
                int updated = pstmt.executeUpdate();
                assertTrue("Doctor update should succeed", updated > 0);
            }

            // 4. Delete doctor
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM med WHERE ID_med = ?")) {
                pstmt.setInt(1, medId);
                int deleted = pstmt.executeUpdate();
                assertTrue("Doctor delete should succeed", deleted > 0);
            }

            pass("testDoctorManagement (CRUD: insert, select, update, delete)");
        } catch (Exception e) {
            fail("testDoctorManagement: " + e.getMessage());
        }
    }

    private static void testHealthMetricsPersistence() {
        try (Connection conn = DBConnection.getConnection()) {
            String insertSql = "INSERT INTO status (Temp, Weight, Tension, day, week, ID_patient) VALUES (?, ?, ?, ?, ?, ?)";
            int statusId = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, 37);
                pstmt.setInt(2, 70);
                pstmt.setInt(3, 120);
                pstmt.setInt(4, 1);
                pstmt.setInt(5, 1);
                pstmt.setInt(6, 1);
                pstmt.executeUpdate();

                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        statusId = keys.getInt(1);
                    }
                }
            }

            // Verify query
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM status WHERE ID_patient = ? AND day = 1 AND week = 1")) {
                pstmt.setInt(1, 1);
                try (ResultSet rs = pstmt.executeQuery()) {
                    assertTrue("Status record should exist for patient", rs.next());
                    assertEquals("Temp should match", 37, rs.getInt("Temp"));
                    assertEquals("Weight should match", 70, rs.getInt("Weight"));
                    assertEquals("Tension should match", 120, rs.getInt("Tension"));
                }
            }

            pass("testHealthMetricsPersistence (status record insertion and verification)");
        } catch (Exception e) {
            fail("testHealthMetricsPersistence: " + e.getMessage());
        }
    }

    private static void testUserModels() {
        try {
            User user = new User("johndoe", "pass123", "John Doe", "Male", "john@email.com", "0550001122");
            assertEquals("Username should match", "johndoe", user.getUsername());
            assertEquals("Full name should match", "John Doe", user.getFullName());
            assertEquals("Gender should match", "Male", user.getGender());
            assertEquals("Email should match", "john@email.com", user.getEmail());
            assertEquals("Phone should match", "0550001122", user.getPhone());

            UserManager um = new UserManager();
            um.addUser(user);
            User auth = um.authenticate("johndoe", "pass123");
            assertNotNull("User should authenticate in UserManager", auth);

            pass("testUserModels (User and UserManager operations)");
        } catch (Exception e) {
            fail("testUserModels: " + e.getMessage());
        }
    }

    private static void pass(String name) {
        System.out.println("  [PASS] " + name);
        passed++;
    }

    private static void fail(String name) {
        System.err.println("  [FAIL] " + name);
        failed++;
    }

    private static void assertTrue(String message, boolean condition) {
        if (!condition) throw new AssertionError(message);
    }

    private static void assertNotNull(String message, Object obj) {
        if (obj == null) throw new AssertionError(message);
    }

    private static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) return;
        if (expected != null && !expected.equals(actual)) {
            throw new AssertionError(message + " (expected: " + expected + ", got: " + actual + ")");
        }
    }
}
