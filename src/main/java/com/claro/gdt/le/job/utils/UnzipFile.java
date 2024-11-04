package com.claro.gdt.le.job.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
    private static final Logger LOG = LoggerFactory.getLogger(UnzipFile.class);
    public boolean unziping(File inFile) throws IOException {
        LOG.info("unziping process starts : {}",inFile);
        boolean result = false;
        try (GZIPInputStream gin = new GZIPInputStream(new FileInputStream(inFile))) {
            File outFile = new File(inFile.getParent(), inFile.getName().replaceAll("\\.gz", ""));
            try (FileOutputStream fos = new FileOutputStream(outFile)) {
                byte[] buf = new byte[100000];
                int len;
                while ((len = gin.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                result = true;
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            result = false;
        }
        return result;
    }
    public boolean unzipWin(File inFile) throws IOException {
        boolean result = false;
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(inFile))) {
            ZipEntry zipEntry = zis.getNextEntry();
            //
            while (zipEntry != null ) {
                File outFile = new File(inFile.getParent(), String.valueOf(zipEntry));
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    byte[] buf = new byte[100000];
                    int len;
                    while ((len = zis.read(buf)) > 0) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                    result = true;
                }
                zipEntry = zis.getNextEntry();
            }

        } catch (IOException e) {
            System.out.println(e);
            result = false;
        }
        return result;
    }
}
