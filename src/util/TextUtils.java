package util;

public class TextUtils {
	public static String stringProduct(String base, int times) {
		StringBuilder result = new StringBuilder();
		while (times-- > 0) {
			result.append(base);
		}
		return result.toString();
	}
	
	public static Double getProgress(int currentStep, int startingPoint, int finalPoint) {
		int increment = (finalPoint - startingPoint) / 100;
		double progress = currentStep / increment;
		return progress;
	}
	
	public static String getProgressString(double value, int width, String filler) {
		String msgIntro = "Progress: ";
		String msgInfo = String.format("%.0f%%", value);
		int availableSpaces = width - msgInfo.length() - msgIntro.length();
		String decoration = stringProduct(filler, availableSpaces);
		return msgIntro + decoration + msgInfo;
	}
}
