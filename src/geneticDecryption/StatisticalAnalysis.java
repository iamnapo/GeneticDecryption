package geneticDecryption;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class StatisticalAnalysis {

	static String dirPath;

	static char[][] analysis() {
		char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		char[] realFreq = "etaoinsrhldcumfpgwybvkxjqz".toCharArray();
		int[] docLetters = new int[26];
		for (int i = 0; i < 26; i++) {
			docLetters[i] = 0;
		}
		FileReader reader = null;
		try {
			reader = new FileReader(dirPath + "/encrypted.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int nextChar;
		char ch;
		try {
			assert reader != null;
			while ((nextChar = reader.read()) != -1) {
				ch = Character.toLowerCase((char) nextChar);
				if (ch >= 'a' && ch <= 'z') {
					docLetters[ch - 'a']++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		char[][] mostUsedKeymap = new char[26][2];
		int tempInt;
		char tempChar;
		for (int i = 0; i < docLetters.length; i++) {
			mostUsedKeymap[i][1] = realFreq[i];
			for (int j = 1; j < docLetters.length - (i + 1); j++) {
				if (docLetters[j - 1] < docLetters[j]) {
					tempInt = docLetters[j - 1];
					tempChar = letters[j - 1];
					docLetters[j - 1] = docLetters[j];
					letters[j - 1] = letters[j];
					docLetters[j] = tempInt;
					letters[j] = tempChar;
				}
			}
		}
		for (int i = 0; i < docLetters.length; i++) {
			mostUsedKeymap[i][0] = letters[i];
		}
		return mostUsedKeymap;
	}
}
