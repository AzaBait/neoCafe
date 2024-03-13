package com.neobis.neoCafe.service;

import com.neobis.neoCafe.dto.WarehouseDto;
import com.neobis.neoCafe.entity.Warehouse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WarehouseService {

    Warehouse save(Warehouse warehouse);

    List<WarehouseDto> getAll();

    WarehouseDto getById(Long id);

    ResponseEntity<String> deleteWarehouse(Long id);

    WarehouseDto updateWarehouse(Long id, WarehouseDto warehouseDto);
}
