package com.zreader.utils;


import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 哈希加密和比对工具
 * 用于密码的加密和验证，使用指定的加密算法，使用动态盐值
 *
 * @author OrionLi
 * @date 2024/02/11
 */
public final class HexUtil {

    /**
     * 分隔符
     */
    private static final String SEPARATOR = "#";

    /**
     * 分割 {@link HexUtil#SEPARATOR} 后的长度
     */
    private static final int AFTER_SEPARATOR_LENGTH = 3;

    /**
     * 加密策略工厂，存储已知的加密策略实例
     */
    private static final Map<String, EncryptionPolicy> ENCRYPTION_POLICY_MAP = Map.of(
            "s1", (plainPassword, salt) -> Hashing.sha256().hashString(plainPassword + salt, StandardCharsets.UTF_8).toString(),
            "s2", (plainPassword, salt) -> Hashing.sha384().hashString(plainPassword + salt, StandardCharsets.UTF_8).toString(),
            "s3", ((plainPassword, salt) -> Hashing.sha512().hashString(plainPassword + salt, StandardCharsets.UTF_8).toString())
    );

    private static final List<String> POLICY_CODES = ENCRYPTION_POLICY_MAP.keySet().stream().toList();

    /**
     * 比对用户的明文密码和加密字符串
     *
     * @param storedPassword 数据库中存储的密码字符串
     * @param plainPassword  用户输入的明文密码
     * @return 如果密码匹配，则返回true；否则返回false
     */
    public static boolean verify(String storedPassword, String plainPassword) {
        String[] parts = storedPassword.split(SEPARATOR);
        if (parts.length != AFTER_SEPARATOR_LENGTH) {
            throw new IllegalArgumentException("Invalid password format, storedPassword: " + storedPassword);
        }
        String salt = parts[0];
        String algorithmCode = parts[1];
        String encryptedValue = parts[2];

        EncryptionPolicy policy = ENCRYPTION_POLICY_MAP.get(algorithmCode);
        if (policy == null) {
            throw new UnsupportedOperationException("Unsupported util algorithm: " + algorithmCode);
        }

        return encryptedValue.equals(policy.encrypt(plainPassword, salt));
    }

    /**
     * 基于随机盐值 + 随机加密算法进行加密
     *
     * @param plainPassword 明文密码
     * @return 格式化后的加密字符串
     */
    public static String encryptAndFormat(String plainPassword) {
        String algorithmCode = POLICY_CODES.get(ThreadLocalRandom.current().nextInt(POLICY_CODES.size()));
        EncryptionPolicy policy = ENCRYPTION_POLICY_MAP.get(algorithmCode);

        String dynamicSalt = getRandomSalt();
        String encryptedValue = policy.encrypt(plainPassword, dynamicSalt);

        return dynamicSalt + SEPARATOR + algorithmCode + SEPARATOR + encryptedValue;
    }

    /**
     * 生成随机盐值
     *
     * @return 随机盐值
     */
    private static String getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[random.nextInt(5, 9)];
        random.nextBytes(salt);
        return BaseEncoding.base64Url().omitPadding().encode(salt);
    }

    /**
     * 加密策略接口
     *
     * @author OrionLi
     */
    @FunctionalInterface
    interface EncryptionPolicy {

        /**
         * 加密给定的明文密码
         *
         * @param plainPassword 明文密码
         * @param salt          盐值
         * @return 加密后的密码字符串
         */
        String encrypt(String plainPassword, String salt);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        String encrypted = HexUtil.encryptAndFormat("admin123");
        System.out.println(encrypted);
        System.out.println(HexUtil.verify(encrypted, "admin123")); // true
    }

}

