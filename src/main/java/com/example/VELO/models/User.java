package com.example.VELO.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    //Поля

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 2, max = 40, message = "Поле должно быть размером от 2 до 40 символов")
    @Column
    private String familia;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 2, max = 40, message = "Поле должно быть размером от 2 до 40 символов")
    @Column
    private String name;

    @Size(min = 2, max = 40, message = "Поле должно быть размером от 2 до 40 символов")
    @Column
    private String otchestvo;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 20, message = "Поле должно быть размером от 6 до 20 символов")
    @Column
    private String username;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Column
    private String password;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="contacts_id")
    private Contacts contacts;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Comment> comments;

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Chek> cheks;

    private boolean active;

    //Методы GET

    public Long getId() {
        return id;
    }

    public String getFamilia() {
        return familia;
    }

    public String getName() {
        return name;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public Collection<Chek> getCheks() {
        return cheks;
    }

    //Методы SET

    public void setId(Long id) {
        this.id = id;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public void setCheks(Collection<Chek> cheks) {
        this.cheks = cheks;
    }

    //Прочее

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

}
