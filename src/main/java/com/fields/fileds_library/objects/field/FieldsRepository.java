package com.fields.fileds_library.objects.field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldsRepository extends JpaRepository<Field, UUID> {

    @Query("SELECT f FROM Field f WHERE f.fieldName= :fieldName")
    Optional<Field> findFieldByFieldName(String fieldName);

}
