package util;

public class BitLearning {
	
	/*
	 * Returns a string with a 32-bit representation of an integer
	 * That is, a String in the following format:
	 * XXXX XXXX XXXX XXXX XXXX XXXX XXXX XXXX
	 */
	public static String getBinaryString(int number) {
		StringBuilder representation = new StringBuilder();
		int mask = 1 << 31;
		int count = 0;
		while (mask != 0) {
			char digit = (mask & number) == 0 ? '0' : '1';
			representation.append(digit);
			count++;
			if (count % 4 == 0) {
				representation.append(' ');
			}
			mask = mask >>> 1;
		}
		return representation.toString();
	}
	
}
