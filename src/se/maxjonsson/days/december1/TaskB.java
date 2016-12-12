package se.maxjonsson.days.december1;

import se.maxjonsson.Task;
import se.maxjonsson.utils.Coordinate;
import se.maxjonsson.utils.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskB implements Task {
    final String instructionPattern = "^(?<direction>[RL])(?<steps>\\d+)$";

    private final TaskA helper = new TaskA();

    Coordinate position = new Coordinate(0, 0);
    Coordinate direction = new Coordinate(0, 1);

    Map<String, Integer> visitedCoordinates = new HashMap<>();

    public void run() {
        List<String> instructions = helper.getInstructionsAsList(FileReader.readSingleLine("input/december1/input.txt") );
        System.out.println("HQ is " + processDirections(instructions) + " steps away!");
    }

    private int processDirections(List<String> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            boolean arrivedToDestination = walk(instructions.get(i));

            if (arrivedToDestination) {
                break;
            }
        }

        return Math.abs(position.x) + Math.abs(position.y);
    }

    private boolean walk(String instruction) {
        final Matcher instructionMatcher = Pattern.compile(instructionPattern).matcher(instruction);

        if (!instructionMatcher.find()) {
            throw new RuntimeException("Failed to parse instruction!");
        }

        final String whereToTurn = instructionMatcher.group("direction");
        final int numberOfSteps = Integer.parseInt(instructionMatcher.group("steps"));

        if (helper.isFacingUpOrDown(direction)) {
            direction.x = ("R".equals(whereToTurn)) ? direction.y : -direction.y;
            direction.y = 0;
        }
        else { // We are facing right or left
            direction.y = ("R".equals(whereToTurn)) ? -direction.x : direction.x;
            direction.x = 0;
        }

        for (int i = 0; i < numberOfSteps; i++) {
            position.x += direction.x;
            position.y += direction.y;

            final String positionKey = position.x + ":" + position.y;
            if (visitedCoordinates.containsKey(positionKey)) {
                return true;
            }
            visitedCoordinates.put(positionKey, 1);
        }

        return false;
    }

    @Override
    public String getTaskName() {
        return "December 1st, task 2";
    }
}
