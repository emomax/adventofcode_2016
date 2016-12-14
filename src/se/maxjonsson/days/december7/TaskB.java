package se.maxjonsson.days.december7;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskB implements Task {
    final String hypernetSequenceRegex = "\\[[^\\]]*\\]";
    final String abaRegex = "([a-z])(?!\\1)([a-z])\\1";

    @Override
    public void run() {
        final List<String> IPs = FileReader.readLines("input/december7/input.txt");
        final int numberOfValidIPs = getValidIPs(IPs);

        System.out.println("Number of valid IPs that support SSL: " + numberOfValidIPs);
    }

    private int getValidIPs(List<String> IPs) {
        int validIPs = 0;

        for (String IP : IPs) {
            final List<String> hypernetSequences = getHypernetSequences(IP);

            if (!hypernetSequences.stream().anyMatch(this::matchesABA)) {
                // Hypernet Sequence lacks matchesABA
                continue;
            }

            final List<String> possibleBABs = getPossibleABAs(hypernetSequences);
            final String clippedString = IP.replaceAll(hypernetSequenceRegex, "-");

            if (possibleBABs.stream().anyMatch(babRegex -> matches(babRegex, clippedString))) {
                validIPs++;
            }
        }

        return validIPs;
    }

    private List<String> getHypernetSequences(String IP) {
        Matcher matcher = Pattern.compile(hypernetSequenceRegex).matcher(IP);
        List<String> allHypernetSequences = new ArrayList<>();

        while (matcher.find()) {
            allHypernetSequences.add(matcher.group());
        }

        return allHypernetSequences;
    }

    /**
     * Find all items that matches the form [aba] of
     * the string.
     * @param hypernetSequences
     * @return
     */
    private List<String> getPossibleABAs(List<String> hypernetSequences) {
        List<String> possibleABAs = new ArrayList<>();

        for (String sequence : hypernetSequences) {
            sequence = sequence.replaceAll("[\\[\\]]", "");

            for (int i = 0; i < sequence.length() - 2; i++) {
                final String nextThreeLetters = sequence.substring(i, i+3);

                if (matchesABA(nextThreeLetters)) {
                    possibleABAs.add(nextThreeLetters.charAt(1) + "" + nextThreeLetters.charAt(0) + "" + nextThreeLetters.charAt(1));
                }
            }
        }

        return possibleABAs;
    }

    /**
     * Checks whether given input matches the form [aba]
     * anywhere in the string. I.E., oxo, aba, but not
     * ooo, aaa for example.
     *
     * @param sequence
     * @return Whether given input matches form aba
     */
    private boolean matchesABA(String sequence) {
        return matches(abaRegex, sequence);
    }

    private boolean matches(String pattern, String sequence) {
        Matcher abaMatcher = Pattern.compile(pattern).matcher(sequence);
        return abaMatcher.find();
    }

    @Override
    public String getTaskName() {
        return "December 7, task 2";
    }

    public static void main(String... args) {
        TaskB test = new TaskB();
        test.run();
    }
}
