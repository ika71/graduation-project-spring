package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.exception.ImageStoreFailException;
import backend.graduationprojectspring.exception.NotExistsException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    /**
     * 파일 저장
     * @param multipartFile 저장할 파일
     * @return 데이터베이스에 저장한 Image
     * @throws ImageStoreFailException 파일 저장 과정 중에 IOException 발생 시 예외 던짐
     */
    Image storeFile(MultipartFile multipartFile) throws ImageStoreFailException;

    /**
     * id에 해당하는 이미지의 저장 경로를 반환
     * @param id 저장 경로를 확인할 이미지의 id
     * @return 이미지의 저장 경로
     * @throws NotExistsException 해당하는 이미지가 없으면 발생
     */
    String fullPath(Long id) throws NotExistsException;
}
