package com.eduardo.hotelaria.dto;

import com.eduardo.hotelaria.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public record UserDTO(Long id, String name, String password, Set<Role> roles) {

}
