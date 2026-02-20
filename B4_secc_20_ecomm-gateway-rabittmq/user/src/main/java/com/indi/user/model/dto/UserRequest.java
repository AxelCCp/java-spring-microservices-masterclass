package com.indi.user.model.dto;

public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AddressDto address;

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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public AddressDto getAddress() {
        return address;
    }
    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
