package com.claro.gdt.le.job.models.mappermod;



import com.claro.gdt.le.job.models.requests.DictionaryRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DictionaryMapper implements RowMapper<DictionaryRequest> {
    @Override
    public DictionaryRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        DictionaryRequest dictionary = new DictionaryRequest();
        dictionary.setDiccionarioId(rs.getInt("DICCIONARIO_ID"));
        dictionary.setIdiamId(rs.getInt("IDIOMA_ID"));
        dictionary.setDicValor(rs.getString("DIC_VALOR"));
        dictionary.setDicTag(rs.getString("DIC_TAG"));
        dictionary.setfUltMod(rs.getDate("F_ULT_MOD"));
        dictionary.setuUltMod(rs.getInt("U_ULT_MOD"));
        dictionary.setfAlta(rs.getDate("F_ALTA"));
        dictionary.setuAlta(rs.getInt("U_ALTA"));
        dictionary.setEmpresId(rs.getInt("EMPRESA_ID"));
        return dictionary;
    }
}
