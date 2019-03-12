package com.aa.test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class DateTester {

	public static void ss () {
		long millisecondOffset = 60*60*24*365*10;
		String start = "1990-01-01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			Date startDate = sdf.parse(start);
			System.out.println(startDate.getTime());
			System.out.println(new BigDecimal(Math.random()).multiply(new BigDecimal(millisecondOffset)).longValue());
			BigDecimal birthday = new BigDecimal(startDate.getTime()/1000)
					.add(new BigDecimal(Math.random()).multiply(new BigDecimal(millisecondOffset)));
			Date birthdayDate = new Date(birthday.longValue()*1000);
			System.out.println(sdf.format(birthdayDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ss();
		password();
		System.out.println(md5("123"));
	}
	private static void password() {
		// ����
		try {
			String password = Base64.getEncoder().encodeToString("kobe24167?123456".getBytes("UTF-8"));
			System.out.println(password);
			System.out.println(new String(Base64.getDecoder().decode(password.getBytes()), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 //дһ��md5���ܵķ���
    public static String md5(String plainText) {
        //����һ���ֽ�����
        byte[] secretBytes = null;
        try {
              // ����һ��MD5���ܼ���ժҪ  
            MessageDigest md = MessageDigest.getInstance("MD5");
            //���ַ������м���
            md.update(plainText.getBytes());
            //��ü��ܺ������
            secretBytes = md.digest();
            System.out.println(new String(secretBytes, "UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("û��md5����㷨��");
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //�����ܺ������ת��Ϊ16��������
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16��������
        // �����������δ��32λ����Ҫǰ�油0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
