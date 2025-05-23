package com.exo.sec_web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exo.sec_web.models.Utilisateur;
import com.exo.sec_web.models.UtilisateurRole;
import com.exo.sec_web.repositories.UtilisateurRepository;

import jakarta.transaction.Transactional;

@Service
public class UtilisateurService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByNomUtilisateur(username);
        if (utilisateur.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        Utilisateur user = utilisateur.get();
        user.getRoles().size();
        UserDetails userDetails = new User(user.getNomUtilisateur(), user.getMotDePasse(),
                construireRoleAutoriser(user.getRoles()));
        return userDetails;
    }

    private List<GrantedAuthority> construireRoleAutoriser(Set<UtilisateurRole> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (UtilisateurRole role : roles) {
        authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return authorities;
}

}
