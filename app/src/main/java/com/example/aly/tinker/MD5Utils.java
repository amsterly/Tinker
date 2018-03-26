package com.example.aly.tinker;

/**
 * Created by fashionaly on 2018/3/26.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类，提供字符串MD5加密（校验）、文件MD5值获取(校验)功能
 */
public class MD5Utils {

    /**
     * 16进制字符集
     */
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 指定算法为MD5的MessageDigest
     */
    private static MessageDigest messageDigest = null;

    /**
     * 初始化messageDigest的加密算法为MD5
     */
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的MD5值
     *
     * @param file 目标文件
     * @return MD5字符串
     */
    public static String getFileMD5String(File file) {
        String ret = "";
//        FileInputStream in = null;
//        FileChannel ch = null;
//
//        try {
//            in = new FileInputStream(file);
//            ch = in.getChannel();
//            ByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
//            messageDigest.update(byteBuffer);
//            ret = bytesToHex(messageDigest.digest());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(ch, in);
//        }
//
        return ret;
    }

    public static boolean isFileMD5Matched(String filePath, String originalMD5) {
        try {

            InputStream fis;
            fis = new FileInputStream(new File(filePath));
            byte[] buffer = new byte[024];
            int numRead;
            while ((numRead = fis.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, numRead);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return originalMD5.equals(bufferToHex(messageDigest.digest(), 0, messageDigest.digest().length));
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * 获取文件的MD5值
     *
     * @param fileName 目标文件的完整名称
     * @return MD5字符串
     */
    public static String getFileMD5String(String fileName) {
        return getFileMD5String(new File(fileName));
    }

    /**
     * MD5加密字符串
     *
     * @param str 目标字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5String(String str) {
        return getMD5String(str.getBytes());
    }

    /**
     * MD5加密以byte数组表示的字符串
     *
     * @param bytes 目标byte数组
     * @return MD5加密后的字符串
     */
    public static String getMD5String(byte[] bytes) {
        messageDigest.update(bytes);
        return bytesToHex(messageDigest.digest());
    }

    /**
     * 校验密码与其MD5是否一致
     *
     * @param pwd 密码字符串
     * @param md5 基准MD5值
     * @return 检验结果
     */
    public static boolean checkPassword(String pwd, String md5) {
        return getMD5String(pwd).equalsIgnoreCase(md5);
    }

    /**
     * 校验密码与其MD5是否一致
     *
     * @param pwd 以字符数组表示的密码
     * @param md5 基准MD5值
     * @return 检验结果
     */
    public static boolean checkPassword(char[] pwd, String md5) {
        return checkPassword(new String(pwd), md5);
    }

    /**
     * 检验文件的MD5值
     *
     * @param file 目标文件
     * @param md5  基准MD5值
     * @return 检验结果
     */
    public static boolean checkFileMD5(File file, String md5) {
        return getFileMD5String(file).equalsIgnoreCase(md5);
    }

    /**
     * 检验文件的MD5值
     *
     * @param fileName 目标文件的完整名称
     * @param md5      基准MD5值
     * @return 检验结果
     */
    public static boolean checkFileMD5(String fileName, String md5) {
        return checkFileMD5(new File(fileName), md5);
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 目标字节数组
     * @return 转换结果
     */
    public static String bytesToHex(byte bytes[]) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    /**
     * 将字节数组中指定区间的子数组转换成16进制字符串
     *
     * @param bytes 目标字节数组
     * @param start 起始位置（包括该位置）
     * @param end   结束位置（不包括该位置）
     * @return 转换结果
     */
    public static String bytesToHex(byte bytes[], int start, int end) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < start + end; i++) {
            sb.append(byteToHex(bytes[i]));
        }

        return sb.toString();
    }

    /**
     * 双层加密的MD5值
     *
     * @param strSrc
     */
    public static String getSimenMD5(String strSrc) {
        String str1 = getMD5String(strSrc).toLowerCase();

        StringBuffer str2 = new StringBuffer("");
        str2.append(str1.substring(0, 2)).append("|")
                .append(str1.substring(4, 10)).append("|")
                .append(str1.substring(8, 21)).append("|")
                .append(str1.substring(14, 19)).append("|")
                .append(str1.substring(1, 9)).append("|")
                .append(str1.substring(24, 27));

        return getMD5String(str2.toString());
    }

    /**
     * 将单个字节码转换成16进制字符串
     *
     * @param bt 目标字节
     * @return 转换结果
     */
    public static String byteToHex(byte bt) {
        return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];
    }

    public static void main(String[] args) throws IOException {
        String fileName = "D:/javawed/debug.js";
        String md5 = getFileMD5String(new File(fileName));
        System.out.println(fileName + "文件MD5： " + md5);

        System.out.println(getSimenMD5("123456"));
    }
}
