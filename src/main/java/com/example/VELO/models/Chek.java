package com.example.VELO.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chek")
public class Chek {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date data_p = new Date();

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "punkt_id", referencedColumnName = "id")
    private Punkt punkt;

    @ManyToMany
    @JoinTable(name="spisok",
            joinColumns=@JoinColumn(name="chek_id"),
            inverseJoinColumns=@JoinColumn(name="tovar_id"))

    private List<Tovar> tovars;

    public Chek(Date date_p, User authUser) {
        this.data_p = date_p;
        this.user = authUser;
    }

    public Chek() {

    }


    //Методы GET

    public Long getId() {
        return id;
    }

    public Date getData_p() {
        return data_p;
    }

    public User getUser() {
        return user;
    }

    public List<Tovar> getTovars() {
        return tovars;
    }

    public Punkt getPunkt() {
        return punkt;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setData_p(Date data_p) {
        this.data_p = data_p;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTovars(List<Tovar> tovars) {
        this.tovars = tovars;
    }

    public void setPunkt(Punkt punkt) {
        this.punkt = punkt;
    }
}
