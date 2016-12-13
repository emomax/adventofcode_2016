package se.maxjonsson.days.december5;

import javafx.util.Pair;
import se.maxjonsson.Task;

public class TaskB implements Task {
    private final TaskA helper = new TaskA();
    private static final int UPPER_BOUND = 8;

    @Override
    public void run() {
        final String roomName = "ojvtpuvg";

        System.out.println("Decrypting .. Please hold on.");
        final String password = findPasswordForRoom(roomName);

        System.out.println(".. Done.\nDecrypted second password for door '" + roomName + "': " + password);
    }

    private String findPasswordForRoom(String roomName) {
        char[] password = new char[8];
        long nextIndex = 0L;
        int position;

        // Reset char array to outside of boundaries
        for (int i = 0; i < password.length; i++) {
            password[i] = UPPER_BOUND;
        }

        while (passwordNotFull(password)) {
            Pair<String, Long> nextHash = helper.findNextHash(roomName, nextIndex);

            position = Character.getNumericValue(nextHash.getKey().charAt(5));
            nextIndex = nextHash.getValue();

            if (position >= 8 || password[position] != UPPER_BOUND) {
                continue;
            }

            System.out.println("Found hash '" + nextHash.getKey() + "'");
            password[position] = nextHash.getKey().charAt(6);
        }

        return String.copyValueOf(password);
    }

    private boolean passwordNotFull(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == UPPER_BOUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getTaskName() {
        return "December 5, task 1";
    }
}
