package com.thanhluanmobile.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String customerName;
    private String phone;
    private String email;
    private String address;
    private List<CartItemDto> items;
}