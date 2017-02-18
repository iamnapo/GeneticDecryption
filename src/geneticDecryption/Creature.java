package geneticDecryption;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Creature implements Comparable<Creature>{


    char[][] genotype;
    int evaluation;
    static ArrayList<char[]> correctKeyPairs = new ArrayList<>();
    static String dirPath;
    private static final ThreadLocal<String> libPath = ThreadLocal.withInitial(() -> "/Users/Napoleon/Documents/GeneticDecryption/lib");
    private static char[][] mostUsed = StatisticalAnalysis.analysis();

    Creature(){
        this.evaluation = 0;
        this.genotype = new char[26][2];
        char temp;
        Random random = new Random();
        for (int i = 0; i < 26; i++) {this.genotype[i][0] = (char)('a' + i);}
        int j,randomA,randomB;
        for (int i = 0; i < 26; i++) {
            j = 0;
            for(;;) {
                if (j == 25 ||mostUsed[j][0] == this.genotype[i][0]) {
                    this.genotype[i][1] = mostUsed[j][1];
                    break;
                }
                j++;
            }
        }
        for (int i = 0; i < 5; i++) {
            randomA = random.nextInt(26);
            randomB = random.nextInt(26);
            temp = this.genotype[randomA][1];
            this.genotype[randomA][1] = this.genotype[randomB][1];
            this.genotype[randomB][1] = temp;
        }
    }

    Creature createOffspring(Creature partner) {
        Creature offspring = new Creature();
        boolean[] isUsed = new boolean[26];
        Random random = new Random();
        char[][] tempGenotype = offspring.genotype;
        for(int i = 0; i < 26; i++) {
            isUsed[i] = false;
            tempGenotype[i][0] = this.genotype[i][0];
        }
        for(char[] correctChars : correctKeyPairs) {
            if (random.nextInt(10) != 9)
            tempGenotype[correctChars[0]-'a'][1] = correctChars[1];
            isUsed[correctChars[0]-'a'] = true;
        }

        for(int i = 0; i < 26; i++) {
            if ( !isUsed[i] ) {
                tempGenotype[i][1] = random.nextInt(2) == 1 ? this.genotype[i][1] : partner.genotype[i][1];
                isUsed[i] = true;
            }
        }
        offspring.genotype = tempGenotype;
        return offspring;
    }

    void creatureEvaluation() throws Exception{
        TokenSearcher tokenSearcher = new TokenSearcher();
        File decrypted = new File(libPath.get() + "/decrypted.txt");
        FileReader fileReader = new FileReader(dirPath + "/encrypted.txt");
        FileWriter fileWriter = new FileWriter(decrypted.getAbsolutePath());
        int temp,flag,thesi;
        while( (temp = fileReader.read()) != -1) {
            flag = 0;
            thesi=0;
            for(int i = 0; i<26; i++) {
                if (this.genotype[i][0] == Character.toLowerCase((char) temp)){
                    flag = 1;
                    thesi = i;
                    break;
                }
            }
            if (flag ==1) {
                fileWriter.write(this.genotype[thesi][1]);
            }else {
                fileWriter.write(Character.toLowerCase((char) temp));
            }
        }
        fileReader.close();
        fileWriter.close();
        FileInputStream fis = new FileInputStream(decrypted.getAbsolutePath());
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        File indexDir = new File(libPath.get() + "/IndexDir");
        char tempChar;
        String [] tokens;
        int thesiStoGenotype,flag1,j;
        while ((line = br.readLine()) != null) {
            tokens = line.split("\\s+");
            for (String wordInTokens : tokens) {
                if (tokenSearcher.searchIndex(indexDir,wordInTokens) != 0 ) {
                    this.evaluation++;
                    int k = 0;
                    while( k != wordInTokens.length()) {
                        flag1 = 0;
                        tempChar = wordInTokens.charAt(k);
                        j = 0;
                        for(;;) {
                            if(j == 26 || this.genotype[j][1] == tempChar) {
                                thesiStoGenotype = j;
                                break;
                            }
                            j++;
                        }
                        for (char[] charTable : correctKeyPairs) {
                            if ( j == 26 || this.genotype[thesiStoGenotype][0] == charTable[0] || this.genotype[thesiStoGenotype][1] == charTable[1]) {
                                flag1 = 1;
                                break;
                            }
                        }
                        if (flag1 == 0) {
                            char[] tempArray = new char[2];
                            tempArray[1] = tempChar;
                            tempArray[0] = this.genotype[thesiStoGenotype][0];
                            correctKeyPairs.remove(tempArray);
                            correctKeyPairs.add(tempArray);
                        }
                        k++;
                    }
                }
            }
        }

        }

    @Override
    public int compareTo(Creature o) {
        int compareEvaluation = o.evaluation;
        return compareEvaluation - this.evaluation;
    }
}
