package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.FavouriteEntity;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.exception.NotFoundException;
import com.travelbnb.payloads.AppUserPayload;
import com.travelbnb.payloads.FavouritePayload;
import com.travelbnb.repository.FavouriteRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService{

    private final FavouriteRepository favouriteRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository,
                                PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public FavouritePayload addFavourtie(AppUserEntity user, long propertyId, FavouritePayload favouritePayload) {
        Optional<PropertyEntity> opPropertyId = propertyRepository.findById(propertyId);

        if (opPropertyId.isEmpty()) {
            throw new NotFoundException("Property not found with ID: " + propertyId);
        }

        PropertyEntity property = opPropertyId.get();

        if (user == null) {
            throw new NotFoundException("User not found");
        }
            FavouriteEntity favouriteEntity = payloadToEntity(favouritePayload);
            favouriteEntity.setAppUserEntity(user);
            favouriteEntity.setPropertyEntity(property);

            FavouriteEntity saved = favouriteRepository.save(favouriteEntity);

            return entityToPayload(saved);
    }

    @Override
    public List<FavouritePayload> getAllFavourites(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable;
        if (sortDir.equalsIgnoreCase("ASC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("DESC")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }

        Page<FavouriteEntity> userPage = favouriteRepository.findAll(pageable);
        List<FavouritePayload> favouritePayloads = userPage.getContent().stream()
                .map(this::entityToPayload)
                .collect(Collectors.toList());

        return favouritePayloads;
    }

    // Conversion Payload To Entity
    private FavouriteEntity payloadToEntity(FavouritePayload fpd) {
        FavouriteEntity fre = new FavouriteEntity();
        fre.setStatus(fpd.getStatus());
        fre.setPropertyEntity(fpd.getPropertyEntity());
        fre.setAppUserEntity(fpd.getAppUserEntity());

        return fre;
    }

    // Conversion Entity to Payload
    private FavouritePayload entityToPayload(FavouriteEntity fre) {
        FavouritePayload fpd = new FavouritePayload();
        fpd.setId(fre.getId());
        fpd.setStatus(fre.getStatus());
        fpd.setAppUserEntity(fre.getAppUserEntity());
        fpd.setPropertyEntity(fre.getPropertyEntity());

        return fpd;
    }


}
