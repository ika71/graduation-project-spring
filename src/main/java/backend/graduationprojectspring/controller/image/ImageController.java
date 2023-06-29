package backend.graduationprojectspring.controller.image;

import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.ImageStoreService;
import lombok.Getter;
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
        Image savedImage = imageStoreService.storeFile(imageFile);
        Long imageId = savedImage.getId();
        String originName = savedImage.getOriginName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ImageCreateResult(imageId, originName));
    }
    @Getter
    private static class ImageCreateResult{
        private final Long imageId;
        private final String originName;

        public ImageCreateResult(Long imageId, String originName) {
            this.imageId = imageId;
            this.originName = originName;
        }
    }
}
