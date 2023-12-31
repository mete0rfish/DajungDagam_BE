package com.dajungdagam.dg.repository;


import com.dajungdagam.dg.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaJpaRepository  extends JpaRepository<Area, Integer> {
    Area findByGuNameAndDongName(String guName, String dongName);
}
