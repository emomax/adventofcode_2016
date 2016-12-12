package se.maxjonsson.days.december3;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskB implements Task {
    private final TaskA helper = new TaskA();

    @Override
    public void run() {
        List<String> triangleLines = FileReader.readLines("input/december3/input.txt");
        List<String> columnMappedTriangles = readTrianglesAsColumns(triangleLines);

        List<String> validtriangles = columnMappedTriangles.stream()
                .filter(helper::isPossible)
                .collect(Collectors.toList());

        System.out.println("Valid triangles: " + validtriangles.size() + " (out of " + columnMappedTriangles.size() + ")");
    }

    private List<String> readTrianglesAsColumns(List<String> triangleLines) {
        List<String> remappedTriangles = new ArrayList<>();

        List<String> triangle1;
        List<String> triangle2;
        List<String> triangle3;

        for (int i = 0; i < triangleLines.size(); i += 3) {
            triangle1 = Arrays.asList(triangleLines.get(i).trim().split(" +"));
            triangle2 = Arrays.asList(triangleLines.get(i + 1).trim().split(" +"));
            triangle3 = Arrays.asList(triangleLines.get(i + 2).trim().split(" +"));

            remappedTriangles.add(triangle1.get(0) + " " + triangle2.get(0) + " " + triangle3.get(0));
            remappedTriangles.add(triangle1.get(1) + " " + triangle2.get(1) + " " + triangle3.get(1));
            remappedTriangles.add(triangle1.get(2) + " " + triangle2.get(2) + " " + triangle3.get(2));
        }

        return remappedTriangles;
    }

    @Override
    public String getTaskName() {
        return "December 3, task 2";
    }
}
