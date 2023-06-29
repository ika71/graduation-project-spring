package backend.graduationprojectspring.controller.image;

import backend.graduationprojectspring.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageStoreService imageStoreService;

    @PostMapping
    public ResponseEntity<?> imageCreate(
            @RequestParam(name = "imageFile") MultipartFile imageFile) {
        imageStoreService.storeFile(imageFile);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
