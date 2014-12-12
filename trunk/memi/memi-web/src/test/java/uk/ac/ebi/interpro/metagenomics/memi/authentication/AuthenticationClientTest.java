package uk.ac.ebi.interpro.metagenomics.memi.authentication;


import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import uk.ac.ebi.ena.authentication.client.AuthenticationClient;
import uk.ac.ebi.ena.authentication.client.AuthenticationClientImpl;
import uk.ac.ebi.ena.authentication.exception.AuthException;
import uk.ac.ebi.ena.authentication.model.AuthRealm;
import uk.ac.ebi.ena.authentication.model.AuthResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * This unit test will make sure that the current version of the authentication client (maintained by ENA) still works with the latest authentication web service.
 *
 * @author Maxim Scheremetjew
 */
public class AuthenticationClientTest {
    /**
     * Tests an invalid service URL.
     */
    @Test
    public void testAuthenticationClientInvalidServiceURL() throws IOException, NoSuchAlgorithmException {
        //Setting test attributes
        final String authenticationServiceUrl = "http://test:0000/ena/authentication/";
        final String username = "test";
        final String password = "";

        //Instantiate the client with an invalid service URL
        final AuthenticationClient authenticationClient = new AuthenticationClientImpl(authenticationServiceUrl);

        try {
            //Set the auth realm to SRA as recommended by ENA
            final List<AuthRealm> realms = Arrays.asList(new AuthRealm[]{AuthRealm.SRA});
            //Login trial
            AuthResult authResult = authenticationClient.login(username, password, realms);
            assertTrue("Login trial should fail! Authenticated as " + authResult.getPrinciple(), false);
        } catch (final AuthException authException) {
            final String actual = authException.getMessage();
            final String expected = "The service is currently unavailable, please try again later.";
            assertEquals("Unexpected authentication exception message", expected, actual);
        }
    }

    /**
     * Tests an invalid user name password combination.
     */
    @Test
    public void testAuthenticationClientInvalidUserNamePassword() {
        //Setting test attributes
        Properties props = getProperties();
        final String authenticationServiceUrl = props.getProperty("authentication.service.url");
        final String username = "test";
        final String password = "test";

        //Instantiate the client with an invalid service URL
        final AuthenticationClient authenticationClient = new AuthenticationClientImpl(authenticationServiceUrl);

        try {
            //Set the auth realm to SRA as recommended by ENA
            final List<AuthRealm> realms = Arrays.asList(new AuthRealm[]{AuthRealm.SRA});
            //Login trial
            AuthResult authResult = authenticationClient.login(username, password, realms);
            assertTrue("Login trial should fail! Authenticated as " + authResult.getPrinciple(), false);
        } catch (final AuthException authException) {
            final String actual = authException.getMessage();
            final String expected = "Invalid username or password";
            assertEquals("Unexpected authentication exception message", expected, actual);
        }
    }

    /**
     * Tests a valid user name password combination.
     */
    @Test
    public void testAuthenticationClientValidAuthentication() {
        //Setting test attributes
        Properties props = getProperties();
        final String authenticationServiceUrl = props.getProperty("authentication.service.url");
        final String username = props.getProperty("username");
        final String password = loadPasswordFromFile();

        //Instantiate the client with an invalid service URL
        final AuthenticationClient authenticationClient = new AuthenticationClientImpl(authenticationServiceUrl);

        try {
            final List<AuthRealm> realms = Arrays.asList(new AuthRealm[]{AuthRealm.SRA});
            final AuthResult authResult = authenticationClient.login(username, password, realms);

            assertNotNull("Authentication result shouldn't be NULL!", authResult);
            assertTrue("Not authenticated successfully for user " + username + " !", authResult.getAuthenticated());
        } catch (final AuthException ex) {
            assertTrue("Login trial shouldn't fail!", false);
        }
    }

    private String loadPasswordFromFile() {
        URL url = getClass().getClassLoader().getResource("binary.dat");
        assert url != null;
        File datFile = null;
        try {
            datFile = new File(url.toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(datFile);

            StringBuilder sb = new StringBuilder();
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char
                sb.append((char) content);
            }
            return new String(Base64.decodeBase64(sb.toString()));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        String propertyFileName = "properties.config";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
        if (inputStream == null) {
            throw new IllegalStateException("Property file " + propertyFileName + " not found in the classpath.");
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Problem accessing properties file properties.config");
        }
        return properties;
    }
}
