import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Random random = new Random();
    private static String[] dictionary = {"тетрадь", "яблоко", "бутылка"};
    private static Scanner scanner = new Scanner(System.in);
    private static int guessCount = 0;
    private static int correctGuesses = 0;
    private static Map<Character, Integer> charMap = new HashMap<>();

    public static void main(String[] args) {
        String word = getRandomWord(dictionary);
        charMap = initializeCharMap(word);
        while (guessCount < word.length()){
            guessCount++;
            String guess = getInput(scanner);
            correctGuesses += matchesCount(guess.charAt(0));
            if (correctGuesses == word.length()){
                System.out.println("Игрок победил");
                return;
            }
        }
        System.out.println("Игрок проиграл");
    }

    private static int matchesCount(Character guess){
        Integer value = charMap.get(guess);
        return (value != null) ? value : 0;
    }

    private static String getRandomWord(String[] source) {
        return dictionary[random.nextInt(dictionary.length)];
    }

    private static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }

    private static Map<Character, Integer> initializeCharMap (String word){
        Map<Character, Integer> temp = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
                Character currentChar = word.charAt(i);
                if (!temp.containsKey(currentChar)){
                    temp.put(currentChar, 1);
                } else {
                    temp.put(currentChar, temp.get(currentChar) + 1);
                }
        }
        return temp;
    }
}