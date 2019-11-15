package com.exmaple;


import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class WxChatImgRevert2 {

    public static void main(String[] args) {
        String path = "C:\\Users\\kobe2\\Documents\\WeChat Files\\kobe24167\\FileStorage";
        String targetPath = "E:\\temp";
        convert(path, targetPath);
    }

    /**
     * @param path       ͼƬĿ¼��ַ
     * @param targetPath ת����Ŀ¼
     */
    private static void convert(String path, String targetPath) {
        File[] file = new File(path).listFiles();
        if (file == null) {
            return;
        }
        int size = file.length;
        System.out.println("�ܹ�" + size + "���ļ�");
        AtomicReference<Integer> integer = new AtomicReference<>(0);
        AtomicInteger x = new AtomicInteger();
        for (File file1 : file) {
            if (file1.isFile()) {
                Object[] xori = getXor(file1);
                if (xori != null && xori[1] != null){
                    x.set((int)xori[1]);
                }
                break;
            }
        }
        Arrays.stream(file).parallel().forEach(file1 -> {
            if (file1.isDirectory()) {
                String[] newTargetPath = file1.getPath().split("/|\\\\");
                File targetFile = new File(targetPath+File.separator+newTargetPath[newTargetPath.length - 1]);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                convert(file1.getPath(),targetPath+File.separator+newTargetPath[newTargetPath.length - 1]);
                return;
            }
            Object[] xor = getXor(file1);
            if (x.get() == 0 && xor[1] != null && (int) xor[1] != 0) {
                x.set((int) xor[1]);
            }
            xor[1] = xor[1] == null ? x.get() : xor[1];
            try (InputStream reader = new FileInputStream(file1);
                 OutputStream writer =
                         new FileOutputStream(targetPath + File.separator + file1.getName().split("\\.")[0] + (xor[0] != null ?
                                 "." + xor[0] : ""))) {
                byte[] bytes = new byte[1024 * 10];
                int b;
                while ((b = reader.read(bytes)) != -1) {//�����in.read(bytes);���ǰ��������еĶ�����д�뵽�ڴ��У�bytes����
                    for (int i = 0; i < bytes.length; i++) {
                        bytes[i] = (byte) (int) (bytes[i] ^ (int) xor[1]);
                        if (i == (b - 1)) {
                            break;
                        }
                    }
                    writer.write(bytes, 0, b);
                    writer.flush();
                }
                integer.set(integer.get() + 1);
                System.out.println(file1.getName() + "(��С:" + ((double) file1.length() / 1000) + "kb,���ֵ:" + xor[1] + ")," +
                        "���ȣ�" + integer.get() +
                        "/" + size);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("������ϣ�");
    }

    /**
     * �ж�ͼƬ���ֵ
     *
     * @param file
     * @return
     */
    private static Object[] getXor(File file) {
        Object[] xor = null;
        if (file != null) {
            byte[] bytes = new byte[4];
            try (InputStream reader = new FileInputStream(file)) {
                reader.read(bytes, 0, bytes.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            xor = getXor(bytes);
        }
        return xor;
    }

    /**
     * @param bytes
     * @return
     */
    private static Object[] getXor(byte[] bytes) {
        Object[] xorType = new Object[2];
        int[] xors = new int[3];
        for (Map.Entry<String, String> type : FILE_TYPE_MAP.entrySet()) {
            String[] hex = {
                    String.valueOf(type.getKey().charAt(0)) + type.getKey().charAt(1),
                    String.valueOf(type.getKey().charAt(2)) + type.getKey().charAt(3),
                    String.valueOf(type.getKey().charAt(4)) + type.getKey().charAt(5)
            };
            xors[0] = bytes[0] & 0xFF ^ Integer.parseInt(hex[0], 16);
            xors[1] = bytes[1] & 0xFF ^ Integer.parseInt(hex[1], 16);
            xors[2] = bytes[2] & 0xFF ^ Integer.parseInt(hex[2], 16);
            if (xors[0] == xors[1] && xors[1] == xors[2]) {
                xorType[0] = type.getValue();
                xorType[1] = xors[0];
                break;
            }
        }
        return xorType;
    }

    private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    static {
        getAllFileType();
    }

    private static void getAllFileType() {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png"); //PNG (png)
        FILE_TYPE_MAP.put("47494638396126026f01", "gif"); //GIF (gif)
        FILE_TYPE_MAP.put("49492a00227105008037", "tif"); //TIFF (tif)
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); //16ɫλͼ(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); //24λλͼ(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); //256ɫλͼ(bmp)
        FILE_TYPE_MAP.put("41433130313500000000", "dwg"); //CAD (dwg)
        FILE_TYPE_MAP.put("3c21444f435459504520", "html"); //HTML (html)
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm"); //HTM (htm)
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css"); //css
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js"); //js
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("38425053000100000000", "psd"); //Photoshop (psd)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel ע�⣺word��msi �� excel���ļ�ͷһ��
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd"); //Visio ��ͼ
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb"); //MS Access (mdb)
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462d312e360d25", "pdf"); //Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); //rmvb/rm��ͬ
        FILE_TYPE_MAP.put("464c5601050000000900", "flv"); //flv��f4v��ͬ
        FILE_TYPE_MAP.put("00000020667479706973", "mp4");
        FILE_TYPE_MAP.put("49443303000000000f76", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg"); //
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); //wmv��asf��ͬ
        FILE_TYPE_MAP.put("524946464694c9015741", "wav"); //Wave (wav)
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid"); //MIDI (mid)
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");//��ִ���ļ�
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");//jsp�ļ�
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");//MF�ļ�
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");//xml�ļ�
        FILE_TYPE_MAP.put("efbbbf2f2a0d0a53514c", "sql");//xml�ļ�
        FILE_TYPE_MAP.put("7061636b616765207765", "java");//java�ļ�
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");//bat�ļ�
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");//gz�ļ�
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");//bat�ļ�
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");//bat�ļ�
        FILE_TYPE_MAP.put("49545346030000006000", "chm");//bat�ļ�
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");//bat�ļ�
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");//docx�ļ�
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");//WPS����wps�����et����ʾdps����һ����
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("494d4b48010100000200", "264");


        FILE_TYPE_MAP.put("6D6F6F76", "mov"); //Quicktime (mov)
        FILE_TYPE_MAP.put("FF575043", "wpd"); //WordPerfect (wpd)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)
        FILE_TYPE_MAP.put("2142444E", "pst"); //Outlook (pst)
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf"); //Quicken (qdf)
        FILE_TYPE_MAP.put("E3828596", "pwl"); //Windows Password (pwl)
        FILE_TYPE_MAP.put("2E7261FD", "ram"); //Real Audio (ram)
    }
}