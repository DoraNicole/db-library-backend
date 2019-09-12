package com.company.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ImageModel img;

    private boolean isAdmin;

    private boolean isBanned;

    public LocalDate getBanUntil() {
        return banUntil;
    }

    public void setBanUntil(LocalDate banUntil) {
        this.banUntil = banUntil;
    }

    private LocalDate banUntil;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Penalty> penalties = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_genres",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "genre_id")
//    )
    private List<Genre> genres;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImageModel getImg() {
        return img;
    }

    public void setImg(ImageModel img) {
        this.img = img;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Penalty> getPenalties() {
        return penalties;
    }

    public void setPenalties(List<Penalty> penalties) {
        this.penalties = penalties;
    }

    public void addPenalty(Penalty penalty) {
        this.penalties.add(penalty);
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }


    //delete old penalties that are not any more valid
    public void clearOldPenalties() {
        penalties.stream()
                .filter(t -> t.getPenaltyAddedDate().plusMonths(Penalty.numberOfMonthsPenaltyExist).isBefore(LocalDate.now()))
                .forEach(t -> penalties.remove(t));
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
//
//    public Set<Genre> getPreferences() {
//        return preferences;
//    }
//
//    public void setPreferences(Set<Genre> preferences) {
//        this.preferences = preferences;
//    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
