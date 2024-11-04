package com.claro.gdt.le.job.services;

import com.claro.gdt.le.job.models.SGTException;
import org.springframework.stereotype.Service;

@Service
public interface SutelDataService {

    Integer getNewValidation(String file, String type) throws SGTException;

    Integer saveNewFile(String fileName, String type) throws SGTException;
}
