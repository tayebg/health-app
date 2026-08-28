package ehealth;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
    private static String cachedUrl = null;
    private static String cachedUser = null;
    private static String cachedPass = null;
    private static boolean initialized = false;

    // PostgreSQL connection profiles
    private static final String[] PG_DATABASES = {"tabib", "ehealth", "postgres"};
    private static final String[][] PG_CREDENTIALS = {
        {"postgres", "postgres"},
        {"postgres", "admin"},
        {"postgres", "root"},
        {"postgres", "123456"},
        {"postgres", "1234"},
        {"postgres", "password"},
        {"postgres", ""}
    };

    // MySQL connection profiles
    private static final String[] MY_DATABASES = {"tabib", "ehealth"};
    private static final String[][] MY_CREDENTIALS = {
        {"root", ""},
        {"Tabib", "abc123"},
        {"root", "root"},
        {"root", "123456"},
        {"root", "1234"}
    };

    public static Connection getConnection() throws SQLException {
        // 1. Try configuration from db.properties if exists
        Properties props = loadProperties();
        if (props != null) {
            String type = props.getProperty("db.type", "postgresql").trim().toLowerCase();
            String host = props.getProperty("db.host", "localhost").trim();
            String port = props.getProperty("db.port", type.equals("postgresql") ? "5432" : "3306").trim();
            String name = props.getProperty("db.name", "tabib").trim();
            String user = props.getProperty("db.user", type.equals("postgresql") ? "postgres" : "root").trim();
            String pass = props.getProperty("db.password", "").trim();

            String driver = type.equals("postgresql") ? "org.postgresql.Driver" : "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:" + type + "://" + host + ":" + port + "/" + name;
            if (type.equals("mysql")) {
                url += "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            }

            try {
                Class.forName(driver);
                Connection conn = DriverManager.getConnection(url, user, pass);
                if (conn != null && !conn.isClosed()) {
                    cachedUrl = url;
                    cachedUser = user;
                    cachedPass = pass;
                    ensureTablesExist(conn);
                    return conn;
                }
            } catch (Exception ignored) {}
        }

        // 2. If already cached, reuse
        if (cachedUrl != null) {
            try {
                Connection conn = DriverManager.getConnection(cachedUrl, cachedUser, cachedPass);
                if (conn != null && !conn.isClosed()) {
                    return conn;
                }
            } catch (SQLException ignored) {
                cachedUrl = null;
            }
        }

        // 3. Try PostgreSQL auto-discovery
        try {
            Class.forName("org.postgresql.Driver");
            for (String db : PG_DATABASES) {
                for (String[] cred : PG_CREDENTIALS) {
                    String url = "jdbc:postgresql://localhost:5432/" + db;
                    try {
                        Connection conn = DriverManager.getConnection(url, cred[0], cred[1]);
                        if (conn != null && !conn.isClosed()) {
                            cachedUrl = url;
                            cachedUser = cred[0];
                            cachedPass = cred[1];
                            System.out.println("[DB] Connected to PostgreSQL: " + url);
                            ensureTablesExist(conn);
                            return conn;
                        }
                    } catch (SQLException ignored) {}
                }
            }
        } catch (ClassNotFoundException ignored) {}

        // 4. Fallback to MySQL auto-discovery
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (String db : MY_DATABASES) {
                for (String[] cred : MY_CREDENTIALS) {
                    String url = "jdbc:mysql://localhost:3306/" + db + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                    try {
                        Connection conn = DriverManager.getConnection(url, cred[0], cred[1]);
                        if (conn != null && !conn.isClosed()) {
                            cachedUrl = url;
                            cachedUser = cred[0];
                            cachedPass = cred[1];
                            System.out.println("[DB] Connected to MySQL: " + url);
                            return conn;
                        }
                    } catch (SQLException ignored) {}
                }
            }
        } catch (ClassNotFoundException ignored) {}

        // 5. Final fallback connection attempt
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tabib", "postgres", "postgres");
    }

    private static Properties loadProperties() {
        File f = new File("db.properties");
        if (f.exists()) {
            try (FileInputStream fis = new FileInputStream(f)) {
                Properties props = new Properties();
                props.load(fis);
                return props;
            } catch (Exception ignored) {}
        }
        return null;
    }

    private static void ensureTablesExist(Connection conn) {
        if (initialized) return;
        try (Statement stmt = conn.createStatement()) {
            String dbProduct = conn.getMetaData().getDatabaseProductName().toLowerCase();
            if (dbProduct.contains("postgres")) {
                // Create PostgreSQL tables
                stmt.execute("CREATE TABLE IF NOT EXISTS admin (" +
                             "id_admin SERIAL PRIMARY KEY, " +
                             "user_admin VARCHAR(50) UNIQUE NOT NULL, " +
                             "pass_admin VARCHAR(50) NOT NULL);");

                stmt.execute("CREATE TABLE IF NOT EXISTS med (" +
                             "id_med SERIAL PRIMARY KEY, " +
                             "user_med VARCHAR(50) UNIQUE NOT NULL, " +
                             "name_med VARCHAR(80) NOT NULL, " +
                             "email_med VARCHAR(100) UNIQUE NOT NULL, " +
                             "phone_med VARCHAR(20), " +
                             "pass_med VARCHAR(50) NOT NULL, " +
                             "gender_med VARCHAR(20), " +
                             "id_admin INT DEFAULT 1);");

                stmt.execute("CREATE TABLE IF NOT EXISTS patient (" +
                             "id_patient SERIAL PRIMARY KEY, " +
                             "user_pat VARCHAR(50) UNIQUE NOT NULL, " +
                             "name_pat VARCHAR(80) NOT NULL, " +
                             "email_pat VARCHAR(100) UNIQUE NOT NULL, " +
                             "phone_pat VARCHAR(20), " +
                             "pass_pat VARCHAR(50) NOT NULL, " +
                             "gender_pat VARCHAR(20), " +
                             "id_med INT, " +
                             "id_admin INT DEFAULT 1);");

                stmt.execute("CREATE TABLE IF NOT EXISTS status (" +
                             "id_status SERIAL PRIMARY KEY, " +
                             "sugar INT DEFAULT 0, " +
                             "temp INT NOT NULL, " +
                             "weight INT NOT NULL, " +
                             "tension INT NOT NULL, " +
                             "day INT NOT NULL, " +
                             "week INT NOT NULL, " +
                             "id_patient INT);");

                // Seed admin if not present
                try {
                    stmt.execute("INSERT INTO admin (user_admin, pass_admin) VALUES ('admin', 'admin') ON CONFLICT (user_admin) DO NOTHING;");
                } catch (Exception ignored) {}
            }
            initialized = true;
        } catch (Exception ex) {
            System.err.println("[DB] Notice initializing tables: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");
        try (Connection conn = getConnection()) {
            System.out.println("[SUCCESS] Connected to: " + conn.getMetaData().getDatabaseProductName() + " " + conn.getMetaData().getDatabaseProductVersion());
            System.out.println("[URL]: " + conn.getMetaData().getURL());
            System.out.println("[User]: " + conn.getMetaData().getUserName());
        } catch (Exception e) {
            System.err.println("[FAILED] " + e.getMessage());
        }
    }
}
