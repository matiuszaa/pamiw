package com.example.mwo.app.repository;

import com.example.mwo.app.entity.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends PagingAndSortingRepository<Parking, Integer> {
    Page<Parking> findByCityName(String city, Pageable pageable);
}
