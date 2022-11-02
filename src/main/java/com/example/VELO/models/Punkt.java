package com.example.VELO.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
@Table(name = "punkt")
public class Punkt {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 20, message = "Поле должно быть размером от 6 до 40 символов")
    private String adres;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 9, max = 20, message = "Поле должно быть размером от 9 до 20 символов")
    private String work_time;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 20, message = "Поле должно быть размером от 6 до 20 символов")
    private String name;

    @OneToMany(mappedBy = "punkt", fetch = FetchType.EAGER)
    private Collection<Chek> cheks;

    //Методы GET

    public Long getId() {
        return id;
    }

    public String getAdres() {
        return adres;
    }

    public String getWork_time() {
        return work_time;
    }

    public String getName() {
        return name;
    }

    public Collection<Chek> getCheks() {
        return cheks;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCheks(Collection<Chek> cheks) {
        this.cheks = cheks;
    }
}
