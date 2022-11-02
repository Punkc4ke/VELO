package com.example.VELO.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Supplier {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 20, message = "Поле должно быть размером от 6 до 20 символов")
    public String name_org;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 11, max = 20, message = "Поле должно быть размером от 11 до 20 символов")
    private String phone;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private Collection<Tovar> tovars;

    //Методы GET

    public Long getId() {
        return id;
    }

    public String getName_org() {
        return name_org;
    }

    public String getPhone() {
        return phone;
    }

    public Collection<Tovar> getTovars() {
        return tovars;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setName_org(String name_org) {
        this.name_org = name_org;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTovars(Collection<Tovar> tovars) {
        this.tovars = tovars;
    }




}
