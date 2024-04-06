package com.whlDownloader.whl;


import org.zeroturnaround.zip.ZipUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@org.springframework.stereotype.Service
public class Service {

    String downloadDirectory = "downloads";

    public byte[] whlfile_downloader(String packageName, boolean deps , String version) throws IOException, InterruptedException {
        download(packageName, deps , version);
        ZipUtil.pack(new File(downloadDirectory), new File("payload/downloads.zip"));

        return readFileToByteArray("payload/downloads.zip");


    }

    public static byte[] readFileToByteArray(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public void download(String packageName, boolean deps
    , String version ) throws IOException, InterruptedException {
        // Delete the directory and its contents if it exists
        File directory = new File(downloadDirectory);
        if (directory.exists()) {
            deleteDirectory(directory);
        }

        // Create the directory
        directory.mkdirs();

        StringBuilder cmd = new StringBuilder("pip download " + packageName);
        if(!version.equals("latest"))
        {
            cmd.append("==").append(version);
        }
        if (!deps) {
            cmd.append(" --no-deps");
        }

        String[] command = cmd.toString().split(" ");
        System.out.println(cmd);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        // Specify the download directory
        processBuilder.directory(new File(downloadDirectory));

        Process process = processBuilder.start();

        // Read the output of the process
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        process.waitFor();
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }


}
