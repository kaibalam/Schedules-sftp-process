package com.claro.gdt.le.job.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class JdbcDAO implements DAO{
    private static final Logger LOG = LoggerFactory.getLogger(JdbcDAO.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List list() {
        return null;
    }

    @Override
    public void create(Object o) {

    }

    @Override
    public Optional get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(int id) {

    }

    @Override
    public void delete(int id) {

    }
}
