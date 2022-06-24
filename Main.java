package ll1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
            try (Scanner sc = new Scanner(System.in)){
                System.out.println("Utvonala a faljnak ami tartalmaza a Grammar (Hagyd uresen ha alapertelmezett szeretned):");
                String fileLocationPath = sc.nextLine();
                if(fileLocationPath.isEmpty()) {
                    String folder = new File("").getAbsolutePath();
                    fileLocationPath = folder + "\\ll1\\test.txt";
                }

                Grammar grammar = readGrammar(fileLocationPath);
                System.out.println("Hasznald a kovetkezo Grammar:\n");
                System.out.println(grammar);

                System.out.print("Szo annalizalasra: ");
                String word = sc.nextLine();
                LL1AnalysiserForSolutaion analysis = new LL1AnalysiserForSolutaion(grammar);
                LL1AnalysiserForSolutaion.Result result = analysis.analyse(word);

                if(result.isWordAccepted()) {
                    System.out.println("\nA szo része a grammar altal generalt nyelvnek: ");
                    for(String rule : result.getDerivation()) System.out.println(rule);
                }
                else {
                    System.out.println("\nA szo része a grammar altal generalt nyelvnek:");
                }

            }
            catch(ExceptionOfGrammar e) {
                e.printStackTrace();
            }
        }

        private static Grammar readGrammar(String grammarFilePath) throws ExceptionOfGrammar {
            File file = new File(grammarFilePath);
            Grammar gram = new Grammar();

            try(Scanner sc = new Scanner(file)){
                String line = sc.nextLine();
                for(String symbol : line.split("\\s*,\\s*")) gram.addTermSymbol(symbol.charAt(0));

                line = sc.nextLine();
                for(String symbol : line.split("\\s*,\\s*")) gram.addNonTermSymbol(symbol.charAt(0));

                line = sc.nextLine();
                if(!line.isEmpty()) gram.setStart(line.charAt(0));

                while(sc.hasNextLine()) {
                    line = sc.nextLine();
                    String[] sides = line.split("\\s*->\\s*");
                    gram.addProductionRule(sides[0].charAt(0), sides.length > 1 ? sides[1] : "");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return gram;
        }
}
