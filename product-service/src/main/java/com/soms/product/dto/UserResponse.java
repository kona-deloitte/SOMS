package com.soms.product.dto;

import lombok.Data;

@Data
public class UserResponse {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String address;
    public boolean isAdmin;
}
