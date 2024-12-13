package com.example.tdesdemo;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class BlockChipHerUtil {

    private static final String SECRET_KEY_NOT_ALLOW_EMPTY = "SecretKey not allow empty";

    private static final String ALGORITHM = "DESede";

    private final String keyPartOne, keyPartTwo;

    public BlockChipHerUtil(String keyPartOne, String keyPartTwo) {
        this.keyPartOne = keyPartOne;
        this.keyPartTwo = keyPartTwo;
    }

    public SecretKey generateSecretKey() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        final String combinedKey = this.keyPartOne + this.keyPartTwo;

        byte[] keyBytes = combinedKey.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes24 = new byte[24];
        System.arraycopy(keyBytes, 0, keyBytes24, 0, Math.min(keyBytes.length, 24));

        final DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes24);
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(keySpec);
    }

    public String encryptingString(
            @Nullable SecretKey secretKey,
            @NonNull String rawMessage
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (secretKey == null) {
            throw new NullPointerException(SECRET_KEY_NOT_ALLOW_EMPTY);
        }

        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        final byte[] encryptedData = cipher.doFinal(rawMessage.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decryptingString(
            @Nullable SecretKey secretKey,
            @NonNull String decodedMessage
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (secretKey == null) {
            throw new NullPointerException(SECRET_KEY_NOT_ALLOW_EMPTY);
        }

        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        final byte[] decodedData = Base64.getDecoder().decode(decodedMessage);
        final byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
