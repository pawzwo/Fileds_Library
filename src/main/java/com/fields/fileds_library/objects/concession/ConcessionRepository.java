package com.fields.fileds_library.objects.concession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConcessionRepository extends JpaRepository<Concession, UUID> {

//    @Query("SELECT c FROM Concession c JOIN concession_owner co ON c.id = co.concession_id WHERE co.owner_id = :companyName")
//    List<Concession> findAllByConcessionName(String concessionName, String companyName);

}
