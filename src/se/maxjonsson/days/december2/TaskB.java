package se.maxjonsson.days.december2;

import se.maxjonsson.Task;
import se.maxjonsson.utils.Coordinate;
import se.maxjonsson.utils.FileReader;

import java.util.List;
import java.util.stream.Collectors;

public class TaskB implements Task {
    final String[][] keyPad = new String[5][5];
    Coordinate position;

    private final TaskA helper;

    public TaskB() {
        keyPad[2][0] = "1";
        keyPad[1][1] = "2";
        keyPad[2][1] = "3";
        keyPad[3][1] = "4";
        keyPad[0][2] = "5";
        keyPad[1][2] = "6";
        keyPad[2][2] = "7";
        keyPad[3][2] = "8";
        keyPad[4][2] = "9";
        keyPad[1][3] = "A";
        keyPad[2][3] = "B";
        keyPad[3][3] = "C";
        keyPad[2][4] = "D";

        helper = new TaskA(keyPad);
    }

    @Override
    public void run() {
        List<String> instructions = FileReader.readLines("input/december2/input.txt");

        List<String> code = instructions.stream()
                .map(helper::fromInstructionsToDigit)
                .collect(Collectors.toList());

        String formattedCode = String.join("", code);
        System.out.println("The bathroom code is: " + formattedCode);
    }

    @Override
    public String getTaskName() {
        return "December 2nd, task 2";
    }
}
