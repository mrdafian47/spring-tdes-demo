package com.example.tdesdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class BlockChipHerUtilTest {

    private BlockChipHerUtil blockChipHerUtil;

    @BeforeEach
    void setUp() {
        blockChipHerUtil = new BlockChipHerUtil(
                "123456789ABCDEFG",
                "HJKLMNO123456789"
        );
    }

    @Test
    void testGenerateSecretKey() {
        try {
            final SecretKey secretKey = blockChipHerUtil.generateSecretKey();
            assertNotNull(secretKey, "SecretKey should not be null");
            assertEquals("DESede", secretKey.getAlgorithm(), "Algorithm should be DESede");
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            fail("Exception during key generation: " + e.getMessage());
        }
    }

    @Test
    void testEncryptingAndDecryptingString() {
        try {
            final SecretKey secretKey = blockChipHerUtil.generateSecretKey();
            final String rawMessage = "This is a test message";

            // Encrypt the raw message
            final String encryptedMessage = blockChipHerUtil.encryptingString(secretKey, rawMessage);
            assertNotNull(encryptedMessage, "Encrypted message should not be null");

            // Decrypt the message back
            final String decryptedMessage = blockChipHerUtil.decryptingString(secretKey, encryptedMessage);
            assertEquals(rawMessage, decryptedMessage, "Decrypted message should match the original raw message");
        } catch (Exception e) {
            fail("Exception during encryption/decryption: " + e.getMessage());
        }
    }

    @Test
    void testEncryptingStringWithNullKey() {
        final String rawMessage = "Test Message";
        assertThrows(NullPointerException.class, () -> blockChipHerUtil.encryptingString(null, rawMessage));
    }

    @Test
    void testDecryptingStringWithNullKey() {
        final String encodedMessage = "TestMessage";
        assertThrows(NullPointerException.class, () -> blockChipHerUtil.decryptingString(null, encodedMessage));
    }
}