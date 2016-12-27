package se.maxjonsson.days.december10;

import java.util.Arrays;

class ChipBot {
    private int[] chips = new int[2];

    private String lowRecipient = "";
    private String highRecipient = "";

    private String lowType = "";
    private String highType = "";

    public ChipBot() {
        empty();
    }

    public ChipBot(int initialValue) {
        empty();
        giveChipOfValue(initialValue);
    }

    public void setLowRecipient(final String ID, final String type) {
        lowRecipient = ID;
        lowType = type;
    }

    public String getLowRecipientType() {
        return lowType;
    }

    public String getLowRecipientID() {
        return lowRecipient;
    }

    public void setHighRecipient(final String ID, final String type) {
        highRecipient = ID;
        highType = type;
    }

    public String getHighRecipientType() {
        return highType;
    }

    public String getHighRecipientID() {
        return highRecipient;
    }

    public int getHighestChip() {
        if (chips[0] > chips[1]) {
            return chips[0];
        }

        return chips[1];
    }

    public int getLowestChip() {
        if (chips[0] > chips[1]) {
            return chips[1];
        }

        return chips[0];
    }

    public void giveChipOfValue(int value) {
        if (chips[0] == -1) {
            chips[0] = value;
        }
        else if (chips[1] == -1) {
            chips[1] = value;
        }
        else {
            throw new RuntimeException("A chipbot can only hold 2 chips!\n" +
                    "Tried to add " + value + " to \n" + this);
        }
    }

    public void empty() {
        Arrays.fill(chips, -1);
    }

    public boolean containsBoth(int a, int b) {
        if (a == b) {
            return false;
        }

        if (chips[0] == a || chips[1] == a) {
            return chips[0] == b || chips[1] == b;
        }

        return false;
    }

    public boolean hasTwoChips() {
        return chips[0] != -1 && chips[1] != -1;
    }

    @Override
    public String toString() {
        return "ChipBot [" + chips[0] + ", " + chips[1] + "] -> high: [" +
                highType + "] " + highRecipient + ", low: [" +
                lowType + "]" + lowRecipient;
    }
}
