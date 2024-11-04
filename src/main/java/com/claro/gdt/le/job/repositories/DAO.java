package com.claro.gdt.le.job.repositories;

import java.util.List;
import java.util.Optional;

public interface DAO  <T>{

    List<T> list();

    void create(T t);

    Optional<T> get(int id);

    void update(int id);

    void delete(int id);

}
