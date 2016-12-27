package se.maxjonsson.days.december10;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.Regex;

import java.util.*;
import java.util.regex.Matcher;

public class TaskA implements Task {
    private Map<String, ChipBot> bots = new HashMap<>();
    private Map<String, String> outputs = new HashMap<>();

    private final String instructionRegex = "^bot (?<botID>\\d{1,3}) gives low to (?<lowType>bot|output) (?<toLowID>\\d{1,3}) and high to (?<highType>bot|output) (?<toHighID>\\d{1,3})$";
    private final String populationRegex = "^value (?<value>\\d{1,5}) goes to bot (?<botID>\\d{1,3})$";

    private int firstChipToBeFound;
    private int secondChipToBeFound;

    public TaskA() {
        firstChipToBeFound = 61;
        secondChipToBeFound = 17;
    }

    @Override
    public void run() {
        final List<String> instructions = FileReader.readLines("input/december10/input.txt");
        instructions.forEach(this::processInstruction);

        final String startIndex = bots.entrySet().stream()
                                    .filter(entry -> entry.getValue().hasTwoChips())
                                    .findAny()
                                    .orElseThrow(() -> new RuntimeException("No bot had 2 chips!"))
                                    .getKey();

        final String botWhoComparedSoughtValues = findSpecialBot(startIndex);

        if (!bots.containsKey(botWhoComparedSoughtValues)) {
            throw new RuntimeException("Failed to find bot!");
        }
        else {
            System.out.println("Bot which compared " + firstChipToBeFound
                    + "- and " + secondChipToBeFound + "-type chips was: "
                    + botWhoComparedSoughtValues);
        }
    }

    private void processInstruction(final String instruction) {
        if (instruction.startsWith("bot")) {
            giveBotsInstructionAccordingTo(instruction);
        } else {
            populateBotsAccordingTo(instruction);
        }
    }

    private void giveBotsInstructionAccordingTo(final String instruction) {
        Matcher botInstructions = Regex.parse(instructionRegex, instruction);

        final String fromBotID = botInstructions.group("botID");
        final String toLowID = botInstructions.group("toLowID");
        final String toLowType = botInstructions.group("lowType");

        final String toHighID = botInstructions.group("toHighID");
        final String toHighType = botInstructions.group("highType");


        if (bots.containsKey(fromBotID)) {
            bots.get(fromBotID).setHighRecipient(toHighID, toHighType);
            bots.get(fromBotID).setLowRecipient(toLowID, toLowType);
        }
        else {
            final ChipBot bot = new ChipBot();

            bot.setHighRecipient(toHighID, toHighType);
            bot.setLowRecipient(toLowID, toLowType);
            bots.put(fromBotID, bot);
        }
    }

    private void populateBotsAccordingTo(final String instruction) {
        Matcher populateInstructionMatcher = Regex.parse(populationRegex, instruction);

        final int chipValue = Integer.parseInt(populateInstructionMatcher.group("value"));
        final String toBot = populateInstructionMatcher.group("botID");

        giveTo("bot", toBot, chipValue);
    }

    private void giveTo(String type, String index, int value) {
        if (type.equals("output")) {
            outputs.put(index, Integer.toString(value));
            return;
        }
        
        if (bots.containsKey(index)) {
            bots.get(index).giveChipOfValue(value);
        }
        else {
            bots.put(index, new ChipBot(value));
        }
    }

    /**
     * Find the bot which compares the two given inputs recursively.
     * @param index
     * @return The ID of the bot.
     */
    private String findSpecialBot(final String index) {
        final ChipBot currentBot = bots.get(index);

        final String highID = currentBot.getHighRecipientID();
        final String lowID = currentBot.getLowRecipientID();

        giveTo(currentBot.getHighRecipientType(), highID, currentBot.getHighestChip());
        giveTo(currentBot.getLowRecipientType(), lowID, currentBot.getLowestChip());

        if (currentBot.getHighRecipientType().equals("bot") && bots.get(highID).hasTwoChips()) {
            final String idFromChildren = findSpecialBot(highID);

            if (!idFromChildren.equals("")) {
                return idFromChildren;
            }
        }

        if (currentBot.getLowRecipientType().equals("bot") && bots.get(lowID).hasTwoChips()) {
            final String idFromChildren = findSpecialBot(lowID);

            if (!idFromChildren.equals("")) {
                return idFromChildren;
            }
        }

        if (currentBot.containsBoth(firstChipToBeFound, secondChipToBeFound)) {
            return index;
        }
        return "";
    }

    @Override
    public String getTaskName() {
        return "December 10, task 1";
    }


    public static void main(String... args) {
        TaskA a = new TaskA();
        a.run();
    }
}
