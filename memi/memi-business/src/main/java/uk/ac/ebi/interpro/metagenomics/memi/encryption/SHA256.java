package uk.ac.ebi.interpro.metagenomics.memi.encryption;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    private final static Log log = LogFactory.getLog(SHA256.class);

    public static String encrypt(String password) {
        log.info("Encrypting password...");
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
            log.error("Could not encrypt the specified password!", e);
        }
        return result;
    }
}