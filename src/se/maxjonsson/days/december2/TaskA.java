package se.maxjonsson.days.december2;

import se.maxjonsson.Task;
import se.maxjonsson.utils.Coordinate;
import se.maxjonsson.utils.FileReader;

import java.util.List;
import java.util.stream.Collectors;

public class TaskA implements Task {
    final String[][] keyPad;
    Coordinate position;

    public TaskA() {
        keyPad = new String[3][3];
        keyPad[0][0] = "1";
        keyPad[1][0] = "2";
        keyPad[2][0] = "3";
        keyPad[0][1] = "4";
        keyPad[1][1] = "5";
        keyPad[2][1] = "6";
        keyPad[0][2] = "7";
        keyPad[1][2] = "8";
        keyPad[2][2] = "9";
    }

    public TaskA(String[][] keyPad) {
        this.keyPad = keyPad;
    }

    @Override
    public void run() {
        List<String> instructions = FileReader.readLines("input/december2/input.txt");

        List<String> code = instructions.stream()
                .map(instruction -> fromInstructionsToDigit(instruction))
                .collect(Collectors.toList());

        String formattedCode = String.join("", code);
        System.out.println("The bathroom code is: " + formattedCode);
    }

    String fromInstructionsToDigit(String instruction) {
        position = new Coordinate(1, 1); // Center of keypad

        for (int i = 0; i < instruction.length(); i++) {
            position = moveToPosition(instruction.charAt(i));
        }

        return keyPad[position.x][position.y];
    }

    private Coordinate moveToPosition(char move) {
        Coordinate previousPosition = new Coordinate(position.x, position.y);

        if ('U' == move) {
            position.y--;
        } else if ('D' == move) {
            position.y++;
        } else if ('L' == move) {
            position.x--;
        } else if ('R' == move) {
            position.x++;
        }

        clampPosition(keyPad.length - 1);

        if (keyPad[position.x][position.y] == null) {
            position = previousPosition;
        }

        return position;
    }

    private void clampPosition(int bounds) {
        if (position.x < 0 || position.x > bounds) {
            position.x = (position.x > 1) ? bounds : 0;
        }
        if (position.y < 0 || position.y > bounds) {
            position.y = (position.y > 1) ? bounds : 0;
        }
    }

    @Override
    public String getTaskName() {
        return "December 2nd, task 1";
    }
}
