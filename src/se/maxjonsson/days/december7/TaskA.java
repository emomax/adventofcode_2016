package se.maxjonsson.days.december7;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskA implements Task {
    final String hypernetSequenceRegex = "\\[[^\\]]*\\]";
    final String abbaRegex = "([a-z])(?!\\1)([a-z])\\2\\1";

    @Override
    public void run() {
        final List<String> IPs = FileReader.readLines("input/december7/input.txt");
        final int numberOfValidIPs = getValidIPs(IPs);

        System.out.println("Number of valid IPs: " + numberOfValidIPs);
    }

    private int getValidIPs(List<String> IPs) {
        int validIPs = 0;

        for (String IP : IPs) {
            final List<String> hypernetSequences = getHypernetSequences(IP);

            if (hypernetSequences.stream().anyMatch(this::matchesABBA)) {
                continue;
            }

            final String clippedString = IP.replaceAll(hypernetSequenceRegex, "-");
            if (matchesABBA(clippedString)) {
                validIPs++;
            }
        }

        return validIPs;
    }

    /**
     * All hypernet sequences are parts of the string
     * within brackets. I.E. 'abcde[hypernetsequence]fghij'
     * @param IP
     * @return
     */
    private List<String> getHypernetSequences(String IP) {
        Matcher matcher = Pattern.compile(hypernetSequenceRegex).matcher(IP);
        List<String> allHypernetSequences = new ArrayList<>();

        while (matcher.find()) {
            allHypernetSequences.add(matcher.group());
        }

        return allHypernetSequences;
    }

    /**
     * Checks whether given input matches the form abba
     * anywhere in the string. I.E., oxxo, abba, but not
     * oooo, aaaa for example.
     *
     * @param sequence
     * @return Whether given input matches form abba
     */
    private boolean matchesABBA(String sequence) {
        Matcher abbaMatcher = Pattern.compile(abbaRegex).matcher(sequence);
        return abbaMatcher.find();
    }

    @Override
    public String getTaskName() {
        return "December 7, task 1";
    }
}
