import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static final String DICTIONARY_FILE_PATH = "Dictionary.txt";
    private static final String INVALID_YES_OR_NO_INPUT = "Неизвестная команда. Необходимо ввести да или нет!";
    private static final String INVALID_LETTER_INPUT = "Неизвестная команда. Необходимо ввести один символ русского алфавита!";
    private static final String PLAYER_WIN = "Игрок выиграл!";
    private static final String PLAYER_LOSE = "Игрок проиграл!";
    private static final String START_GAME_AGAIN_MESSAGE = "Начать новую игру (да/нет)?";
    private static final String GAME_NOT_FINISHED = "Игра не закончена";
    private static final String UNSOLVED_LETTER = "_";
    private static final String UNSOLVED_WORD = "Загаданное слово - %s \r\n";
    private static final String WRONG_LETTERS_MESSAGE = "Ошибки (%d):";
    private static final String GUESSING_WORD_MESSAGE = "Слово:";
    private static final String FILE_NOT_FOUND_MESSAGE = "Не обнаружен файл Dictionary.txt, необходимый для инициализации игрового словаря.";
    private static final String FILE_IS_EMPTY_MESSAGE = "Файл Dictionary.txt пуст. Пожалуйста, добавьте слова в файл.";
    private static final String LETTER_ALREADY_GUESSED = "Вы уже вводили букву %s, её в слове нет. Введите другую букву русского алфавита. \r\n";
    private static final String YES = "да";
    private static final String NO = "нет";
    private static final Pattern russianAlphabet = Pattern.compile("[а-яёА-ЯЁ]");
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final Map<Character, Integer> lettersOfGuessingWord = new HashMap<>();
    private static List<String> dictionary = new ArrayList<>();
    private static final Set<Character> solvedLetters = new HashSet<>();
    private static final List<Character> wrongGuessedLetters = new ArrayList<>();
    private static final int WRONG_ANSWERS_LIMIT = 6;
    private static int wrongAnswersCounter = 0;
    private static int solvedLettersCounter = 0;

    public static void main(String[] args) {
        while (true) {
            printGameMessage(START_GAME_AGAIN_MESSAGE);
            if (!hasToStartNewRound()) {
                break;
            }
            clearData();
            startGameRound();
        }
        SCANNER.close();
    }

    private static void startGameRound() {
        fillTheDictionary();
        String wordForGuessing = getRandomWord();
        initializeLettersOfGuessingWord(wordForGuessing);
        startGameLoop(wordForGuessing);
    }

    private static void startGameLoop(String wordForGuessing) {
        while (wrongAnswersCounter <= WRONG_ANSWERS_LIMIT) {
            printCurrentGameState(wordForGuessing);
            String resultOfRound = getResultOfRound(wordForGuessing.length());
            if (isGameFinished(resultOfRound)) {
                printGameMessage(resultOfRound);
                if (resultOfRound.equals(PLAYER_LOSE)){
                    System.out.printf(UNSOLVED_WORD, wordForGuessing);
                }
                break;
            }
            String PlayerGuess = SCANNER.nextLine();
            if (isInputCorrect(PlayerGuess)) {
                processEnteredLetter(PlayerGuess.toLowerCase().charAt(0));
            } else {
                printGameMessage(INVALID_LETTER_INPUT);
            }
        }
    }

    private static void processEnteredLetter(char guess) {
        if (lettersOfGuessingWord.containsKey(guess)) {
            solvedLetters.add(guess);
            solvedLettersCounter += matchesCount(guess);
            lettersOfGuessingWord.put(guess, 0);
        } else {
            if (!wrongGuessedLetters.contains(guess)) {
                wrongGuessedLetters.add(guess);
                wrongAnswersCounter++;
            } else {
                System.out.printf(LETTER_ALREADY_GUESSED, guess);
            }
        }
    }

    private static int matchesCount(Character guess) {
        Integer value = lettersOfGuessingWord.get(guess);
        return (value != null) ? value : 0;
    }

    private static String getResultOfRound(int wordLength) {
        if (wrongAnswersCounter == WRONG_ANSWERS_LIMIT) {
            return PLAYER_LOSE;
        } else if (solvedLettersCounter == wordLength) {
            return PLAYER_WIN;
        } else {
            return GAME_NOT_FINISHED;
        }
    }

    private static boolean hasToStartNewRound() {
        do {
            String decision = SCANNER.nextLine();
            if (decision.equalsIgnoreCase(YES)) {
                return true;
            } else if (decision.equalsIgnoreCase(NO)) {
                return false;
            } else {
                printGameMessage(INVALID_YES_OR_NO_INPUT);
            }
        }
        while (true);
    }

    private static void initializeLettersOfGuessingWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            Character currentChar = word.charAt(i);
            if (!lettersOfGuessingWord.containsKey(currentChar)) {
                lettersOfGuessingWord.put(currentChar, 1);
            } else {
                lettersOfGuessingWord.put(currentChar, lettersOfGuessingWord.get(currentChar) + 1);
            }
        }
    }

    private static void printSolvedLetters(String word) {
        StringBuilder resultMessage = new StringBuilder(GUESSING_WORD_MESSAGE);
        for (int i = 0; i < word.length(); i++) {
            if (solvedLetters.contains(word.charAt(i))) {
                resultMessage.append(word.charAt(i));
            } else {
                resultMessage.append(UNSOLVED_LETTER);
            }
        }
        printGameMessage(resultMessage.toString());
    }

    private static void printWrongLetters(int wrongAnswersCounter) {
        String resultMessage = String.format(WRONG_LETTERS_MESSAGE, wrongAnswersCounter);
        if (wrongAnswersCounter > 0) {
            resultMessage += " " + String.join(", ", wrongGuessedLetters.toString());
        }
        printGameMessage(resultMessage);
    }

    private static void printCurrentGameState(String word) {
        printGallowsState(wrongAnswersCounter);
        printSolvedLetters(word);
        printWrongLetters(wrongAnswersCounter);
    }

    private static void printGameMessage(String message) {
        System.out.println(message);
    }

    private static void printGallowsState(int wrongAnswersCounter) {
        GallowsDrawing.drawCurrentGallowsState(wrongAnswersCounter);
    }

    private static boolean isNullOrEmpty(String source) {
        return (source == null || source.trim().isEmpty());
    }

    private static boolean isInputCorrect(String source) {
        return !isNullOrEmpty(source) && isOneSymbolLength(source) && isRussianAlphabetLetter(source.charAt(0));
    }

    private static boolean isOneSymbolLength(String sourse) {
        return sourse.length() == 1;
    }

    private static boolean isRussianAlphabetLetter(char source) {
        return String.valueOf(source).matches(russianAlphabet.toString());
    }

    private static void fillTheDictionary() {
        try {
            dictionary = Files.readAllLines(Paths.get(DICTIONARY_FILE_PATH));
            if (dictionary.isEmpty()) {
                printGameMessage(FILE_IS_EMPTY_MESSAGE);
                System.exit(1);
            }
        } catch (IOException ex) {
            printGameMessage(FILE_NOT_FOUND_MESSAGE);
            System.exit(1);
        }
    }

    private static String getRandomWord() {
        return dictionary.get(RANDOM.nextInt(dictionary.size()));
    }

    private static boolean isGameFinished(String resultOfRound) {
        return resultOfRound.equals(PLAYER_WIN) || resultOfRound.equals(PLAYER_LOSE);
    }

    private static void clearData() {
        lettersOfGuessingWord.clear();
        solvedLetters.clear();
        wrongGuessedLetters.clear();
        solvedLettersCounter = 0;
        wrongAnswersCounter = 0;
    }
}