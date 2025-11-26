package com.alsolaarriana.Lab7.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO used for GraphQL Mutations (Creation/Update) as input data.
 * Does not include the ID field for creation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInput {
    private String firstName;
    private String lastName;
    private String email;
    private String course;
}