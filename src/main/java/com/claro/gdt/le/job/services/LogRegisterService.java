package com.claro.gdt.le.job.services;




import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.models.requests.LogRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogRegisterService {

    List<LogRegisterRequest> listAllLogs() throws SGTException;

    LogRegisterRequest findById(Integer id) throws SGTException;

    boolean saveLog(LogRegisterRequest request) throws SGTException;
}
