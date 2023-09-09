package backend.graduationprojectspring.controller;

import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.service.ImageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

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
            @RequestParam(name = "imageFile") List<MultipartFile> imageFile) {
        List<Image> savedImageList = imageService.storeFileList(imageFile);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ImageCreateResult(savedImageList));
    }

    @Getter
    @ToString
    public static class ImageCreateResult{
        private final List<ImageCreateDto> imageList;

        public ImageCreateResult(List<Image> imageList) {
            this.imageList = imageList.stream()
                    .map(ImageCreateDto::new)
                    .toList();
        }
    }

    @Getter
    @ToString
    public static class ImageCreateDto {
        private final Long imageId;
        private final String originName;

        public ImageCreateDto(Image image) {
            this.imageId = image.getId();
            this.originName = image.getOriginName();
        }
    }
}
