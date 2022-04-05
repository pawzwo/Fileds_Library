package com.fields.fileds_library.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class ProtoEntity {

    @Id
    private UUID id = UUID.randomUUID();

    @Version
    private Long version;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtoEntity protoEntity = (ProtoEntity) o;
        return Objects.equals(id, protoEntity.id) && Objects.equals(version, protoEntity.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}