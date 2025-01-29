import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Random random = new Random();
    //private static String[] dictionary = {"тетрадь", "яблоко", "бутылка"};
    private static String[] dictionary = {"ааб"};
    private static Scanner scanner = new Scanner(System.in);
    private static int guessCount = 0;
    private static int correctGuesses = 0;
    private static Map<Character, Integer> charMap = new HashMap<>();
    private static final String startGameMessage = "Желаете начать игру (да/нет)?";////////не забыть использовать
    private static final String invalidInput1 = "Неизвестная команда. Для старта игры введите да";////////не забыть использовать
    private static final String invalidInput2 = "Введён недопустимый символ. ";////////не забыть использовать
    private static final String playerWinMessage = "Игрок выиграл!";
    private static final String playerLoseMessage = "Игрок проиграл!";
    private static final String askToEnterLetter = "Введите букву";//менять формулировку или убрать вообще
    private static final String underscore = "_";//переименовать?
    private static Set<Character> guessedLetters = new HashSet<>();
    private static List<Character> wrongLetters = new ArrayList<>(); //переименовать?
    private static Pattern russianAlphabet = Pattern.compile("[а-яёА-ЯЁ]]");
        //Добавить вывод системных сообщений (введите букву, игрок выиграл, проиграл)

    public static void main(String[] args) {

        Pattern russianLetters = Pattern.compile("а-яА-Я");

        return;

        /*String word = getRandomWord(dictionary);

        charMap = initializeCharMap(word);
        while (guessCount < word.length()){
            guessCount++;
            printSolvedLetters(word, guessedLetters);
            String guess = castStringToLowerCase(getInput(scanner));
            if (charMap.containsKey(guess.charAt(0))){//дублирование
                guessedLetters.add(guess.charAt(0));
            } else {
                wrongLetters.add(guess.charAt(0));
            }
            correctGuesses += matchesCount(guess.charAt(0));
            if (correctGuesses == word.length()){
                printGameMessage(playerWinMessage);
                return;
            }
        }
        printGameMessage(playerLoseMessage);*/
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

    private static void printGameMessage(String message){
        System.out.println(message);
    }

    //отобразить

    private static void printSolvedLetters(String word,Set<Character> guessedLetters){//test
        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters.contains(word.charAt(i))){
                System.out.print(word.charAt(i));//дублирование
            } else {
                System.out.print(underscore);
            }
        }
        System.out.println();
    }

    private static String castStringToLowerCase(String source){
        return source.toLowerCase();
    }

    private static boolean isRussianAlphabetChar(String source){
        Matcher matcher = russianAlphabet.matcher(source);
        return (matcher.find());
    }



}