package com.example.thanh.model;

public class User {
    private int age;
    private int height;
    private int weight;
    private int workingLevel;
    private String avtId;
    private String coverId;
    private int wallet;
    private boolean isBan;
    private int status;
    private int lastActive;
    private String bankAccount;
    private String bankName;
    private int _id;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private String phonenumber;
    private String address;
    private int __v;

    // Constructors
    public User() {
    }

    public User(int age, int height, int weight, int workingLevel, String avtId, String coverId, int wallet,
                boolean isBan, int status, int lastActive, String bankAccount, String bankName, int _id,
                String firstname, String lastname, String gender, String email, String phonenumber,
                String address, int __v) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.workingLevel = workingLevel;
        this.avtId = avtId;
        this.coverId = coverId;
        this.wallet = wallet;
        this.isBan = isBan;
        this.status = status;
        this.lastActive = lastActive;
        this.bankAccount = bankAccount;
        this.bankName = bankName;
        this._id = _id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.__v = __v;
    }

    // Getters and Setters

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWorkingLevel() {
        return workingLevel;
    }

    public void setWorkingLevel(int workingLevel) {
        this.workingLevel = workingLevel;
    }

    public String getAvtId() {
        return avtId;
    }

    public void setAvtId(String avtId) {
        this.avtId = avtId;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLastActive() {
        return lastActive;
    }

    public void setLastActive(int lastActive) {
        this.lastActive = lastActive;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
