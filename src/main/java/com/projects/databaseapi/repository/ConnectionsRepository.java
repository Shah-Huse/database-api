package com.projects.databaseapi.repository;

import com.projects.databaseapi.model.Connections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public interface ConnectionsRepository extends JpaRepository<Connections, Long> {
    @Modifying
    @Query("delete from Connections c where c.id in (:ids)")
    void deleteConnectionsById(@Param("ids") Set<Long> ids);
}
