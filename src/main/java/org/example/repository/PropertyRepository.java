package org.example.repository;

import org.example.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByPropertyType(Property.PropertyType propertyType);

    List<Property> findByStatus(Property.PropertyStatus status);

    List<Property> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Property> findByAreaBetween(Double minArea, Double maxArea);

    List<Property> findByRooms(Integer rooms);

    List<Property> findByAddressContainingIgnoreCase(String address);

    List<Property> findByStatusOrderByPriceAsc(Property.PropertyStatus status);

    List<Property> findByStatusOrderByPriceDesc(Property.PropertyStatus status);
}

