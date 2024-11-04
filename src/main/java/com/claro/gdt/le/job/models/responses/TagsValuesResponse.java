package com.claro.gdt.le.job.models.responses;

public class TagsValuesResponse {
    private String tag;
    private String value;

    public TagsValuesResponse() {
    }

    public TagsValuesResponse(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{tag=%s,value=%s}",tag,value);
    }
}
