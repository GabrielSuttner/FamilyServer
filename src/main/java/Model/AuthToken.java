package Model;

import java.util.Random;

public class AuthToken {
    private int tokenID;
    private String userName;
    private String token;

    /**
     * Builds a new token.
     * @param userName
     */
    public AuthToken(String userName) {
        this.token = randomString();
        this.userName = userName;
    }

    /**
     *
     * @return username
     */
    public String getUserName() {
        return  this.userName;
    }

    /**
     *
     * @return token
     */
    public String getToken() {
        return this.token;
    }

    /**
     *
     * @return a random string
     */
    private String randomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    /**
     *
     * @param s
     * @return
     */
    public boolean validateToken(String s) {
        if(this.token == s) {
            return true;
        }
        return false;
    }
}
