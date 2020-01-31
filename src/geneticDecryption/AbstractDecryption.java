package geneticDecryption;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public abstract class AbstractDecryption {

	void createDecryptedFile(String dirPath, char[][] keyMapForDecryption) throws Exception {
		System.out.println("Decrypting...");
		File decrypted = new File(dirPath + "/decrypted.txt");
		FileReader fileReader = new FileReader(dirPath + "/encrypted.txt");
		FileWriter fileWriter = new FileWriter(decrypted.getAbsolutePath());
		int temp, flag, thesi;
		while ((temp = fileReader.read()) != -1) {
			flag = 0;
			thesi = 0;
			for (int i = 0; i < 26; i++) {
				if (keyMapForDecryption[i][0] == Character.toLowerCase((char) temp)) {
					flag = 1;
					thesi = i;
					break;
				}
			}
			if (flag == 1) {
				fileWriter.write(keyMapForDecryption[thesi][1]);
			} else {
				fileWriter.write(Character.toLowerCase((char) temp));
			}
		}
		fileReader.close();
		fileWriter.close();
		System.out.println("Done!!");
	}

	File getEncryptedFileFromUser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.showOpenDialog(null);
		return fileChooser.getSelectedFile();
	}

	public abstract void decrypt(File encryptedFile) throws Exception;

}
