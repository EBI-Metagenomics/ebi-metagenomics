package uk.ac.ebi.interpro.metagenomics.memi.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a simple utility class with method to encrypt password from the ENA database.
 * This class is provided by Lawrence Bower from the ENA group.
 *
 * @author Lawrence Bower, EMBL-EBI
 * @since 1.0-SNAPSHOT
 */
public class SHA256 {

    public static String encrypt(String password) {

        String result = password;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            result = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}