/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 * 
 * Based on: http://java.dzone.com/articles/secure-password-storage-lots
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (kohtikumppanuutta@ixonos.com).
 *
 */
package fi.koku.services.utility.userinfo.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import fi.koku.KoKuFaultException;

public class UserInfoPasswordEncryption {

  private boolean authenticate(String attemptedPassword, String encryptedPassword, String salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Encrypt the clear-text password using the same salt that was used to
    // encrypt the original password
    byte[] encryptedAttemptedPassword = encryptedPassword(attemptedPassword, salt).getBytes();

    // Authentication succeeds if encrypted password that the user entered
    // is equal to the stored hash
    return Arrays.equals(encryptedPassword.getBytes(), encryptedAttemptedPassword);
  }

  public boolean authenticateUser(String attemptedPassword, String encryptedPassword, String salt) {
    boolean authenticated = false;

    try {
      authenticated = authenticate(attemptedPassword, encryptedPassword, salt);
    } catch (NoSuchAlgorithmException e) {
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PASSWORD_ENCRYPTION_ERROR;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);
    } catch (InvalidKeySpecException e) {
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PASSWORD_ENCRYPTION_ERROR;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);
    }
    return authenticated;
  }

  public String getEncryptedPassword(String password, String salt) {
    String encrypted = "";
    try {
      encrypted = encryptedPassword(password, salt);
    } catch (NoSuchAlgorithmException e) {
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PASSWORD_ENCRYPTION_ERROR;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);
    } catch (InvalidKeySpecException e) {
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PASSWORD_ENCRYPTION_ERROR;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);
    }
    return encrypted;
  }

  public String getSalt() {

    String salt = "";

    try {
      salt = generateSalt();
    } catch (NoSuchAlgorithmException e) {
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PASSWORD_ENCRYPTION_ERROR;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);      
    }
    return salt;
  }

  private String encryptedPassword(String password, String salt) throws NoSuchAlgorithmException,
      InvalidKeySpecException {
    // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
    // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
    String algorithm = "PBKDF2WithHmacSHA1";
    // SHA-1 generates 160 bit hashes, so that's what makes sense here
    int derivedKeyLength = 160;
    // Pick an iteration count that works for you. The NIST recommends at
    // least 1,000 iterations:
    int iterations = 20000;

    byte[] saltArray = salt.getBytes();

    KeySpec spec = new PBEKeySpec(password.toCharArray(), saltArray, iterations, derivedKeyLength);

    SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

    return new String(f.generateSecret(spec).getEncoded());
  }

  private String generateSalt() throws NoSuchAlgorithmException {
    // VERY important to use SecureRandom instead of just Random
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

    // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
    byte[] salt = new byte[8];
    random.nextBytes(salt);

    return new String(salt);
  }
}
