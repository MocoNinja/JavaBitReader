import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import util.BitLearning;
import util.TextUtils;

public class Loader {
	private byte[] DATA = null;
	private final String filename;
	
	public Loader(String file) {
		filename = file;
		try {
			DATA = Files.readAllBytes(new File(file).toPath());
			System.out.println("Successfully read " + DATA.length + " bytes in " + file + "!");
		} catch (IOException e) {
			System.err.println("Error reading " + file);
		}
	}
	
	public void readBytes() {
		if (DATA == null) {
			System.err.println("There is no loaded data. Exiting now...");
			System.exit(1);
		} else {
			for (Byte b : DATA) {
				String msg = String.format("Read value of %6d equals to binary string %s...", b, BitLearning.getBinaryString(b));
				System.out.println(msg);
			}
		}
	}
	
	public void logBytes() {
		Scanner in = new Scanner(System.in);
		String logName = "";
		System.out.print("Enter the name of your log file: ");
		
		try {
			logName = in.nextLine();
			if (logName.equals("")) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.err.println("Error creating the log. Using the default one...");
			logName = "LOG.log";
		}
		
		// Don't close the scanner here, because it shall close de InputStream
		// Thus, rendering next tries to use it useless!
		
		
		File log = new File(logName);
		
		if (log.exists()) {
			System.out.println("Specified file already exists!!");
			System.out.print("Do you wish to remove it? (y/n): ");
			in = new Scanner(System.in);
			String answer = in.nextLine();
			String pattern = "[yY]|[yY]es|[yY]ES|[sS]|[sS][íiIÍ]";
			boolean overwrite = answer.matches(pattern);
			if (overwrite) {
				System.out.println("Overwriting the file...");
				log.delete();
				createFile(log);
			} else {
				System.out.println("Appending new data to existing log!");
			}
		} else {
			System.out.println("Specified file does not exist!. Creating it...");
			createFile(log);
		}
		
		
		PrintWriter writer = null;
		try {
			FileWriter accessToFile = new FileWriter(log, true);
			writer = new PrintWriter(new BufferedWriter(accessToFile)); // BufferedWriter -> Performance; PrintWriter -> Convenience; Both -> WIN;
			if (DATA == null) {
				System.err.println("There is no loaded data. Exiting now...");
				System.exit(1);
			} else {
				final String SEPARATOR = TextUtils.stringProduct("-", 80);
				writer.println(SEPARATOR);
				final String id = String.format("Creating log of file '%s' at '%s'", filename, 
						new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
				writer.println(id);
				writer.println(SEPARATOR);
				int index = 0;
				double currProgress = 0.0, oldProgress = 0.0;
				for (Byte b : DATA) {
					currProgress = TextUtils.getProgress(index, 0, DATA.length);
					if (currProgress != oldProgress) {
						System.out.println(TextUtils.getProgressString(
								currProgress, 80, "."));
					}
					String msg = String.format("%3d:%s", b, BitLearning.getBinaryString(b));
					oldProgress = currProgress;
					index++;
					writer.println(msg);
				}
			}
			
		} catch (IOException e) {
			System.err.println("ERROR: FILE COULD NOT BE ACCESED. IS IT OPEN OR IN A FORBIDDEN PATH??");
		} finally {
			in.close();
			writer.close();
		}
		
	}
	
	private void createFile(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.err.println("ERROR: FILE COULD NOT BE ACCESED. IS IT OPEN OR IN A FORBIDDEN PATH??");
		}
	}
}
