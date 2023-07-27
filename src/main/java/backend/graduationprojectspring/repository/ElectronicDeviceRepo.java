package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.ElectronicDevice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ElectronicDeviceRepo extends JpaRepository<ElectronicDevice, Long> {
    boolean existsByName(String name);
}
