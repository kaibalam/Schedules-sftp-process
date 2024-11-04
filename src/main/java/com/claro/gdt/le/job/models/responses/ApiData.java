package com.claro.gdt.le.job.models.responses;

public class ApiData {

    private String name;
    private Object data;

    public ApiData() {
    }

    public ApiData(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{name=%s, data=%s}", name, data);
    }
}
