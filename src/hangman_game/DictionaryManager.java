package hangman_game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DictionaryManager {
    private static final String DICTIONARY_FILE_PATH = "Dictionary.txt";
    private static final String FILE_NOT_FOUND_MESSAGE = "Не обнаружен файл Dictionary.txt, необходимый для инициализации игрового словаря.";
    private static final String FILE_IS_EMPTY_MESSAGE = "Файл Dictionary.txt пуст. Пожалуйста, добавьте слова в файл.";
    private static final Random RANDOM = new Random();
    private static List<String> dictionary = Collections.emptyList();

    static void fillTheDictionary() {
        try {
            dictionary = Files.readAllLines(Paths.get(DICTIONARY_FILE_PATH));
            if (dictionary.isEmpty()) {
                System.out.println(FILE_IS_EMPTY_MESSAGE);
                System.exit(1);
            }
        } catch (IOException ex) {
            System.out.println(FILE_NOT_FOUND_MESSAGE);
            System.exit(1);
        }
    }

    static String getRandomWord() {
        return dictionary.get(RANDOM.nextInt(dictionary.size()));
    }
}
