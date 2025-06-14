package com.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("account")
public class Account {

    @Id
    @Column("id")
    private Long id;

    @Column("account_id")
    private String accountId;

    @Column("name")
    private String name;

    @Column("age")
    private Integer age;

    @Column("email")
    private String email;
}