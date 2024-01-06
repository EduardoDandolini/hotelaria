package com.eduardo.hotelaria.repository;

import com.eduardo.hotelaria.entity.Hotel;
import com.eduardo.hotelaria.entity.Reserve;
import com.eduardo.hotelaria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("SELECT reserve " +
            "FROM tb_reserves reserve " +
            "where reserve.hotel.name = :nameHotel")
    List<Hotel> findReserveByNameHotel(@Param("nameHotel") String nameHotel);
    @Query("SELECT reserve " +
            "FROM tb_reserves reserve " +
            "where reserve.hotel.name = :userName")
    List<User> findReserveByUserName(@Param("userName") String userName);
}
