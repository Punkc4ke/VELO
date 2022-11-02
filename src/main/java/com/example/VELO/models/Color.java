package com.example.VELO.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Color {

    // Поля
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 5, max = 30, message = "Поле должно быть размером от 5 до 30 символов")
    public String color_name;

    @OneToMany(mappedBy = "color", fetch = FetchType.EAGER)
    private Collection<Tovar> tovars;


    //Методы GET

    public Long getId() {
        return id;
    }

    public String getColor_name() {
        return color_name;
    }

    public Collection<Tovar> getTovars() {
        return tovars;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public void setTovars(Collection<Tovar> tovars) {
        this.tovars = tovars;
    }

}
