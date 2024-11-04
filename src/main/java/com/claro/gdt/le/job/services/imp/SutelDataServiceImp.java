package com.claro.gdt.le.job.services.imp;




import com.claro.gdt.le.job.models.SGTException;
import com.claro.gdt.le.job.services.SutelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class SutelDataServiceImp implements SutelDataService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public SutelDataServiceImp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer getNewValidation(String file, String type) throws SGTException {
        String sql = String.format("SELECT COUNT(NOMBRE_ARCHIVO) AS VALOR FROM TBL_GDT_ARCHIVOS_SUTEL WHERE NOMBRE_ARCHIVO = '%s' AND TIPO_ARCHIVO = '%s'",file,type);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


    public Integer saveNewFile(String fileName, String type) throws SGTException {
        return jdbcTemplate.update("INSERT INTO TBL_GDT_ARCHIVOS_SUTEL(NOMBRE_ARCHIVO,TIPO_ARCHIVO,FECHA) VALUES(?,?,SYSDATE)",
                fileName,type);
    }


}
