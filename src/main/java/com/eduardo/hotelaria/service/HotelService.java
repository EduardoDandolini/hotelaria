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
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;
    public List<HotelDTO> getAllHotels() {
        List<Hotel> hoteis = hotelRepository.findAll();
            if (CollectionUtils.isEmpty(hoteis)){
                return hoteis.stream()
                        .map(Hotel::toDTO)
                        .collect(Collectors.toList());
            } else {
                throw new NotFoundException("No hotel found");
            }
    }
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
    @Transactional(rollbackFor = Exception.class)
    public void deleteHotel(Long id){
        if (hotelRepository.existsById(id)){
            hotelRepository.deleteById(id);
        } else {
            throw new NotFoundException("Hotel with id: " + id + " does not exist");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public HotelDTO updateHotel(Long id, HotelDTO dto){
        return hotelRepository.findById(id)
                .map(existingHotel -> {
                    existingHotel.setName(dto.getName());
                    existingHotel.setLocation(dto.getLocation());
                    existingHotel.setNumberRooms(dto.getNumberRooms());
                    existingHotel.setAmenities(dto.getAmenities());
                    return hotelRepository.save(existingHotel).toDTO();
                })
                .orElseThrow(() -> new NotFoundException("Hotel with id: " + id + " not found"));
    }

    private boolean isAdmin(Authentication authentication) {
        return SecurityUtils.hasRole(authentication, Role.ROLE_ADMIN);
    }
}
