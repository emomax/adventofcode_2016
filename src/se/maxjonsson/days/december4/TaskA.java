package se.maxjonsson.days.december4;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.MutableInt;
import se.maxjonsson.utils.Regex;

import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class TaskA implements Task {
    private final static String ROOM_REGEX = "^(?<encryptedRoomName>[a-z\\-]*)(?<roomID>\\d{1,4})\\[(?<checksum>[a-z]{5})\\]$";

    @Override
    public void run() {
        List<String> encryptedRoomNames = FileReader.readLines("input/december4/input.txt");

        int sumOfSectorIDs = encryptedRoomNames.stream()
                .map(rawRoomName -> rawRoomName.trim())
                .filter(this::isRealRoom)
                .map(encryptedRoomName -> Regex.parse(ROOM_REGEX, encryptedRoomName).group("roomID"))
                .map(id -> Integer.parseInt(id))
                .reduce(0, (a, b) -> a + b);

        System.out.println("Sum of the IDs of the real rooms: " + sumOfSectorIDs);
    }

    boolean isRealRoom(String encryptedRoomName) {
        final Matcher roomMatcher = Regex.parse(ROOM_REGEX, encryptedRoomName);

        final String rawEncryptedName = roomMatcher.group("encryptedRoomName");
        final String checksum = roomMatcher.group("checksum");

        final String encryptedName = rawEncryptedName.replaceAll("-", "");
        final String calculatedCheckSum = calculateCheckSum(encryptedName);

        return calculatedCheckSum.equals(checksum);
    }

    private String calculateCheckSum(String encryptedRoomName) {
        final List<String> characters = Arrays.asList(encryptedRoomName.split(""));

        final Map<String, MutableInt> characterMap = getCharacterFrequencyMap(characters);
        final Map<Integer, String> fullStringsAsOccurencesMap = invertAndReduce(characterMap);

        List<String> mostFrequentCharacters = fullStringsAsOccurencesMap.entrySet().stream()
                .sorted((pair1, pair2) -> Integer.compare(pair2.getKey(), pair1.getKey()))
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());

        String output = "";

        for (String token : mostFrequentCharacters) {
            final String sorted = getSortedString(token);
            output += sorted;
        }

        return output.substring(0,5);
    }

    private Map<String, MutableInt> getCharacterFrequencyMap(List<String> characters) {
        Map<String, MutableInt> characterMap = new HashMap<>();
        for (String character : characters) {
            MutableInt count = characterMap.get(character);

            if (count == null) {
                characterMap.put(character, new MutableInt());
            }
            else {
                count.increment();
            }
        }
        return characterMap;
    }

    /**
     * Set values to keys, and map all values to the same entry
     * @param characterMap
     * @return
     */
    private Map<Integer, String> invertAndReduce(Map<String, MutableInt> characterMap) {
        Map<Integer, String> fullStringsAsOccurencesMap = new HashMap<>();

        for (Map.Entry<String, MutableInt> entry : characterMap.entrySet()) {
            int key = entry.getValue().get();
            String value = entry.getKey();

            if (fullStringsAsOccurencesMap.containsKey(key)) {
                final String currentString = fullStringsAsOccurencesMap.get(key);
                fullStringsAsOccurencesMap.put(key, currentString + value);
            }
            else {
                fullStringsAsOccurencesMap.put(key, value);
            }
        }

        return fullStringsAsOccurencesMap;
    }

    /**
     * Sort a string alphabetically
     * @param token
     * @return
     */
    private String getSortedString(String token) {
        char[] chars = token.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    @Override
    public String getTaskName() {
        return "December 4, task 1";
    }
}
