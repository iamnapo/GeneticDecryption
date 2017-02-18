package geneticDecryption;


import java.util.ArrayList;
import java.util.Collections;

class Genetic {

    private int numberOfPopulation;
    private int maxGenerations;

    Genetic(int numberOfPopulation, int maxGenerations) {
        this.numberOfPopulation = numberOfPopulation;
        this.maxGenerations = maxGenerations;
    }


    ArrayList<Creature> initializePopulation() throws Exception{
        ArrayList<Creature> population = new ArrayList<>();
        for (int i = 0; i < this.numberOfPopulation; i++) {
            Creature newCreature = new Creature();
            newCreature.creatureEvaluation();
            population.add(newCreature);
        }
        return population;
    }

    char[][] geneticAlgorithm(ArrayList<Creature> population) throws Exception {
        char[][] bestSolution;
        int numberOfGenerations = 0,i;
        Collections.sort(population);
        while (numberOfGenerations < this.maxGenerations) {
            System.out.println("Size = " + Creature.correctKeyPairs.size());
            int halfSize = population.size()/2;
            for (i = 0; i < halfSize; i++) { population.remove(population.size()-1);}
            i = 0;
            while(population.size() < numberOfPopulation) {
                Creature offspring = population.get(i).createOffspring(population.get(i+1));
                offspring.creatureEvaluation();
                population.add(offspring);
                i++;
            }
            numberOfGenerations++;
            System.out.print(numberOfGenerations);
            Collections.sort(population);
            System.out.println(" " + population.get(0).evaluation);

        }
        bestSolution = population.get(0).genotype;
        for (char[] charTable : bestSolution) {
            System.out.println(charTable[0] + " " + charTable[1]);
        }
        return bestSolution;
    }
}