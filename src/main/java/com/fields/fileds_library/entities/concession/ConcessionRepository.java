package com.fields.fileds_library.entities.concession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConcessionRepository extends JpaRepository<Concession, UUID> {

    @Query(value = "SELECT c FROM Concession c JOIN c.owners o WHERE (:ownerName IS NULL OR o.companyName= :ownerName)")
    List<Concession> findAllConcessions(String ownerName);

}
