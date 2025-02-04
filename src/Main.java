import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String INVALID_YES_OR_NO_INPUT = "Неизвестная команда. Необходимо ввести да или нет!";
    private static final String INVALID_CHAR_INPUT = "Неизвестная команда. Необходимо ввести один символ русского алфавита!";
    private static final String PLAYER_WIN_MESSAGE = "Игрок выиграл!";
    private static final String PLAYER_LOSE_MESSAGE = "Игрок проиграл!";
    private static final String START_GAME_AGAIN_MESSAGE = "Хотите сыграть ещё раз (да/нет)?";
    private static final String GAME_NOT_FINISHED = "Игра не закончена";
    private static final String UNSOLVED_CHAR = "_";
    private static final Pattern russianAlphabet = Pattern.compile("[а-яёА-ЯЁ]");
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final Map<Character, Integer> charMap = new HashMap<>();//переименовать?
    private static List<String> dictionary = new ArrayList<>();
    private static final Set<Character> solvedLetters = new HashSet<>();
    private static final List<Character> wrongGuessedLetters = new ArrayList<>();
    private static final int WRONG_ANSWERS_LIMIT = 6;
    private static int wrongAnswersCounter = 0;
    private static int solvedLettersCounter = 0;

    public static void main(String[] args) {
        while (true) {
            startGameRound();
            printGameMessage(START_GAME_AGAIN_MESSAGE);
            if (hasToStartNewRound()) {
                clearData();
                continue;
            }
            break;
        }
    }
    private static void startGameRound() {
        fillTheDictionary();//корректна ли обработка, если словарь не инициализировался, то программа вылетает IllegalArgumentException
        //+дополнить файл словами, чтобы их побольше было
        String wordForGuessing = getRandomWord(dictionary);
        initializeCharMap(wordForGuessing);
        startGameLoop(wordForGuessing);
    }
    private static void startGameLoop(String wordForGuessing) {
        while (wrongAnswersCounter <= WRONG_ANSWERS_LIMIT) {
            printCurrentGameState(wordForGuessing);
            if (!getResultOfRound(wordForGuessing.length()).equals(GAME_NOT_FINISHED)) {
                printGameMessage(getResultOfRound(wordForGuessing.length()));
                break;
            }
            String guessFullString = getInput(SCANNER);
            if (!isInputCorrect(guessFullString)) {
                printGameMessage(INVALID_CHAR_INPUT);
                continue;
            }
            processEnteredChar(guessFullString.toLowerCase().charAt(0));
        }
    }
    private static String getRandomWord(List<String> source) {
        return dictionary.get(RANDOM.nextInt(dictionary.size()));
    }
    private static void initializeCharMap(String word) {
        for (int i = 0; i < word.length(); i++) {
            Character currentChar = word.charAt(i);
            if (!charMap.containsKey(currentChar)) {
                charMap.put(currentChar, 1);
            } else {
                charMap.put(currentChar, charMap.get(currentChar) + 1);
            }
        }
    }
    private static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }
    private static void printGameMessage(String message) {
        System.out.println(message);
    }
    private static void printGallowsState(int wrongAnswersCounter) {
        GallowsDrawing.drawCurrentGallowsState(wrongAnswersCounter);
    }
    private static void printSolvedLetters(String word, Set<Character> solvedLetters) {
        System.out.print("Слово:");
        for (int i = 0; i < word.length(); i++) {
            if (solvedLetters.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));//дублирование
            } else {
                System.out.print(UNSOLVED_CHAR);
            }
        }
        System.out.println();
    }
    private static void printWrongChars(int wrongAnswersCounter) {//refact
        if (wrongAnswersCounter == 0) {
            System.out.print("Ошибки (" + wrongAnswersCounter + "): \r\n");
        } else {
            System.out.print("Ошибки (" + wrongAnswersCounter + "): ");
            for (int i = 0; i < wrongGuessedLetters.size(); i++) {
                if (i == 0) {
                    System.out.print(wrongGuessedLetters.get(i));
                } else {
                    System.out.print(", " + wrongGuessedLetters.get(i));
                }
            }
            System.out.println();
        }
    }
    private static void printCurrentGameState(String word) {//test
        printGallowsState(wrongAnswersCounter);
        printSolvedLetters(word, solvedLetters);
        printWrongChars(wrongAnswersCounter);
    }
    private static boolean checkStringIsNullOrEmpty(String source) {//test
        return (source == null || source.trim().isEmpty());
    }
    private static boolean isInputCorrect(String source) {//test
        boolean isValid = false;
        if (checkStringIsNullOrEmpty(source)) {
            return isValid;
        }
        if (isOneSymbolLength(source) && isRussianAlphabetChar(source.charAt(0))) {
            isValid = true;
        }
        return isValid;
    }
    private static boolean isOneSymbolLength(String sourse) {//test
        return sourse.length() == 1;
    }
    private static boolean isRussianAlphabetChar(char source) {
        Matcher matcher = russianAlphabet.matcher(String.valueOf(source));
        return (matcher.find());
    }
    private static String getResultOfRound(int wordLength) {//test
        if (wrongAnswersCounter == WRONG_ANSWERS_LIMIT) {
            return PLAYER_LOSE_MESSAGE;
        } else if (solvedLettersCounter == wordLength) {
            return PLAYER_WIN_MESSAGE;
        } else {
            return GAME_NOT_FINISHED;
        }
    }
    private static int matchesCount(Character guess) {
        Integer value = charMap.get(guess);
        return (value != null) ? value : 0;
    }
    private static void processEnteredChar(char guess) {//test
        if (charMap.containsKey(guess)) {
            solvedLetters.add(guess);
            solvedLettersCounter += matchesCount(guess);
            charMap.put(guess, 0);//чтобы повторный ввод отгаданной буквы не увеличивал счётчик
        } else {
            wrongGuessedLetters.add(guess);
            wrongAnswersCounter++;
        }
    }
    private static boolean hasToStartNewRound() {//test
        boolean needToStartNewRound = false;
        while (true) {
            String decision = getInput(SCANNER);
            if (decision.equalsIgnoreCase("да")) {
                needToStartNewRound = true;
                break;
            } else if (decision.equalsIgnoreCase("нет")) {
                break;
            } else {
                printGameMessage(INVALID_YES_OR_NO_INPUT);
            }
        }
        return needToStartNewRound;
    }
    private static void fillTheDictionary() {
        try {
            dictionary = Files.readAllLines(Paths.get("Dictionary.txt"));
        } catch (IOException ex) {
            System.out.println("Не удалось считать файл Dictionary.txt, необходимый для инициализации игрового словаря.");
        }
    }
    private static void clearData() {//test
        charMap.clear();
        solvedLetters.clear();
        wrongGuessedLetters.clear();
        solvedLettersCounter = 0;
        wrongAnswersCounter = 0;
    }
}