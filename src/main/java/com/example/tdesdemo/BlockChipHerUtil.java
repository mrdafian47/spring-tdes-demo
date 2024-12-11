package com.example.tdesdemo;

import org.springframework.lang.NonNull;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class BlockChipHerUtil {

    private final SecretKeySpec secretKeySpec;
    private final IvParameterSpec ivSpec;

    public BlockChipHerUtil(@NonNull String secretKey, @NonNull String vectorKey) {
        byte[] secretKeyByte = secretKey.getBytes();
        byte[] vectorKeyByte = vectorKey.getBytes();

        this.secretKeySpec = new SecretKeySpec(secretKeyByte, "TripleDES");
        this.ivSpec = new IvParameterSpec(vectorKeyByte);
    }

    public String encryptingString(
            @NonNull String rawMessage
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

        byte[] secretMessagesBytes = rawMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessagesBytes);

        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

        return encodedMessage;
    }

    public String decryptingString(
            @NonNull String decodedMessage
    ) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher decryptCipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

        byte[] rawEncryptedMessage = Base64.getDecoder().decode(decodedMessage);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(rawEncryptedMessage);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        return decryptedMessage;
    }


}
