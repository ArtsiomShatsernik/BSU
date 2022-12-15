package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.*;
import java.util.zip.*;

public class ArchivingLib {
    public static void packJar(ArrayList<String> fileNames, String jarName) throws IOException {
        JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(jarName + ".jar"));
        jarOut.setLevel(Deflater.DEFAULT_COMPRESSION);
        for (String file : fileNames) {
            jarOut.putNextEntry(new JarEntry(file));
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            int len;
            while ((len = inputStream.read(buffer)) > 0)
                jarOut.write(buffer, 0, len);
            jarOut.closeEntry();
            inputStream.close();
        }
        jarOut.close();
    }

    public static void packJar(String jarName) throws IOException {
        ArrayList<String> fileNames;
        fileNames = formFiles();
        JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(jarName + ".jar"));
        jarOut.setLevel(Deflater.DEFAULT_COMPRESSION);
        for (String file : fileNames) {
            jarOut.putNextEntry(new JarEntry(file));
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            int len;
            while ((len = inputStream.read(buffer)) > 0)
                jarOut.write(buffer, 0, len);
            jarOut.closeEntry();
            inputStream.close();
        }
        jarOut.close();
    }
    public static  void unpackingJar(String jarName) {
        byte[] buffer = new byte[1024];
        String dstDirectory = jarName.substring(0, jarName.lastIndexOf("."));
        File dstDir = new File(dstDirectory);
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        try {
            JarInputStream jarIn = new JarInputStream(new FileInputStream(jarName));
            ZipEntry entry = jarIn.getNextEntry();
            String nextFileName;
            while (entry != null) {
                nextFileName = entry.getName();
                File nextFile = new File(dstDirectory + File.separator + nextFileName);
                if (entry.isDirectory()) {
                    nextFile.mkdir();
                } else {
                    new File(nextFile.getParent()).mkdir();
                    FileOutputStream fileOut = new FileOutputStream(nextFile);
                    int length;
                    while ((length = jarIn.read(buffer)) > 0) {
                        fileOut.write(buffer, 0, length);
                    }
                }
                entry = jarIn.getNextEntry();
            }
            jarIn.closeEntry();
            jarIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void packZip(ArrayList<String> fileNames, String zipName) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipName + ".zip"));
        zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);
        for (String file : fileNames) {
            zipOut.putNextEntry(new ZipEntry(file));
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            int len;
            while ((len = inputStream.read(buffer)) > 0)
                zipOut.write(buffer, 0, len);
            zipOut.closeEntry();
            inputStream.close();
        }
        zipOut.close();
    }

    public static void packZip(String zipName) throws IOException {
        ArrayList<String> fileNames;
        fileNames = formFiles();
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipName + ".zip"));
        zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);
        for (String file : fileNames) {
            zipOut.putNextEntry(new ZipEntry(file));
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            int len;
            while ((len = inputStream.read(buffer)) > 0)
                zipOut.write(buffer, 0, len);
            zipOut.closeEntry();
            inputStream.close();
        }
        zipOut.close();
    }
    public static  void unpackingZip(String zipName) {
        byte[] buffer = new byte[1024];
        String dstDirectory = zipName.substring(0, zipName.lastIndexOf("."));
        File dstDir = new File(dstDirectory);
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        try {
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipName));
            ZipEntry entry = zipIn.getNextEntry();
            String nextFileName;
            while (entry != null) {
                nextFileName = entry.getName();
                File nextFile = new File(dstDirectory + File.separator + nextFileName);
                if (entry.isDirectory()) {
                    nextFile.mkdir();
                } else {
                    new File(nextFile.getParent()).mkdir();
                    FileOutputStream fileOut = new FileOutputStream(nextFile);
                    int length;
                    while ((length = zipIn.read(buffer)) > 0) {
                        fileOut.write(buffer, 0, length);
                    }
                }
                entry = zipIn.getNextEntry();
            }
            zipIn.closeEntry();
            zipIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static ArrayList<String> formFiles() {
        ArrayList<String> files = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        String file;
        System.out.println("Enter file names.\nWhen all files entered write \"Stop\".");
        Boolean flag = true;
        while(true) {
            file = in.nextLine();
            if (file.equals("Stop")) {
                break;
            }
            try {
                FileInputStream fin = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                flag = false;
                System.out.println("Incorrect file name");
            }
            if (flag.equals(true)) {
                files.add(file);
            }
            flag = true;
        }
        return files;
    }
}

