package com.alsolaarriana.Lab7;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String course; // e.g., "Computer Science"
}