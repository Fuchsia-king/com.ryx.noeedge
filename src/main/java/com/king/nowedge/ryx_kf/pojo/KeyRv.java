package com.king.nowedge.ryx_kf.pojo;

public class KeyRv {
    private Long id;

    private String key1;

    private String rkey;

    private Integer type;

    private Integer sort;

    private Integer display;

    private Integer ideleted;

    private String rkey1;

    public KeyRv() {
        this.sort = 1;
        this.type = 1;
        this.display = 1;
        this.ideleted = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1 == null ? null : key1.trim();
    }

    public String getRkey() {
        return rkey;
    }

    public void setRkey(String rkey) {
        this.rkey = rkey == null ? null : rkey.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getIdeleted() {
        return ideleted;
    }

    public void setIdeleted(Integer ideleted) {
        this.ideleted = ideleted;
    }

    public String getRkey1() {
        return rkey1;
    }

    public void setRkey1(String rkey1) {
        this.rkey1 = rkey1 == null ? null : rkey1.trim();
    }
}