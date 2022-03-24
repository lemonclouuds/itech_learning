package com.itech.learning.repository;

import com.itech.learning.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    void deleteByIdIn(Collection<Long> ids);
}
