package se.maxjonsson.days.december10;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;
import se.maxjonsson.utils.Regex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class TaskB implements Task {
    private Map<String, ChipBot> bots = new HashMap<>();
    private Map<String, String> outputs = new HashMap<>();

    private final String instructionRegex = "^bot (?<botID>\\d{1,3}) gives low to (?<lowType>bot|output) (?<toLowID>\\d{1,3}) and high to (?<highType>bot|output) (?<toHighID>\\d{1,3})$";
    private final String populationRegex = "^value (?<value>\\d{1,5}) goes to bot (?<botID>\\d{1,3})$";

    @Override
    public void run() {
        final List<String> instructions = FileReader.readLines("input/december10/input.txt");
        instructions.forEach(this::processInstruction);

        while (bots.values().stream().anyMatch(ChipBot::hasTwoChips)) {
            performBotInstructions();
        }

        final int sumOfLowestThree = Integer.parseInt(outputs.get("0")) * Integer.parseInt(outputs.get("1")) * Integer.parseInt(outputs.get("2"));
        System.out.println("The sum of the lowest three output chip-bins was: " + sumOfLowestThree );
    }

    private void performBotInstructions() {
        final ChipBot currentBot = bots.values().stream()
                .filter(ChipBot::hasTwoChips)
                .findAny()
                .get();

        final String highID = currentBot.getHighRecipientID();
        final String lowID = currentBot.getLowRecipientID();

        giveTo(currentBot.getHighRecipientType(), highID, currentBot.getHighestChip());
        giveTo(currentBot.getLowRecipientType(), lowID, currentBot.getLowestChip());

        currentBot.empty();
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

    @Override
    public String getTaskName() {
        return "December 10, task 2";
    }
}
