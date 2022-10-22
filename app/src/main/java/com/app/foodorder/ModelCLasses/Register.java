package com.app.foodorder.ModelCLasses;

public class Register {
    String name, email, address, password,id;

    public Register(String name, String email, String address, String password, String id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.id = id;
    }
    public Register(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
