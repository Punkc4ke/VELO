package com.example.VELO.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "comment")
public class Comment {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @DecimalMin(value = "0.0", message = "Рейтинг должен быть больше 0")
    @DecimalMax(value = "6.0", message = "Рейтинг должен быть меньше 5")
    @Positive(message = "Рейтинг не может быть отрицательным")
    private Integer rating;

    @NotBlank(message = "Поле обязательно к заполнению")
    private String full_text;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tovar_id", referencedColumnName = "id")
    private Tovar tovar;

    //Методы GET

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public String getFull_text() {
        return full_text;
    }

    public User getUser() {
        return user;
    }

    public Tovar getTovar() {
        return tovar;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTovar(Tovar tovar) {
        this.tovar = tovar;
    }
}
