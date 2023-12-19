package com.kafka.springrabitmqexample.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "`user`")
public class User extends BaseEntity{
    private String name;
    private String surname;
    private String email;
}
