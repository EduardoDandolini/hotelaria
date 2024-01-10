package com.eduardo.hotelaria.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
public record HotelDTO(Long id,
                       @NotEmpty @NotNull String name,
                       @NotEmpty @NotNull String location,
                       @NotNull Long numberRooms,
                       List<String> amenities) {
}
