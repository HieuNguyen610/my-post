package com.example.demo.response;

import com.example.demo.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date dateOfBirth;

    private List<Role> role;

    public static StaffResponse fromEntity(com.example.demo.entity.Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .username(staff.getUsername())
                .email(staff.getEmail())
                .phone(staff.getPhone())
                .dateOfBirth(staff.getDateOfBirth())
                .role(staff.getRole())
                .build();
    }
}
