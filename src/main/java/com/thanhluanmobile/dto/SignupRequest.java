package com.thanhluanmobile.dto;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
public class SignupRequest {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private String address;
    private Date dateOfBirth;
    private Set<String> roles;
}