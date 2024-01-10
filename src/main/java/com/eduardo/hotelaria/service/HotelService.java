package com.eduardo.hotelaria.service;

import com.eduardo.hotelaria.dto.HotelDTO;
import com.eduardo.hotelaria.entity.Hotel;
import com.eduardo.hotelaria.entity.Role;
import com.eduardo.hotelaria.entity.User;
import com.eduardo.hotelaria.exception.NotFoundException;
import com.eduardo.hotelaria.exception.UnauthorizedAccessException;
import com.eduardo.hotelaria.repository.HotelRepository;
import com.eduardo.hotelaria.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;
    @Transactional(readOnly = true)
    public List<HotelDTO> getAllHotels() {
        List<Hotel> hoteis = hotelRepository.findAll();
            if (!hoteis.isEmpty()){
                return hoteis.stream()
                        .map(Hotel::toDTO)
                        .collect(Collectors.toList());
            } else {
                throw new NotFoundException("No hotel found");
            }
    }
    @Transactional(readOnly = true)
    public Optional<HotelDTO> getHotelByName(@Valid HotelDTO dto) {
        return hotelRepository.findHotelByName(dto.getName())
                .map(Hotel::toDTO);
    }
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HotelDTO createNewHotel(HotelDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAdmin(authentication)){
            Hotel hotel = new Hotel(dto.getName(), dto.getLocation(), dto.getNumberRooms(), dto.getAmenities());
            Hotel saveHotel = hotelRepository.save(hotel);
            return saveHotel.toDTO();
        } else {
            throw new UnauthorizedAccessException("User does not have permission to create a new hotel");
        }
    }
    private boolean isAdmin(Authentication authentication) {
        return SecurityUtils.hasRole(authentication, Role.ROLE_ADMIN);
    }
}
