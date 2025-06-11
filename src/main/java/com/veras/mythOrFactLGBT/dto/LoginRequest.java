package com.veras.mythOrFactLGBT.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String username; // Or email, depending on login preference

    @NotBlank
    private String password;

    // Manual getters and setters are removed
}
