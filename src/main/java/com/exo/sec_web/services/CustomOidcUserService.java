package com.exo.sec_web.services;

import com.exo.sec_web.models.Utilisateur;
import com.exo.sec_web.models.UtilisateurRole;
import com.exo.sec_web.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByNomUtilisateur(email);

        Utilisateur utilisateur;
        if (utilisateurOptional.isEmpty()) {
            utilisateur = new Utilisateur();
            utilisateur.setNomUtilisateur(email);
            utilisateur.setMotDePasse(null);
            Set<UtilisateurRole> roles = new HashSet<>();
            UtilisateurRole defaultRole = new UtilisateurRole();
            defaultRole.setName("USER");
            utilisateur.setRoles(roles);
            utilisateurRepository.save(utilisateur);
        } else {
            utilisateur = utilisateurOptional.get();
            utilisateur.setSomeField(oidcUser.getSomeAttribute());
            utilisateurRepository.save(utilisateur);
        }

        Set<UtilisateurRole> dbRoles = utilisateur.getRoles();
        if (dbRoles == null) {
            dbRoles = new HashSet<>();
        }

        if (dbRoles.isEmpty() && utilisateurOptional.isEmpty()) {
            UtilisateurRole tempUserRole = new UtilisateurRole();
            tempUserRole.setName("USER");
            dbRoles.add(tempUserRole);
        }

        List<GrantedAuthority> authorities = dbRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        if (nameAttributeKey == null || nameAttributeKey.isEmpty()) {
            nameAttributeKey = "sub";
        }

        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), nameAttributeKey);
    }
}
