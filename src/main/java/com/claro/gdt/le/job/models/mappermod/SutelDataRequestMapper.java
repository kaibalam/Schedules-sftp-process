package com.claro.gdt.le.job.models.mappermod;

import com.claro.gdt.le.job.models.requests.SutelDataRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SutelDataRequestMapper implements RowMapper<SutelDataRequest> {

    @Override
    public SutelDataRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        SutelDataRequest sutel = new SutelDataRequest();
        sutel.setFileName(rs.getString("NOMBRE_ARCHIVO"));
        sutel.setFileName(rs.getString("TIPO_ARCHIVO"));
        sutel.setFileName(rs.getString("FECHA"));

        return sutel;
    }
}
