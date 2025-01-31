import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Random RANDOM = new Random();//нужен final? спросить ии
    //private static String[] dictionary = {"тетрадь", "яблоко", "бутылка"};
    private static String[] dictionary = {"ааб"};
    private static final Scanner SCANNER = new Scanner(System.in);//нужен final?
    private static int tryCounter = 0;
    private static int correctGuesses = 0;
    private static Map<Character, Integer> charMap = new HashMap<>();
    private static final String START_GAME_MESSAGE = "Желаете начать игру (да/нет)?";////////не забыть использовать
    private static final String INVALID_INPUT1 = "Неизвестная команда. Для старта игры введите да";////////Переименовать
    private static final String INVALID_INPUT2 = "Введён неизвестный символ. Необходимо ввести любую букву русского алфавита! ";////////Переименовать
    private static final String PLAYER_WIN_MESSAGE = "Игрок выиграл!";
    private static final String PLAYER_LOSE_MESSAGE = "Игрок проиграл!";
    private static final String START_GAME_AGAIN_MESSAGE = "Хотите сыграть ещё раз (да/нет)?";
    private static final String ASK_TO_ENTER_LETTER = "Введите букву";//менять формулировку или убрать вообще
    private static final String UNDERSCORE = "_";//переименовать?
    private static Set<Character> guessedLetters = new HashSet<>();
    private static List<Character> wrongLetters = new ArrayList<>(); //переименовать?
    private static Pattern russianAlphabet = Pattern.compile("[а-яёА-ЯЁ]");
    private static final int WRONG_ANSWERS_LIMIT = 6;
    private static int wrongAnswersCounter = 0;
    private static boolean continueGame = false;

    private static String wrongLettersMessage = "Ошибки";///пока не использую, надо доработать логику

    public static void main(String[] args) {
        do {
            startGame();
            printGameMessage(START_GAME_AGAIN_MESSAGE);
            String decision = getInput(SCANNER);
            if (decision.equalsIgnoreCase("да")){
                continueGame = true;
                clearData();
            } else if (decision.equalsIgnoreCase("нет")){
                continueGame = false;
            } else {
                //Сообщение, что введена непонятная команда
            }
        } while (continueGame);
    }

    private static void startGame(){
        String word = getRandomWord(dictionary);
        //1. Если введённый символ не прошёл валидацию - программа не должна проверять содержится ли она в мапе и остальную логику
        //Печатается виселица, загаданное слово, счётчик ошибок
        //Пользователь вводит символ или строку
        //Происходит поэтапная валидация
            //Если пустая строка - сообщение об ошибке, повторный ввод, счётчик неверных угадываний +1
            //кастим до lowercase и вытаскиваем первый чар строки
            //Если если это не символ русского алфавита - сообщение об ошибке, повторный ввод, счётчик неверных угадываний +1
        //Если это символ русского алфавита
            //содержит ли мапа чаров этот символ
            //если содержит - добавляем чар в guessedLetters
                //иначе добавляем чар в wrongLetters
        charMap = initializeCharMap(word);
        while (tryCounter <= WRONG_ANSWERS_LIMIT) {
            printGallowsState(wrongAnswersCounter);
            printSolvedLetters(word, guessedLetters);
            printWrongCharGuesses(wrongAnswersCounter);
            if (tryCounter == WRONG_ANSWERS_LIMIT){
                break;
            }
            tryCounter++;
            String guessFullString = castStringToLowerCase(getInput(SCANNER));
            char guess = getFirstLetterFromString(guessFullString);

            if (!isRussianAlphabetChar(guess)) {
                printGameMessage(INVALID_INPUT2);
            }
            if (charMap.containsKey(guess)) {//дублирование
                if (!guessedLetters.contains(guess)){
                    guessedLetters.add(guess);

                }
            } else {
                wrongLetters.add(guess);
                wrongAnswersCounter++;
            }
            correctGuesses += matchesCount(guess);
            charMap.put(guess,0);
            if (correctGuesses == word.length()) {
                printGameMessage(PLAYER_WIN_MESSAGE);
                return;
            }
        }
        printGameMessage(PLAYER_LOSE_MESSAGE);
    }

    private static int matchesCount(Character guess) {
        Integer value = charMap.get(guess);
        return (value != null) ? value : 0;
    }

    private static String getRandomWord(String[] source) {
        return dictionary[RANDOM.nextInt(dictionary.length)];
    }

    private static String getInput(Scanner scanner) {
        return scanner.nextLine();
    }
    private static Map<Character, Integer> initializeCharMap(String word) {
        Map<Character, Integer> temp = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            Character currentChar = word.charAt(i);
            if (!temp.containsKey(currentChar)) {
                temp.put(currentChar, 1);
            } else {
                temp.put(currentChar, temp.get(currentChar) + 1);
            }
        }
        return temp;
    }
    private static void printGameMessage(String message) {
        System.out.println(message);
    }
    private static void printSolvedLetters(String word, Set<Character> guessedLetters) {
        System.out.print("Слово:");
        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));//дублирование
            } else {
                System.out.print(UNDERSCORE);
            }
        }
        System.out.println();
    }

    private static String castStringToLowerCase(String source) {
        return source.toLowerCase();
    }

    private static boolean isRussianAlphabetChar(char source) {//переписать ИИ?
        Matcher matcher = russianAlphabet.matcher("" + source);
        return (matcher.find());
    }
    private static Character getFirstLetterFromString(String source) {
        return source.charAt(0);
    }

    private static void printGallowsState(int wrongAnswersCounter){
        System.out.println(GallowsDrawing.drawCurrentGallowsState(wrongAnswersCounter));
    }
    private static void clearData(){//test
        charMap.clear();
        guessedLetters.clear();
        wrongLetters.clear();
        wrongAnswersCounter = 0;
    }

    private static boolean checkStringIsNullOrEmpty(String source){//test
        return (source == null || source.trim().isEmpty());
    }

    private static boolean isCharValid(char source){//test
        return false;
    }

    private static boolean isEnteredStringValid(String source){//test
        return false;
    }

    private static void printWrongCharGuesses(int wrongAnswersCounter){
        System.out.println("Ошибки (" + wrongAnswersCounter + "): ");
    }
}