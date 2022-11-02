package com.example.VELO.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contacts")
public class Contacts {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 11, max = 20, message = "Поле должно быть размером от 11 до 20 символов")
    private String phone;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 20, message = "Поле должно быть размером от 8 до 20 символов")
    @Email
    private String email;

    @OneToOne(optional = true, mappedBy = "contacts")
    private User user;

    //Методы GET

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    //Методы GET

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
