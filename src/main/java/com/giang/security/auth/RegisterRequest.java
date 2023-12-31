package com.giang.security.auth;

import com.giang.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String lastName;

    private String  firstName;

    private String email;

    private String password;

    private Role role;


}
