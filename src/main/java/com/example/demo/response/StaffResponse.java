package com.example.demo.response;

import com.example.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StaffResponse implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Date dateOfBirth;

    private List<Role> role;
}
