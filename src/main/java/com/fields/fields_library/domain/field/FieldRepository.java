package com.fields.fields_library.domain.field;

import com.fields.fields_library.model.HCtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {

    @Query("SELECT DISTINCT f FROM Field f JOIN f.hydrocarbons h WHERE (:hydrocarbons IS NULL OR h = :hydrocarbons)")
    List<Field> findAllFields(HCtype hydrocarbons);

    @Query("SELECT f FROM Field f WHERE f.fieldName = :fieldName")
    Optional<Field> findFieldByFieldName(String fieldName);

    @Transactional
    @Modifying
    @Query("UPDATE Field f SET f.reservesOil = :reservesOil, f.reservesGas = :reservesGas, f.reservesCondensate = :reservesCondensate" +
            " WHERE f.fieldName=:fieldName")
    void updateReserves(Double reservesOil, Double reservesGas, Double reservesCondensate, String fieldName);
}
