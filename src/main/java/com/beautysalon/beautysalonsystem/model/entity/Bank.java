package com.beautysalon.beautysalonsystem.model.entity;

import com.beautysalon.beautysalonsystem.model.entity.enums.AccountType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "bankEntity")
@Table(name = "bank_tbl")
@RequestScoped
public class Bank extends Base implements Serializable {

    @Id
    @SequenceGenerator(name = "bankSeq", sequenceName = "bank_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankSeq")
    @Column(name = "bank_id", length = 20)
    private Long  id;

    @Column(name = "bank_name", columnDefinition = "NVARCHAR2(20)", nullable = false, unique = true, length = 20)
    @Pattern(regexp = "^[a-zA-zا-ی'\\s]{3,20}$", message = "invalid Name")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @Column(name = "bank_accountNumber", length = 16, nullable = false)
    @Pattern(regexp = "^[0-9]{5,16}$", message = "Invalid Account Number")
    @Size(min = 5, max = 16, message = " Account Number must be between 5 and 16 characters")
    private String accountNumber;

    @Column(name = "bank_branchCode", nullable = false)
    private Long branchCode;

    @Column(name = "bank_branchName",columnDefinition = "NVARCHAR2(20)", nullable = false, length = 20)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,20}$", message = "Invalid Branch Name")
    @Size(min = 3, max = 20, message = "Branch Name must be between 3 and 20 characters")
    @NotBlank(message = "Should Not Be Null")
    private String branchName;

    @Column(name = "bank_accountBalance", nullable = false)
    private Double accountBalance;

    @Column(name = "status")
    private boolean status;
}