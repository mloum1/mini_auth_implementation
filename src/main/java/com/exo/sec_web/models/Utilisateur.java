package com.exo.sec_web.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String nomUtilisateur;

    @Column(name = "password", nullable = false)
    private String motDePasse;

    @OneToMany
    @MapKey(name = "name")
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Map<String, UtilisateurRole> roles = new HashMap<>();

}
