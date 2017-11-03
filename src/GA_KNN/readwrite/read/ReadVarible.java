package GA_KNN.readwrite.read;

public abstract class ReadVarible {
	
	public static int dataNum;			// number of data
	public static int dimNum;			// number of dimension
	public static int classNum;			// number of class
	public static String[][] dataStr;	// data string
	public static double[][] data;		// data
	public static String[] classStr;	// class string
	public static String[] className;	// class name
	public static int[] classValue;		// class value
	public static String fileName = "iris.arff";
	public static String readPath = "";
	//
	public static String[] files_name_array;
	public static boolean normalize_check = true;		// Normalization check  true: normalized, false: don't normalized
}
