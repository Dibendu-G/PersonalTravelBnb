package com.travelbnb.service;

import com.travelbnb.entity.AppUserEntity;
import com.travelbnb.entity.FavouriteEntity;
import com.travelbnb.entity.PropertyEntity;
import com.travelbnb.exception.NotFoundException;
import com.travelbnb.payloads.FavouritePayload;
import com.travelbnb.repository.FavouriteRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
