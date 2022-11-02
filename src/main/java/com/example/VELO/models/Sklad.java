package com.example.VELO.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Sklad {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 20, message = "Поле должно быть размером от 6 до 20 символов")
    private String adres;


    @OneToMany(mappedBy = "sklad", fetch = FetchType.EAGER)
    private Collection<Tovar> tovars;

    //Методы GET

    public Long getId() {
        return id;
    }

    public String getAdres() {
        return adres;
    }

    public Collection<Tovar> getTovars() {
        return tovars;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setTovars(Collection<Tovar> tovars) {
        this.tovars = tovars;
    }
}
