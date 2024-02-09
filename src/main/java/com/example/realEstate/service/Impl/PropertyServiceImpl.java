package com.example.realEstate.service.Impl;

import com.example.realEstate.entity.*;
import com.example.realEstate.entity.enums.ListingType;
import com.example.realEstate.entity.enums.PropertyStatus;
import com.example.realEstate.entity.enums.PropertyType;
import com.example.realEstate.entity.httpdata.PropertyRequest;
import com.example.realEstate.integration.StorageService;
import com.example.realEstate.repository.OwnerRepository;
import com.example.realEstate.repository.PropertyRepository;
import com.example.realEstate.repository.SearchOffersDao;
import com.example.realEstate.service.PropertyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;


@Service
@RequiredArgsConstructor
class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final OwnerRepository ownerRepository;
    private final SearchOffersDao searchOffersDao;
    private final StorageService storageService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addProperty(long ownerId, PropertyRequest propertyRequest) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));
        storageService.save(propertyRequest.getFile());
        Property property = new Property();
        Address address = new Address(propertyRequest.getStreet(), propertyRequest.getCity(), propertyRequest.getState(), propertyRequest.getZipcode());
        PropertyDetails propertyDetails = new PropertyDetails(propertyRequest.isPet(), propertyRequest.getCooling(), propertyRequest.getHeater(), propertyRequest.getDeposit());
        Photos photos = new Photos();
        photos.setLink("http://localhost:8080/api/images/"+propertyRequest.getFile().getOriginalFilename());
        property.setPropertyType(propertyRequest.getPropertyType());
        List<Photos> photosList = new ArrayList<>();
        photosList.add(photos);
        property.setPhotos(photosList);
        property.setPrice(propertyRequest.getPrice());
        property.setBedrooms(propertyRequest.getBedrooms());
        property.setBathrooms(propertyRequest.getBathrooms());
        property.setPropertyStatus(PropertyStatus.AVAILABLE);
        property.setLotSize(propertyRequest.getLotSize());
        property.setBuiltYear(propertyRequest.getBuiltYear());
        property.setListingType(propertyRequest.getListingType());
        property.setAddress(address);
        property.setPropertyDetails(propertyDetails);
        property.setCreatedAt(LocalDateTime.now());
        property.setOwnerId(ownerId);
        owner.getProperties().add(property);
        ownerRepository.save(owner);
    }

    @Override
    public List<Property> getAllPropertiesByOwnerId(long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));
        return owner.getProperties();
    }

    @Override
    public Property getPropertyById(long id) {
        return propertyRepository.findById(id).get();
    }


    @Override
    public Property deletePropertyById(long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Property not found with id: " + id));

        propertyRepository.delete(property);
        return property;
    }



    @Override
    public Property updatePropertyById(long ownerId, long id, PropertyRequest propertyRequest) {
        Property property= propertyRepository.findById(id).get();
        property.setPropertyType(propertyRequest.getPropertyType());
        property.setPrice(propertyRequest.getPrice());
        property.setBedrooms(propertyRequest.getBedrooms());
        property.setBathrooms(propertyRequest.getBathrooms());
        property.setPropertyStatus(PropertyStatus.AVAILABLE);
        property.setLotSize(propertyRequest.getLotSize());
        property.setBuiltYear(propertyRequest.getBuiltYear());
        property.setListingType(propertyRequest.getListingType());
        property.setAddress(propertyRequest.getAddress());
        property.setPropertyDetails(propertyRequest.getPropertyDetails());
        propertyRepository.save(property);
        return property;
    }

    @Override
    public void deleteByIdAndOwnerId(long id, long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Owner not found"));
        Property property = propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("Property not found"));
        if(!owner.getProperties().contains(property)) {
            throw new RuntimeException("Property not found");
        }
        owner.getProperties().remove(property);
        ownerRepository.save(owner);
    }

    @Override
    public List<Offer> searchPropertyCriteria(Long id, Long pptId, String city, String state,
                                              Double price, LocalDateTime submittedAt) {
// TO-DO
// NEED TO BE FILTERED BY OWNER ID AS WELL
// Owner / getOwnerById
// Properties owner.getProperties()
// properties.stream(property.getOffers().
        return searchOffersDao.searchPropertyCriteria(id, pptId, city, state, price,submittedAt);
    }

    @Override
    public Page<Property> findPropertiesByCriteria(
            ListingType listingType, PropertyType propertyType) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> query = builder.createQuery(Property.class);
        Root<Property> root = query.from(Property.class);
        List<Predicate> predicates = buildPredicates(builder, root, listingType, propertyType);

        query.where(predicates.toArray(new Predicate[0]));
        query.select(root);

        List<Property> properties = entityManager.createQuery(query)
                .getResultList();

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Property> countRoot = countQuery.from(Property.class);
        List<Predicate> countPredicates = buildPredicates(builder, countRoot, listingType, propertyType);

        countQuery.where(countPredicates.toArray(new Predicate[0]));
        countQuery.select(builder.count(countRoot));

        Long totalItems = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(properties);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder builder, Root<Property> root, ListingType listingType,
                                            PropertyType propertyType) {

        List<Predicate> predicates = new ArrayList<>();
        if (listingType != null) {
            predicates.add(builder.equal(root.get("listingType"), listingType));
        }
        if (propertyType != null) {
            predicates.add(builder.equal(root.get("propertyType"), propertyType));
        }

        return predicates;
    }

    @Override
    public List<Property> findPropertyStatus() {
        return propertyRepository.findAllByPropertyStatus(PropertyStatus.RENTED);
    }
}