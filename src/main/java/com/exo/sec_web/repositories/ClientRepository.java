package com.exo.sec_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exo.sec_web.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
