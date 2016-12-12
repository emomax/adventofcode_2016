package se.maxjonsson.days.december4;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.Regex;

import java.util.List;
import java.util.regex.Matcher;

public class TaskB implements Task {
    private final static String ROOM_REGEX = "^(?<encryptedRoomName>[a-z\\-\\s]*)(?<roomID>\\d{1,4})\\[(?<checksum>[a-z]{5})\\]$";

    private final TaskA helper = new TaskA();

    @Override
    public void run() {
        List<String> encryptedRoomNames = FileReader.readLines("input/december4/input.txt");

        final String sectorID = encryptedRoomNames.stream()
                .map(rawRoomName -> rawRoomName.trim())
                .filter(helper::isRealRoom)
                .map(this::decryptRoom)
                .filter(decryptedName -> decryptedName.contains("north"))
                .map(rawDecryptedName -> Regex.parse(ROOM_REGEX, rawDecryptedName).group("roomID"))
                .findAny()
                .get();

        System.out.println("Sector ID of room where North pole objects are located: " + sectorID);
    }

    private String decryptRoom(String encryptedRoom) {
        Matcher roomMatcher = Regex.parse(ROOM_REGEX, encryptedRoom);

        int rotations = Integer.parseInt(roomMatcher.group("roomID"));
        String encryptedRoomName = roomMatcher.group("encryptedRoomName");
        String decryptedRoomName = rotN(encryptedRoomName, rotations);

        return decryptedRoomName + roomMatcher.group("roomID") + "[" + roomMatcher.group("checksum") + "]";
    }

    private String rotN(String encryptedRoom, int rotations) {
        char[] characters = encryptedRoom.toCharArray();

        String resultString = "";
        for (int i = 0; i < characters.length; i++) {
            char currentChar = characters[i];

            if (currentChar == '-') {
                currentChar = ' ';
                resultString += currentChar;
                continue;
            }

            for (int j = 0; j < rotations; j++) {
                if (currentChar == 'z')
                    currentChar = 'a';
                else {
                    currentChar += 1;
                }
            }

            resultString += currentChar;
        }

        return resultString;
    }

    @Override
    public String getTaskName() {
        return "December 4, task 2";
    }
}
