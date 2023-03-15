package com.example.EnterpriseResourcePlanningTESTS.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
public class Protocol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(length = 100, nullable = false)
    private String name;


    @NotNull
    @Min(1)
    @Max(7)
    private Integer hours;


    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    LocalDate dateOfCreate = LocalDate.now();

    private String description;


    Calendar calendar = Calendar.getInstance();
    int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);



    public Protocol(Long id, String name, Integer hours, LocalDate dateOfCreate, Calendar calendar) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.dateOfCreate = dateOfCreate;
        this.calendar = calendar;
    }

    public Protocol() {
    }


    public Protocol(Long id, String name, Integer hours, Customer customer, User user, LocalDate dateOfCreate, String description, Calendar calendar, int weekOfYear) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.customer = customer;
        this.user = user;
        this.dateOfCreate = dateOfCreate;
        this.description = description;
        this.calendar = calendar;
        this.weekOfYear = weekOfYear;
    }


    public Protocol(String name, User user, String description) {
        this.name = name;
        this.user = user;
        this.description = description;
    }

    public Protocol(Long id, String name, Integer hours, User user) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(LocalDate dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public void setTotalHours(int itemTotalHours) {
    }
}
