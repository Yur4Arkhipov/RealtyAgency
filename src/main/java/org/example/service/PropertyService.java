package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Property;
import org.example.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    public Optional<Property> findById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }

    public List<Property> findByType(Property.PropertyType type) {
        return propertyRepository.findByPropertyType(type);
    }

    public List<Property> findByStatus(Property.PropertyStatus status) {
        return propertyRepository.findByStatus(status);
    }

    public List<Property> findAvailable() {
        return propertyRepository.findByStatus(Property.PropertyStatus.AVAILABLE);
    }

    public List<Property> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return propertyRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Property> findByAreaRange(Double minArea, Double maxArea) {
        return propertyRepository.findByAreaBetween(minArea, maxArea);
    }

    public List<Property> findByRooms(Integer rooms) {
        return propertyRepository.findByRooms(rooms);
    }

    public List<Property> searchByAddress(String address) {
        return propertyRepository.findByAddressContainingIgnoreCase(address);
    }

    public long count() {
        return propertyRepository.count();
    }

    public long countAvailable() {
        return propertyRepository.findByStatus(Property.PropertyStatus.AVAILABLE).size();
    }
}

