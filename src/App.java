
public class App {

	public static void main(String[] args) {
		String path = "";
		try {
			path = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("No command line arguments loaded. Using sample file...");
			path = "File";
		}
		Loader a = new Loader(path);
		a.logBytes();
	}
}
