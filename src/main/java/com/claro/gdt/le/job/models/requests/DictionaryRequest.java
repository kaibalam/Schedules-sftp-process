package com.claro.gdt.le.job.models.requests;

import java.util.Date;

public class DictionaryRequest {
    private int diccionarioId;
    private int idiamId;
    private String dicValor;
    private String dicTag;
    private Date fUltMod;
    private int uUltMod;
    private Date fAlta;
    private int uAlta;
    private int empresId;

    public DictionaryRequest() {
    }

    public DictionaryRequest(int diccionarioId, int idiamId, String dicValor, String dicTag, Date fUltMod, int uUltMod, Date fAlta, int uAlta, int empresId) {
        this.diccionarioId = diccionarioId;
        this.idiamId = idiamId;
        this.dicValor = dicValor;
        this.dicTag = dicTag;
        this.fUltMod = fUltMod;
        this.uUltMod = uUltMod;
        this.fAlta = fAlta;
        this.uAlta = uAlta;
        this.empresId = empresId;
    }

    public int getDiccionarioId() {
        return diccionarioId;
    }

    public void setDiccionarioId(int diccionarioId) {
        this.diccionarioId = diccionarioId;
    }

    public int getIdiamId() {
        return idiamId;
    }

    public void setIdiamId(int idiamId) {
        this.idiamId = idiamId;
    }

    public String getDicValor() {
        return dicValor;
    }

    public void setDicValor(String dicValor) {
        this.dicValor = dicValor;
    }

    public String getDicTag() {
        return dicTag;
    }

    public void setDicTag(String dicTag) {
        this.dicTag = dicTag;
    }

    public Date getfUltMod() {
        return fUltMod;
    }

    public void setfUltMod(Date fUltMod) {
        this.fUltMod = fUltMod;
    }

    public int getuUltMod() {
        return uUltMod;
    }

    public void setuUltMod(int uUltMod) {
        this.uUltMod = uUltMod;
    }

    public Date getfAlta() {
        return fAlta;
    }

    public void setfAlta(Date fAlta) {
        this.fAlta = fAlta;
    }

    public int getuAlta() {
        return uAlta;
    }

    public void setuAlta(int uAlta) {
        this.uAlta = uAlta;
    }

    public int getEmpresId() {
        return empresId;
    }

    public void setEmpresId(int empresId) {
        this.empresId = empresId;
    }
}
