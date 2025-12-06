package com.example.pknk.service.clinic;

import com.example.pknk.domain.dto.request.clinic.CategoryDentalServiceRequest;
import com.example.pknk.domain.dto.response.clinic.CategoryDentalServiceResponse;
import com.example.pknk.domain.dto.response.clinic.DentalServicesEntityResponse;
import com.example.pknk.domain.entity.clinic.CategoryDental;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.CategoryDentalRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CategoryDentalService {
        CategoryDentalRepository categoryDentalRepository;

        @PreAuthorize("hasRole('ADMIN')")
        public CategoryDentalServiceResponse createCategoryDentalService(CategoryDentalServiceRequest request){
                if(categoryDentalRepository.existsByName(request.getName())){
                        log.error("Loại dịch vụ: {} đã tồn tại, thêm thất bại.", request.getName());
                        throw new AppException(ErrorCode.CATEGORY_SERVICE_EXISTED);
                }

                CategoryDental categoryDental = CategoryDental.builder()
                        .name(request.getName())
                        .build();

                categoryDentalRepository.save(categoryDental);
                log.info("Loại dịch vụ: {} được thêm thành công.", request.getName());

                return CategoryDentalServiceResponse.builder()
                        .id(categoryDental.getId())
                        .name(request.getName())
                        .build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        public CategoryDentalServiceResponse updateCategoryDentalService(String categoryId, CategoryDentalServiceRequest request){
                CategoryDental categoryDental = categoryDentalRepository.findById(categoryId).orElseThrow(() -> {
                        log.error("Loại dịch vụ: {} không tồn tại, cập nhật thất bại.", request.getName());
                        throw new AppException(ErrorCode.CATEGORY_SERVICE_NOT_EXISTED);
                });
                
                categoryDental.setName(request.getName());

                categoryDentalRepository.save(categoryDental);
                log.info("Loại dịch vụ: {} được cập nhật thành công.", request.getName());

                return CategoryDentalServiceResponse.builder()
                        .id(categoryId)
                        .name(request.getName())
                        .listDentalServiceEntity(categoryDental.getListDentalService().stream().map(dentalServicesEntity -> DentalServicesEntityResponse.builder()
                                        .id(dentalServicesEntity.getId())
                                        .name(dentalServicesEntity.getName())
                                        .unit(dentalServicesEntity.getUnit())
                                        .unitPrice(dentalServicesEntity.getUnitPrice())
                                        .build()
                                ).toList())
                        .build();
        }

        public List<CategoryDentalServiceResponse> getAllCategoryDentalService(){
                List<CategoryDental> listCategoryService = new ArrayList<>(categoryDentalRepository.findAll());

                return listCategoryService.stream().map(categoryDental -> CategoryDentalServiceResponse.builder()
                                .id(categoryDental.getId())
                                .name(categoryDental.getName())
                                .listDentalServiceEntity(categoryDental.getListDentalService().stream().map(dentalServicesEntity -> DentalServicesEntityResponse.builder()
                                        .id(dentalServicesEntity.getId())
                                        .name(dentalServicesEntity.getName())
                                        .unit(dentalServicesEntity.getUnit())
                                        .unitPrice(dentalServicesEntity.getUnitPrice())
                                        .build()
                                ).toList())
                                .build()
                        ).toList();
        }
}
