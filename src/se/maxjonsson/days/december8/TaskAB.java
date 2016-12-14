package se.maxjonsson.days.december8;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.Regex;

import java.util.List;
import java.util.regex.Matcher;

public class TaskAB implements Task {
    private boolean[][] screen;
    private final static int SCREEN_HEIGHT = 6;
    private final static int SCREEN_WIDTH = 50;

    private final String createRectangleRegex = "^rect (?<x>[\\d]{1,5})x(?<y>[\\d]{1,5})$";
    private final String shiftRegex = "^rotate (?<columnOrRow>(?:column|row)) (?:x|y)=(?<index>[\\d]{1,2}) by (?<shift>\\d{1,3})$";

    public TaskAB() {
        screen = new boolean[SCREEN_HEIGHT][SCREEN_WIDTH];

        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            for (int j = 0; j < SCREEN_WIDTH; j++) {
                screen[i][j] = false;
            }
        }
    }

    @Override
    public void run() {
        final List<String> instructions = FileReader.readLines("input/december8/input.txt");
        final int numberOfLitPixels = getNumberOfLitPixelsAfter(instructions);

        System.out.println("Number of lit pixels: " + numberOfLitPixels + ", and the code is shown below:");
        drawDisplay();
    }

    private int getNumberOfLitPixelsAfter(List<String> instructions) {
        for (String instruction : instructions) {
            if (instruction.startsWith("rect")) {
                lightUpRectangle(instruction);
            }
            else {
                shiftPixels(instruction);
            }
        }

        return calculateLitPixels();
    }

    private void lightUpRectangle(String instruction) {
        Matcher rectangleMatcher = Regex.parse(createRectangleRegex, instruction);

        int lengthOfRectangle = Integer.parseInt(rectangleMatcher.group("x"));
        int heightOfRectangle = Integer.parseInt(rectangleMatcher.group("y"));

        for (int i = 0; i < heightOfRectangle; i++) {
            for (int j = 0; j < lengthOfRectangle; j++) {
                screen[i][j] = true;
            }
        }
    }

    private void shiftPixels(String instruction) {
        Matcher shiftmatcher = Regex.parse(shiftRegex, instruction);

        final boolean isRow = shiftmatcher.group("columnOrRow").equals("row");
        final int index = Integer.parseInt(shiftmatcher.group("index"));
        final int shiftValue = Integer.parseInt(shiftmatcher.group("shift"));

        if (isRow) {
            shiftRow(index, shiftValue);
        }
        else {
            shiftColumn(index, shiftValue);
        }
    }

    private void shiftRow(int index, int shiftValue) {
        boolean[] rowToBeShifted = new boolean[SCREEN_WIDTH];

        for (int i = 0; i < SCREEN_WIDTH; i++) {
            rowToBeShifted[i] = screen[index][i];
        }

        for (int i = 0; i < SCREEN_WIDTH; i++) {
            screen[index][(i + shiftValue) % SCREEN_WIDTH] = rowToBeShifted[i];
        }
    }

    private void shiftColumn(int index, int shiftValue) {
        boolean[] columnToBeShifted = new boolean[SCREEN_HEIGHT];

        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            columnToBeShifted[i] = screen[i][index];
        }

        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            screen[(i + shiftValue) % SCREEN_HEIGHT][index] = columnToBeShifted[i];
        }
    }

    private int calculateLitPixels() {
        int litPixels = 0;

        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            for (int j = 0; j < SCREEN_WIDTH; j++) {
                if (screen[i][j]) {
                    litPixels++;
                }
            }
        }

        return litPixels;
    }

    private void drawDisplay() {
        for (int i = 0; i < SCREEN_HEIGHT; i++) {
            String row = "";
            for (int j = 0; j < SCREEN_WIDTH; j++) {
                row += screen[i][j] ? "#" : ".";
            }
            System.out.println(row);
        }
    }

    @Override
    public String getTaskName() {
        return "December 7, task 1 & 2";
    }
}
