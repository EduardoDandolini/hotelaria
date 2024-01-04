package com.eduardo.hotelaria.repository;

import com.eduardo.hotelaria.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h " +
            "from tb_hotels h " +
            "where h.name = :name")
    List<Hotel> findHotelByName(@Param("name") String name);
}
