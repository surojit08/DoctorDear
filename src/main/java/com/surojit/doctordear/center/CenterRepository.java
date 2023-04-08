package com.surojit.doctordear.center;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long>, JpaSpecificationExecutor<Center> {

    default List<Center> findActiveCenters(String name) {
        return findAll((root, query, cb) -> {
            Predicate withStatus = cb.equal(root.get(Center_.status), Status.A);
            Predicate withName = cb.like(root.get(Center_.name), name); // or use metaclass
            return cb.and(withName, withStatus);
        });
    }
}
