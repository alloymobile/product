package com.alloymobile.product.integration.client;

import com.alloymobile.product.persistence.model.Address;
import com.alloymobile.product.persistence.model.Location;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Address toAddress(com.alloymobile.product.integration.client.model.Address address){
        Address dbo = new Address();
        dbo.setId(address.getId());
        dbo.setAddress(address.getAddress());
        dbo.setCity(address.getCity());
        dbo.setCountry(address.getCountry());
        dbo.setState(address.getState());
        dbo.setPostalCode(address.getPostalCode());
        return dbo;
    }

    public Location toLocation(com.alloymobile.product.integration.client.model.Address address){
        Location location = new Location();
        location.setType(address.getLocation().getType());
        location.setCoordinates(address.getLocation().getCoordinates());
        return location;
    }
}
