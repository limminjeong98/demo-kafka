package org.example.shipment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final ShipmentCheckOutRepository shipmentCheckOutRepository;

    public Long saveCheckOutData(CheckOutDto checkOutDto) {
        ShipmentCheckOutEntity shipmentCheckOutEntity = modelMapper.map(checkOutDto, ShipmentCheckOutEntity.class);
        ShipmentCheckOutEntity savedEntity = shipmentCheckOutRepository.save(shipmentCheckOutEntity);
        return savedEntity.getShipmentId();
    }
}
