package com.eduardo.hotelaria.entity;

import com.eduardo.hotelaria.dto.HotelDTO;
import com.eduardo.hotelaria.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tb_hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String location;

    @NotNull
    private Long numberRooms;

    private List<String> amenities;

    public Hotel(String name, String location, Long numberRooms, List<String> amenities) {
        this.name = name;
        this.location = location;
        this.numberRooms = numberRooms;
        this.amenities = amenities;
    }

    public HotelDTO toDTO() {
        return new HotelDTO(id, name, location, numberRooms, amenities);
    }
}
