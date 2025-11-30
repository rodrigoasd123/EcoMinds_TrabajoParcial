package com.upc.products.security.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String jwt;
    private String username;
    private Set<String> roles;
}