package se.maxjonsson.days.december6;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.MutableInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskB implements Task {
    final static int PASSWORD_LENGTH = 8;
    Map<String, MutableInt>[] characterMaps;

    public TaskB() {
        characterMaps = new Map[PASSWORD_LENGTH];

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            characterMaps[i] = new HashMap<>();
        }
    }

    @Override
    public void run() {
        final List<String> inputLines = FileReader.readLines("input/december6/input.txt");
        final String deductedPassword = calculatePassword(inputLines, PASSWORD_LENGTH);

        System.out.println("Given the signals, the password is: " + deductedPassword);
    }

    private String calculatePassword(List<String> transmittedData, int passwordLength) {
        transmittedData.stream().forEach(this::populateMaps);
        String password = "";

        for (Map<String, MutableInt> map : characterMaps) {
            password += map.entrySet().stream()
                    .sorted((entry1, entry2) -> Integer.compare(entry1.getValue().get(), entry2.getValue().get()))
                    .findFirst()
                    .get()
                    .getKey();
        }

        return password;
    }

    private void populateMaps(String input) {
        char[] letters = input.toCharArray();
        char currentLetter;

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            currentLetter = letters[i];
            MutableInt count = characterMaps[i].get(String.valueOf(letters[i]));

            if (count == null) {
                characterMaps[i].put(String.valueOf(currentLetter), new MutableInt());
            }
            else {
                count.increment();
            }
        }
    }

    @Override
    public String getTaskName() {
        return "December 6, task 2";
    }
}
