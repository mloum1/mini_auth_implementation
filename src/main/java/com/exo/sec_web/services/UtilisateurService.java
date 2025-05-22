package com.exo.sec_web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exo.sec_web.models.Utilisateur;
import com.exo.sec_web.repositories.UtilisateurRepository;

@Service
public class UtilisateurService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByNomUtilisateur(username);
        if (utilisateur.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        Utilisateur user = utilisateur.get();
        UserDetails userDetails = new User(user.getNomUtilisateur(), user.getMotDePasse(), construireRoleAutoriser());
        return userDetails;
    }

    private List<GrantedAuthority> construireRoleAutoriser() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

}
