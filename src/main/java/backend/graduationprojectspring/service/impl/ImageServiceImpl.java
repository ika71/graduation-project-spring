package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.exception.ImageStoreFailException;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.ImageRepo;
import backend.graduationprojectspring.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Value("${file.dir}")
    private String fileDir; //저장될 폴더 위치
    private final ImageRepo imageRepo;

    /**
     * 파일 저장
     * @param multipartFileList 저장할 파일
     * @return 데이터베이스에 저장한 Image
     * @throws ImageStoreFailException 파일 저장 과정 중에 IOException 발생 시 예외 던짐
     */
    @Override
    @Transactional
    public List<Image> storeFileList(List<MultipartFile> multipartFileList) {
        List<Image> imageList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            String originName = multipartFile.getOriginalFilename();
            String storeName = createStoreName(originName);

            try {
                multipartFile.transferTo(new File(storePath(storeName)));
            } catch (IOException e) {
                throw new ImageStoreFailException("이미지 저장에 실패 하였습니다.", e);
            }
            imageList.add(new Image(originName, storeName));
        }
        return imageRepo.saveAll(imageList);
    }

    /**
     * id에 해당하는 이미지의 저장 경로를 반환
     * @param id 저장 경로를 확인할 이미지의 id
     * @return 이미지의 저장 경로
     * @throws NotExistsException 해당하는 이미지가 없으면 발생
     */
    @Override
    @Transactional
    public String fullPath(Long id){
        Image image = imageRepo.findById(id)
                .orElseThrow(() -> new NotExistsException("해당하는 이미지가 없습니다."));
        if(!image.isVisible()){
            throw new NotExistsException("해당하는 이미지가 없습니다.");
        }
        return storePath(image.getStoreName());
    }
    /**
     * 파일의 저장 이름 생성<br>
     * @param originName 저장할 파일의 이름
     * @return UUID + .확장자
     */
    private String createStoreName(String originName){
        String uuid = UUID.randomUUID().toString();
        String extension = fileNameExtension(originName);
        return uuid + "." + extension;
    }

    /**
     * 파일의 확장자명 추출
     * @param originName 확장자명을 추출할 파일이름
     * @return 추출한 확장자명
     */
    private String fileNameExtension(String originName){
        int lastDotPosition = originName.lastIndexOf(".");
        return originName.substring(lastDotPosition + 1);
    }

    /**
     * 저장될 경로
     * @param storeName 저장할 파일 이름
     * @return 저장될 경로 반환
     */
    private String storePath(String storeName){
        return fileDir + "/" + storeName;
    }
}
