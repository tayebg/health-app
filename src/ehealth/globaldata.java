package ehealth;

public class globaldata {
    public static final int WEEKS = 7;  // Number of weeks
    public static final int DAYS = 7;   // Number of days per week
    public static final int DEFAULT_VALUE = 10; // Default value for health metrics

    // Session data
    public static int id_pa = 1;         // Logged-in Patient ID
    public static int id_med = 1;        // Logged-in Doctor ID
    public static int id_admin = 1;      // Logged-in Admin ID
    public static String currentUserName = "Guest";
    public static String currentUserRole = "patient"; // patient, medcine, admin

    // In-memory health data matrices
    public static int[][] Temp;
    public static int[][] wei;
    public static int[][] ten;

    static {
        Temp = new int[WEEKS][DAYS];
        initializeArray(Temp, 37); // Default temperature ~37°C

        wei = new int[WEEKS][DAYS];
        initializeArray(wei, 70);  // Default weight ~70kg

        ten = new int[WEEKS][DAYS];
        initializeArray(ten, 120); // Default tension ~120 mmHg
    }

    private static void initializeArray(int[][] array, int defaultVal) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = defaultVal;
            }
        }
    }
}
