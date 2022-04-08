package com.fields.fileds_library.entities.field;

import com.fields.fileds_library.model.HCtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {

    @Query("SELECT f FROM Field f JOIN f.hydrocarbons h WHERE (:hydrocarbons IS NULL OR h = :hydrocarbons)")
    List<Field> findAllFields(HCtype hydrocarbons);

    @Query("SELECT f FROM Field f WHERE f.fieldName = :fieldName")
    Optional<Field> findFieldByFieldName(String fieldName);

}
