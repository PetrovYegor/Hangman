import java.util.HashMap;
import java.util.Map;

public class GallowsDrawing {
    private static final Map<Integer, GallowsStates> numberedGallowStates;
    private static final String ILLEGAL_ARGUMENT_MESSAGE = "Невозможно отобразить виселицу, передан некорректный аргумент: ";

    static {
        numberedGallowStates = new HashMap<>();
        numberedGallowStates.put(0, GallowsStates.DEFAULT);
        numberedGallowStates.put(1, GallowsStates.HEAD);
        numberedGallowStates.put(2, GallowsStates.BODY);
        numberedGallowStates.put(3, GallowsStates.LEFT_HAND);
        numberedGallowStates.put(4, GallowsStates.RIGHT_HAND);
        numberedGallowStates.put(5, GallowsStates.LEFT_LEG);
        numberedGallowStates.put(6, GallowsStates.RIGHT_LEG);
    }

    public static void drawCurrentGallowsState(int stateNumber) {
        GallowsStates state = numberedGallowStates.get(stateNumber);
        if (state == null) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE + stateNumber);
        }
        System.out.println(state.getState());
    }

    private enum GallowsStates {
        DEFAULT("""
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
                """), HEAD("""
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
                """), BODY("""
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
                """), LEFT_HAND("""
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
                """), RIGHT_HAND("""
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
                """), LEFT_LEG("""
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
                """), RIGHT_LEG("""
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
                """);
        private final String state;

        GallowsStates(String state) {
            this.state = state;
        }

        private String getState() {
            return state;
        }
    }
}