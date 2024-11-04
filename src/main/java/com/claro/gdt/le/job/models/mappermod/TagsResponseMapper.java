package com.claro.gdt.le.job.models.mappermod;



import com.claro.gdt.le.job.models.responses.TagsValuesResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagsResponseMapper implements RowMapper<TagsValuesResponse> {
    @Override
    public TagsValuesResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        TagsValuesResponse tagsValues = new TagsValuesResponse();
        tagsValues.setTag(rs.getString("DIC_TAG"));
        tagsValues.setValue(rs.getString("DIC_VALOR"));
        return tagsValues;
    }
}
