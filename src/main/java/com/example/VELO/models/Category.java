package com.example.VELO.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Category {

    // Поля
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 5, max = 30, message = "Поле должно быть размером от 5 до 30 символов")
    public String category_name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<Tovar> tovars;


    //Методы GET

    public Long getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public Collection<Tovar> getTovars() {
        return tovars;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setTovars(Collection<Tovar> tovars) {
        this.tovars = tovars;
    }

}
