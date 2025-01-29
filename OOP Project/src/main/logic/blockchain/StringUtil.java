package main.logic.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * class for hash generating
 */
public class StringUtil {
    /**
     * encoding function to create  hash for blocks
     * @param input
     */
    public static String applySha256(String input) {
        //System.out.println("pamPam:   "+input);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(); // Create a StringBuilder object to form the hash string in hexadecimal format
            for (byte b : hash) { // Iterate over each byte of the hash
                String hex = Integer.toHexString(0xff & b); // Convert the byte to a hexadecimal string
                if (hex.length() == 1) {
                    hexString.append('0'); // If the string length is less than 2 characters, add a leading zero
                }
                hexString.append(hex); // Append the hexadecimal representation of the byte to the hash string
            }
            return hexString.toString(); // Return the final hexadecimal representation of the hash as a string
        } catch (Exception e) {
            throw new RuntimeException(e); // If an exception occurs, throw a RuntimeException
        }
    }
}
