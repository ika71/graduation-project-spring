package backend.graduationprojectspring.repository;

import backend.graduationprojectspring.entity.ElectronicDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectronicDeviceRepo extends JpaRepository<ElectronicDevice, Long> {
    boolean existsByName(String name);
}
