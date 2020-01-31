package geneticDecryption;

import java.io.File;
import java.util.ArrayList;

public class GeneticDecryption extends AbstractDecryption {

	public void decrypt(File encryptedFile) throws Exception {
		String directoryPath = encryptedFile.getParentFile().getAbsolutePath();
		StatisticalAnalysis.dirPath = directoryPath;
		Creature.dirPath = directoryPath;
		Genetic genetic = new Genetic(1000, 1);
		ArrayList<Creature> population = genetic.initializePopulation();
		char[][] bestSolution = genetic.geneticAlgorithm(population);
		createDecryptedFile(directoryPath, bestSolution);
	}

	public static void main(String[] args) throws Exception {
		GeneticDecryption decryptionAlgorithm = new GeneticDecryption();
		File selectedFile = decryptionAlgorithm.getEncryptedFileFromUser();
		decryptionAlgorithm.decrypt(selectedFile);
	}

}
