package impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.function.Function;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Decrypter {

    private String username, password, configPath;
    public static Decrypter instance;

    public Decrypter() {
        username = "empty";
        password = "empty";
        configPath = "./config.json"; // Config File Path
    }

    public static Decrypter getInstance() {
        if (instance == null)
            instance = new Decrypter();
        return instance;
    }

    public void initialize() {
        try {
            File configFile = new File(configPath);

            if (!configFile.exists()) {
                System.err.println("Config file not found.");
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
                StringBuilder sb = new StringBuilder();
                String str;

                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }

                JsonObject obj = JsonParser.parseString(sb.toString()).getAsJsonObject();
                setUsername(obj.get("rememberName").getAsString());

                String encryptedPassword = obj.get("rememberPass").getAsString();
                setPassword(Decryptor.AES_ECB_PKCS5.decrypt(encryptedPassword));
            }

            System.out.println("Username: " + getUsername());
            System.out.println("Password: " + getPassword());
        } catch (Exception e) {
            System.err.println("Error initializing: " + e.getMessage());
        }
    }

    private String getUsername() {
        return username.isEmpty() ? new NullPointerException("Username is empty").getMessage() : username;
    }

    private String getPassword() {
        return password.isEmpty() ? new NullPointerException("Password is empty").getMessage() : password;
    }

    private void setUsername(String username) {
        if (!username.isEmpty()) {
            this.username = username;
        } else {
            System.err.println("Username is empty or null");
        }
    }

    private void setPassword(String password) {
        if (!password.isEmpty()) {
            this.password = password;
        } else {
            System.err.println("Password is empty or null");
        }
    }

    public enum Decryptor {

        AES_ECB_PKCS5 {
            @Override
            public String decrypt(String encryptedPassword) {
                Function<String, String> decryptAndRemovePrefix = (str) -> {
                    try {
                        byte[] key = "2640023187059250".getBytes("utf-8");
                        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
                        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                        byte[] decryptedBytes = cipher.doFinal(DatatypeConverter.parseBase64Binary(str));
                        return new String(decryptedBytes);
                    } catch (Exception e) {
                        throw new RuntimeException("Decryption failed", e);
                    }
                };

                String decryptedString = decryptAndRemovePrefix
                        .andThen(Decryptor::getRiseVers)
                        .andThen(result -> result.split("#")[0])
                        .apply(encryptedPassword);

                return decryptedString;

            }
        };


        private static String getRiseVers(String input) {
            Function<String, String> decryptAndRemovePrefix = (str) ->
                    decryptBase64(str)
                            .replace("3ebi2mclmAM7Ao2", "")
                            .replace("KweGTngiZOOj9d6", "");

            String decodedString = decryptAndRemovePrefix
                    .andThen(decryptAndRemovePrefix)
                    .andThen(Decryptor::decryptBase64)
                    .apply(input);

            return decodedString;
        }

        private static String decryptBase64(String input) {
            try {
                return new String(Base64.decodeBase64(input), "utf-8");
            } catch (Exception ignored) {
                return null;
            }
        }

        public abstract String decrypt(String encryptedPassword);

    }
}