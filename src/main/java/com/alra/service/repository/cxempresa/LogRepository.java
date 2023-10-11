package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}
