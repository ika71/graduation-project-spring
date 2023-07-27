package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.impl.ImageServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageServiceImpl imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> imageView(@PathVariable(name = "id")Long id) throws MalformedURLException {
        String fullPath = imageService.fullPath(id);

        UrlResource urlResource = new UrlResource("file:"+fullPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(urlResource);
    }

    @PostMapping
    public ResponseEntity<?> imageCreate(
            @RequestParam(name = "imageFile") MultipartFile imageFile) {
        Image savedImage = imageService.storeFile(imageFile);
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
