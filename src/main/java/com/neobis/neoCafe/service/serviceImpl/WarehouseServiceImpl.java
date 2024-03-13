package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.dto.WarehouseDto;
import com.neobis.neoCafe.entity.Warehouse;
import com.neobis.neoCafe.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {


    @Override
    public Warehouse save(Warehouse warehouse) {
        return null;
    }

    @Override
    public List<WarehouseDto> getAll() {
        return null;
    }

    @Override
    public WarehouseDto getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteWarehouse(Long id) {
        return null;
    }

    @Override
    public WarehouseDto updateWarehouse(Long id, WarehouseDto warehouseDto) {
        return null;
    }
}
