package se.maxjonsson.days.december5;

import javafx.util.Pair;
import se.maxjonsson.Task;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TaskA implements Task {
    MessageDigest messageDigest;

    public TaskA() {
        try {
            messageDigest = MessageDigest.getInstance("md5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to find algorithm", e);
        }
    }

    @Override
    public void run() {
        final String roomName = "ojvtpuvg";

        System.out.println("Decrypting .. Please hold on.");
        final String password = findPasswordForRoom(roomName);

        System.out.println(".. Done.\nDecrypted password for door '" + roomName + "': " + password);
    }

    private String findPasswordForRoom(String roomName) {
        String password = "";
        long nextIndex = 0L;

        for (int i = 0; i < 8; i++) {
            Pair<String, Long> nextHash = findNextHash(roomName, nextIndex);

            System.out.println("Found hash '" + nextHash.getKey() + "'");

            password += nextHash.getKey().charAt(5);
            nextIndex = nextHash.getValue();
        }

        return password;
    }

    Pair<String, Long> findNextHash(String base, long start) {
        long i = 0L;

        while (true) {
            final String md5Hash = md5(base + (start + i++));

            if (md5Hash.startsWith("00000")) {
                return new Pair(md5Hash, start + i);
            }
        }
    }

    private String md5(String toHash) {
        messageDigest.reset();
        messageDigest.update(toHash.getBytes(Charset.forName("UTF8")));

        byte[] hashedBytes =  messageDigest.digest();

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hashedBytes.length; i++) {
            String hex=Integer.toHexString(0xff & hashedBytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    @Override
    public String getTaskName() {
        return "December 5, task 1";
    }
}
