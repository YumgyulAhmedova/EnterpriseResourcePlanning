package com.example.EnterpriseResourcePlanningTESTS.entities;

import com.example.EnterpriseResourcePlanningTESTS.enums.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(length = 20, nullable = false)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 25)
    @Column(length = 20, nullable = false)
    private String middleName;
    @NotBlank
    @NotBlank
    @Size(min = 2, max = 30)
    @Column(length = 30, nullable = false)
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(length = 30, unique = true, nullable = false)
    private String projectName;

    private Type type;


    @Column(nullable = false)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Protocol> protocols = new ArrayList<>();


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractExpirationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Protocol> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<Protocol> protocols) {
        this.protocols = protocols;
    }

    public Date getContractExpirationDate() {
        return contractExpirationDate;
    }

    public void setContractExpirationDate(Date contractExpirationDate) {
        this.contractExpirationDate = contractExpirationDate;
    }

    public boolean isContractExpired() {
        LocalDate today = LocalDate.now();
        LocalDate expirationDate = contractExpirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return expirationDate.isBefore(today);
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getFullName() {
        return getFirstName() + " " + getMiddleName() + " " + getLastName();
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + firstName + '\'' + middleName + '\'' + lastName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", type=" + type +
                ", protocols=" + protocols +
                ", contractExpirationDate=" + contractExpirationDate +
                '}';
    }
}
