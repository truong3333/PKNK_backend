package com.example.pknk.service.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityRequest;
import com.example.pknk.domain.dto.response.clinic.DentalServicesEntityResponse;
import com.example.pknk.domain.entity.clinic.CategoryDental;
import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.CategoryDentalRepository;
import com.example.pknk.repository.clinic.DentalServicesEntityServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DentalServicesEntityService {
        DentalServicesEntityServiceRepository dentalServicesEntityServiceRepository;
        CategoryDentalRepository categoryDentalRepository;

        public DentalServicesEntityResponse createService(String categoryDentalServiceId, DentalServicesEntityRequest request){
            CategoryDental categoryDental = categoryDentalRepository.findById(categoryDentalServiceId).orElseThrow(() -> {
                log.error("Loại dịch vụ: {} không tồn tại, thêm dịch vụ mới thất bại.", request.getName());
                throw new AppException(ErrorCode.CATEGORY_SERVICE_NOT_EXISTED);
            });

            DentalServicesEntity dentalServicesEntity = DentalServicesEntity.builder()
                    .name(request.getName())
                    .unit(request.getUnit())
                    .unitPrice(request.getUnitPrice())
                    .categoryDental(categoryDental)
                    .build();

            categoryDental.getListDentalService().add(dentalServicesEntity);

            dentalServicesEntityServiceRepository.save(dentalServicesEntity);
            log.info("Dịch vụ: {} được thêm thành công.", request.getName());

            return DentalServicesEntityResponse.builder()
                    .id(dentalServicesEntity.getId())
                    .name(request.getName())
                    .unit(request.getUnit())
                    .unitPrice(request.getUnitPrice())
                    .build();
        }

        public DentalServicesEntityResponse updateService(String serviceId, DentalServicesEntityRequest request){
            DentalServicesEntity dentalServicesEntity = dentalServicesEntityServiceRepository.findById(serviceId).orElseThrow(() -> {
                log.error("Dịch vụ id: {} không tồn tại, cập nhật dịch vụ thất bại.", serviceId);
                throw new AppException(ErrorCode.SERVICE_NOT_EXISTED);
            });

            dentalServicesEntity.setName(request.getName());
            dentalServicesEntity.setUnit(request.getUnit());
            dentalServicesEntity.setUnitPrice(request.getUnitPrice());

            dentalServicesEntityServiceRepository.save(dentalServicesEntity);
            log.info("Dịch vụ Id: {} cập nhật thành công.", serviceId);

            return DentalServicesEntityResponse.builder()
                    .id(dentalServicesEntity.getId())
                    .name(request.getName())
                    .unit(request.getUnit())
                    .unitPrice(request.getUnitPrice())
                    .build();
        }

        public List<DentalServicesEntityResponse> getAllService(){
            List<DentalServicesEntity> listService = new ArrayList<>(dentalServicesEntityServiceRepository.findAll());

            return listService.stream().map(dentalServicesEntity -> DentalServicesEntityResponse.builder()
                    .id(dentalServicesEntity.getId())
                    .name(dentalServicesEntity.getName())
                    .unit(dentalServicesEntity.getUnit())
                    .unitPrice(dentalServicesEntity.getUnitPrice())
                    .build()
            ).toList();
        }
}
