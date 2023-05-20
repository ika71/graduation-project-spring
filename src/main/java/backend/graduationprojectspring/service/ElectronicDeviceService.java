package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Category;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ElectronicDeviceService {
    private final ElectronicDeviceRepository electronicDeviceRepository;
    private final CategoryService categoryService;

    @Transactional
    public ElectronicDevice create(ElectronicDevice electronicDevice, Long categoryId){
        Category category = categoryService.findById(categoryId).orElseThrow();
        electronicDevice.setCategory(category);
        return electronicDeviceRepository.save(electronicDevice);
    }
}
