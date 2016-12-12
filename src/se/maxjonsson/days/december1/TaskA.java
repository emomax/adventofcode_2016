package se.maxjonsson.days.december1;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.maxjonsson.Task;
import se.maxjonsson.utils.Coordinate;
import se.maxjonsson.utils.FileReader;

public class TaskA implements Task {
    final String instructionPattern = "^(?<direction>[RL])(?<steps>\\d+)$";

    Coordinate direction = new Coordinate(0, 1);
    Coordinate position = new Coordinate(0, 0);

    @Override
    public void run() {
        String directions = FileReader.readSingleLine("input/december1/input.txt");
        System.out.println("HQ is " + processDirections(directions) + " steps away!");
    }

    private int processDirections(String directionsRaw) {
        final List<String> instructions = getInstructionsAsList(directionsRaw);
        instructions.forEach(this::walk);

        return Math.abs(position.x) + Math.abs(position.y);
    }

    List<String> getInstructionsAsList(String directionsRaw) {
        return Arrays.asList(directionsRaw.split(", "));
    }

    private void walk(String instruction) {
        final Matcher instructionMatcher = Pattern.compile(instructionPattern).matcher(instruction);

        if (!instructionMatcher.find()) {
            throw new RuntimeException("Failed to parse instruction!");
        }

        final String whereToTurn = instructionMatcher.group("direction");
        final int numberOfSteps = Integer.parseInt(instructionMatcher.group("steps"));

        if (isFacingUpOrDown(direction)) {
            direction.x = ("R".equals(whereToTurn)) ? direction.y : -direction.y;
            direction.y = 0;
        }
        else { // We are facing right or left
            direction.y = ("R".equals(whereToTurn)) ? -direction.x : direction.x;
            direction.x = 0;
        }

        position.x += numberOfSteps * direction.x;
        position.y += numberOfSteps * direction.y;
    }

    boolean isFacingUpOrDown(Coordinate direction) {
        return direction.x == 0;
    }

    @Override
    public String getTaskName() {
        return "December 1st, task 1";
    }
}
