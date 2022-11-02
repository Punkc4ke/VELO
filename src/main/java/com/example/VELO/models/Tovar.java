package com.example.VELO.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tovar")
public class Tovar {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 30, message = "Поле должно быть размером от 6 до 30 символов")
    private String name_tovar;

    @DecimalMin(value = "0.0", message = "Цена товара должна быть больше 0")
    @Positive(message = "Цена товара не может быть отрицательной")
    private Double cena;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Category category;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Color color;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Sklad sklad;

    private String data_p;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Supplier supplier;

    @DecimalMin(value = "0.0", message = "Количество товара должно быть больше 0")
    @Positive(message = "Количество товара не может быть отрицательный")
    private Integer count;

    @OneToMany (mappedBy = "tovar", fetch = FetchType.EAGER)
    private Collection<Comment> comments;

    @ManyToMany
    @JoinTable (name="spisok",
            joinColumns=@JoinColumn (name="tovar_id"),
            inverseJoinColumns=@JoinColumn(name="chek_id"))
    private List<Chek> cheks;

    //Методы GET

    public Long getId() {
        return id;
    }

    public String  getName_tovar() {
        return name_tovar;
    }

    public Double getCena() {
        return cena;
    }

    public Category getCategory() {
        return category;
    }

    public Color getColor() {
        return color;
    }

    public Sklad getSklad() {
        return sklad;
    }

    public String getData_p() {
        return data_p;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Integer getCount() {
        return count;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public List<Chek> getCheks() {
        return cheks;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setName_tovar(String name_tovar) {
        this.name_tovar = name_tovar;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSklad(Sklad sklad) {
        this.sklad = sklad;
    }

    public void setData_p(String data_p) {
        this.data_p = data_p;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public void setCheks(List<Chek> cheks) {
        this.cheks = cheks;
    }

}
