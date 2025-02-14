package hangman_game;

public class GallowsDrawing {
    private static final String ILLEGAL_ARGUMENT_MESSAGE = "Невозможно отобразить виселицу, передан некорректный индекс массива: ";
    private static final String[] PICTURES = {
            """
                   _____________
                   | \\_______  |
                   |        \\| |
                             | |
                             | |
                             | |
                             | |
                             | |
                _____________|_|
                |///////////////|
                |///////////////|
            """,
            """
                   _____________
                   | \\_______  |
                   |        \\| |
                   O         | |
                             | |
                             | |
                             | |
                             | |
                _____________|_|
                |///////////////|
                |///////////////|
            """,
            """
                   _____________
                   | \\_______  |
                   |        \\| |
                   O         | |
                   8         | |
                             | |
                             | |
                             | |
                _____________|_|
                |///////////////|
                |///////////////|
            """,
            """
                   _____________
                   | \\_______  |
                   |        \\| |
                   O         | |
                  /8         | |
                             | |
                             | |
                             | |
                _____________|_|
                |///////////////|
                |///////////////|
            """,
            """
                   _____________
                   | \\_______  |
                   |        \\| |
                   O         | |
                  /8\\        | |
                             | |
                             | |
                             | |
                _____________|_|
                |///////////////|
                |///////////////|
            """,
            """
                   _____________
                   | \\_______  |
                   |        \\| |
                   O         | |
                  /8\\        | |
                  /          | |
                             | |
                             | |
                _____________|_|
                |///////////////|
                |///////////////|
            """,
            """
                   _____________
                   | \\_______  |
                   |        \\| |
                   O         | |
                  /8\\        | |
                  / \\        | |
                             | |
                             | |
                _____________|_|
                |///////////////|
                |///////////////|
            """
    };

    public static void printPicture(int numPicture) {
        if (!isNumPictureValid(numPicture)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE + numPicture);
        }
        System.out.println(PICTURES[numPicture]);
    }

    private static boolean isNumPictureValid(int numPicture) {
        return numPicture >= 0 && numPicture < PICTURES.length;
    }
}