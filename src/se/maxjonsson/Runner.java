package se.maxjonsson;

import se.maxjonsson.days.december1.TaskA;
import se.maxjonsson.days.december1.TaskB;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private static List<Task> tasks;

    static {
        tasks = new ArrayList<>();
        tasks.add(new TaskA());
        tasks.add(new TaskB());

        tasks.add(new se.maxjonsson.days.december2.TaskA());
        tasks.add(new se.maxjonsson.days.december2.TaskB());

        tasks.add(new se.maxjonsson.days.december3.TaskA());
        tasks.add(new se.maxjonsson.days.december3.TaskB());

        tasks.add(new se.maxjonsson.days.december4.TaskA());
        tasks.add(new se.maxjonsson.days.december4.TaskB());

        tasks.add(new se.maxjonsson.days.december5.TaskA());
        tasks.add(new se.maxjonsson.days.december5.TaskB());
    }


    public static void runTasks() {
        tasks.stream().forEach(Runner::runTask);
    }

    private static void runTask(final Task task) {
        System.out.println(task.getTaskName() + "");
        task.run();
        System.out.println();
    }

    private Runner() {
    }
}
