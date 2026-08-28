package pages;

public class GlobalData {
	
	 public static int id_pa; 
	
	
  
    public static int[][] Temp;
    public static int[][] wei;
    public static int[][] ten;


	protected static int id_med;

    static {
       
        // Initialize and set default value for Temp matrix
        Temp = new int[6][7];
        for (int i = 0; i < Temp.length; i++) {
            for (int j = 0; j < Temp[i].length; j++) {
                Temp[i][j] = 10; // Set default value to 10
            }
        }

        // Initialize and set default value for hig matrix
        wei = new int[6][7];
        for (int i = 0; i < wei.length; i++) {
            for (int j = 0; j < wei[i].length; j++) {
            	wei[i][j] = 10; // Set default value to 10
            }
        }

        // Initialize and set default value for ten matrix
        ten = new int[6][7];
        for (int i = 0; i < ten.length; i++) {
            for (int j = 0; j < ten[i].length; j++) {
                ten[i][j] = 10; // Set default value to 10
            }
        }
    }
}
