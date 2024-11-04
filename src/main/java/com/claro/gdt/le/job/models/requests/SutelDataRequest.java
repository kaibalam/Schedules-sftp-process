package com.claro.gdt.le.job.models.requests;

import java.time.LocalDate;

public class SutelDataRequest {
    private String fileName;
    private String fileType;
    private LocalDate fileDate;

    public SutelDataRequest() {
    }

    public SutelDataRequest(String fileName, String fileType, LocalDate fileDate) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileDate = fileDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDate getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDate fileDate) {
        this.fileDate = fileDate;
    }
}
