package com.claro.gdt.le.job.models.mappermod;



import com.claro.gdt.le.job.models.requests.LogRegisterRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogRegisterMapper implements RowMapper<LogRegisterRequest> {
    @Override
    public LogRegisterRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        LogRegisterRequest logTable = new LogRegisterRequest();
        logTable.setIdLog(rs.getInt("ID_LOG"));
        logTable.setMsisdn(rs.getString("NUMERO"));
        logTable.setImei(rs.getString("IMEI"));
        logTable.setImsi(rs.getString("IMSI"));
        logTable.setCodCountry(rs.getString("COD_PAIS"));
        logTable.setDateProcess(rs.getDate("FECHA"));
        logTable.setAction(rs.getString("ACCION"));
        logTable.setRequestData(rs.getString("REQUEST"));
        logTable.setResponseData(rs.getString("RESPONSE"));

        return logTable;
    }
}
