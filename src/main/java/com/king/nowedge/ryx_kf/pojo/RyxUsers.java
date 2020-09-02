package com.king.nowedge.ryx_kf.pojo;

import java.util.Date;

public class RyxUsers {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private Integer regTime;

    private Long regIp;

    private Integer lastLoginTime;

    private Long lastLoginIp;

    private Integer updateTime;

    private Byte status;

    private String path;

    private Integer login;

    private Byte gender;

    private Integer integral;

    private Date birthday;

    private Byte sign;

    private Integer district1;

    private Integer district2;

    private Integer district3;

    private String address;

    private Double balance;

    private Byte authType;

    private String validatecode;

    private String qqOpenId;

    private Byte flag;

    private String province;

    private Integer industry;

    private Date tcreate;

    private Double coupon;

    private Long mid;

    private String name;

    private Double score;

    private String dcode;

    private String dpic1;

    private String fname;

    private Integer scale;

    private Integer otype;

    private Integer industry0;

    private String dpic2;

    private Long sid;

    private String icode;

    private Integer rfrom;

    private String introduction;

    public RyxUsers() {
        this.balance = 0.0;
        this.flag = 1;
        this.coupon = 0.0;
        this.score = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getRegTime() {
        return regTime;
    }

    public void setRegTime(Integer regTime) {
        this.regTime = regTime;
    }

    public Long getRegIp() {
        return regIp;
    }

    public void setRegIp(Long regIp) {
        this.regIp = regIp;
    }

    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(Long lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Byte getSign() {
        return sign;
    }

    public void setSign(Byte sign) {
        this.sign = sign;
    }

    public Integer getDistrict1() {
        return district1;
    }

    public void setDistrict1(Integer district1) {
        this.district1 = district1;
    }

    public Integer getDistrict2() {
        return district2;
    }

    public void setDistrict2(Integer district2) {
        this.district2 = district2;
    }

    public Integer getDistrict3() {
        return district3;
    }

    public void setDistrict3(Integer district3) {
        this.district3 = district3;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Byte getAuthType() {
        return authType;
    }

    public void setAuthType(Byte authType) {
        this.authType = authType;
    }

    public String getValidatecode() {
        return validatecode;
    }

    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode == null ? null : validatecode.trim();
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId == null ? null : qqOpenId.trim();
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public Date getTcreate() {
        return tcreate;
    }

    public void setTcreate(Date tcreate) {
        this.tcreate = tcreate;
    }

    public Double getCoupon() {
        return coupon;
    }

    public void setCoupon(Double coupon) {
        this.coupon = coupon;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode == null ? null : dcode.trim();
    }

    public String getDpic1() {
        return dpic1;
    }

    public void setDpic1(String dpic1) {
        this.dpic1 = dpic1 == null ? null : dpic1.trim();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getOtype() {
        return otype;
    }

    public void setOtype(Integer otype) {
        this.otype = otype;
    }

    public Integer getIndustry0() {
        return industry0;
    }

    public void setIndustry0(Integer industry0) {
        this.industry0 = industry0;
    }

    public String getDpic2() {
        return dpic2;
    }

    public void setDpic2(String dpic2) {
        this.dpic2 = dpic2 == null ? null : dpic2.trim();
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getIcode() {
        return icode;
    }

    public void setIcode(String icode) {
        this.icode = icode == null ? null : icode.trim();
    }

    public Integer getRfrom() {
        return rfrom;
    }

    public void setRfrom(Integer rfrom) {
        this.rfrom = rfrom;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
}