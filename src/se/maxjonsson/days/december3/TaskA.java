package se.maxjonsson.days.december3;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskA implements Task {
    @Override
    public void run() {
        List<String> possibleTriangles = FileReader.readLines("input/december3/input.txt");

        List<String> validTriangles = possibleTriangles.stream()
                .filter(this::isPossible)
                .collect(Collectors.toList());

        System.out.println("Valid triangles: " + validTriangles.size() + " (out of " + possibleTriangles.size() + ")");
    }

    boolean isPossible(String triangleRow) {
        List<String> triangles = Arrays.asList(triangleRow.trim().split(" +"));

        if (triangles.size() != 3) {
            throw new RuntimeException("Malformatted triangle line: " + triangles.toString());
        }

        return validTriangle(triangles);
    }

    private boolean validTriangle(List<String> triangles) {
        int sideA = Integer.parseInt(triangles.get(0));
        int sideB = Integer.parseInt(triangles.get(1));
        int sideC = Integer.parseInt(triangles.get(2));

        return ((sideA + sideB > sideC) &&
                (sideA + sideC > sideB) &&
                (sideB + sideC > sideA));
    }

    @Override
    public String getTaskName() {
        return "December 3, task 1";
    }
}
