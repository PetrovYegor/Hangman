import java.util.HashMap;
import java.util.Map;

public class GallowsDrawing {
    private static final Map<Integer, GallowsStates> numberedStates;
    static {
        numberedStates = new HashMap<>();
        numberedStates.put(0,GallowsStates.DEFAULT);
        numberedStates.put(1,GallowsStates.HEAD);
        numberedStates.put(2,GallowsStates.BODY);
        numberedStates.put(3,GallowsStates.LEFT_HAND);
        numberedStates.put(4,GallowsStates.RIGHT_HAND);
        numberedStates.put(5,GallowsStates.LEFT_LEG);
        numberedStates.put(6,GallowsStates.RIGHT_LEG);
    }

    public static String drawCurrentGallowsState(int wrongAnswersCount){//test
        return numberedStates.get(wrongAnswersCount).getState();
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
                """)
        , HEAD("""
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
                """)
        , BODY("""
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
                """)
        , LEFT_HAND("""
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
                """)
        , RIGHT_HAND("""
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
                """)
        , LEFT_LEG("""
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
                """)
        , RIGHT_LEG("""
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
                """)
        ;
        private final String state;

        GallowsStates(String state){
            this.state = state;
        }

        public String getState(){
            return state;
        }
    }
}