package com.fields.fileds_library.audit;

import lombok.RequiredArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

@RevisionEntity(AuditRevisionListener.class)
@RequiredArgsConstructor
public class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .orElse("InitialUser");
        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
        audit.setUserName(currentUser);
        audit.setModifiedAt(LocalDateTime.now());
    }
}
