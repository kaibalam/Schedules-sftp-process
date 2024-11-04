package com.claro.gdt.le.job.services;

import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.models.requests.DictionaryRequest;
import com.claro.gdt.le.job.models.requests.LogRegisterRequest;
import com.claro.gdt.le.job.models.responses.TagsValuesResponse;
import com.jcraft.jsch.JSchException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface IDictionaryDAO {

    List<DictionaryRequest> getAllDictionaryTags() throws SGTException;

    List<TagsValuesResponse> getAllTags() throws SGTException;

    Integer getNewValidation(String file, String type) throws SGTException;

    Integer saveNewFile(String fileName, String type) throws SGTException;

    void mainTask(Map<String, String> mapTags, String filePath) throws SGTException, JSchException, FileNotFoundException;
    boolean saveLog(LogRegisterRequest rq) throws SGTException;


}

