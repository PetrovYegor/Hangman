package hangman_game;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import static hangman_game.GameMessages.*;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int WRONG_ANSWERS_LIMIT = 6;

    public static void main(String[] args) {
        while (true) {
            System.out.println(START_GAME_AGAIN_MESSAGE);
            if (!hasToStartNewRound()) {
                break;
            }
            startGameRound();
        }
        SCANNER.close();
    }

    private static void startGameRound() {
        DictionaryManager.fillTheDictionary();
        String secretWord = DictionaryManager.getRandomWord();
        startGameLoop(secretWord);
    }

    private static void startGameLoop(String secretWord) {
        String maskedSecretWord = "_".repeat(secretWord.length());
        int wrongAnswersCounter = 0;
        final Set<Character> wrongGuessedLetters = new LinkedHashSet<>();

        while (true) {
            printCurrentGameState(maskedSecretWord, wrongGuessedLetters, wrongAnswersCounter);
            System.out.print(ENTER_THE_LETTER_MESSAGE);
            String playerGuess = SCANNER.nextLine();
            if (!isInputCorrect(playerGuess)) {
                System.out.println(INVALID_LETTER_INPUT);
                continue;
            }
            char letter = getLowerCaseFirstLetter(playerGuess);
            if (wasLetterEnteredBefore(maskedSecretWord, wrongGuessedLetters, letter)) {
                System.out.printf(LETTER_ALREADY_GUESSED, letter);
                continue;
            }
            if (isSecretWordContainsLetter(secretWord, letter)) {
                maskedSecretWord = openLetter(secretWord, maskedSecretWord, letter);
            } else {
                wrongAnswersCounter++;
                wrongGuessedLetters.add(letter);
            }
            if (isGameOver(secretWord, maskedSecretWord, wrongAnswersCounter)) {
                if (isWin(secretWord, maskedSecretWord)) {
                    System.out.printf(RESULT_OF_ROUND_MESSAGE, PLAYER_WIN, secretWord);
                } else {
                    GallowsDrawing.printPicture(wrongAnswersCounter);
                    System.out.printf(RESULT_OF_ROUND_MESSAGE, PLAYER_LOSE, secretWord);
                }
                break;
            }
        }
    }

    private static boolean isGameOver(String secretWord, String maskedSecretWord, int wrongAnswersCounter) {
        return isWin(secretWord, maskedSecretWord) || isLose(wrongAnswersCounter);
    }

    private static boolean isWin(String secretWord, String maskedSecretWord) {
        return secretWord.equals(maskedSecretWord);
    }

    private static boolean isLose(int wrongAnswersCounter) {
        return wrongAnswersCounter == WRONG_ANSWERS_LIMIT;
    }

    private static boolean hasToStartNewRound() {
        while (true) {
            String decision = SCANNER.nextLine();
            if (enteredWordIsYesOrNo(decision)) {
                return isInputYes(decision);
            }
            System.out.println(INVALID_YES_OR_NO_INPUT);
        }
    }

    private static boolean isSecretWordContainsLetter(String secretWord, char letter) {
        return secretWord.indexOf(letter) != -1;
    }

    private static boolean enteredWordIsYesOrNo(String input) {
        return isInputYes(input) || isInputNo(input);
    }

    private static boolean isInputYes(String input) {
        return input.equalsIgnoreCase(YES);
    }

    private static boolean isInputNo(String input) {
        return input.equalsIgnoreCase(NO);
    }

    private static void printMaskedSecretWord(String maskedSecretWord) {
        System.out.printf(MASKED_SECRET_WORD_MESSAGE, maskedSecretWord);
    }

    private static void printWrongLetters(Set<Character> wrongGuessedLetters, int wrongAnswersCounter) {
        String resultMessage = String.format(WRONG_LETTERS_MESSAGE, wrongAnswersCounter);
        if (wrongAnswersCounter > 0) {
            resultMessage += " " + String.join(", ", wrongGuessedLetters.toString());
        }
        System.out.println(resultMessage);
    }

    private static void printCurrentGameState(String maskedSecretWord, Set<Character> wrongGuessedLetters, int wrongAnswersCounter) {
        GallowsDrawing.printPicture(wrongAnswersCounter);
        printMaskedSecretWord(maskedSecretWord);
        printWrongLetters(wrongGuessedLetters, wrongAnswersCounter);
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

    private static boolean isRussianAlphabetLetter(char c) {
        c = Character.toLowerCase(c);
        return c == 'ё' || c >= 'а' && c <= 'я';
    }

    private static String openLetter(String secretWord, String maskedSecretWord, char letter) {
        char[] maskedLetters = maskedSecretWord.toCharArray();
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                maskedLetters[i] = letter;
            }
        }
        return new String(maskedLetters);
    }

    private static boolean wasLetterEnteredBefore(String maskedSecretWord, Set<Character> wrongGuessedLetters, char c) {
        return maskedSecretWord.contains(String.valueOf(c)) || wrongGuessedLetters.contains(c);
    }

    private static char getLowerCaseFirstLetter(String source) {
        return source.toLowerCase().charAt(0);
    }
}