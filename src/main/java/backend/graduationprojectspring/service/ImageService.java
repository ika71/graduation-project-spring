package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Image;
import backend.graduationprojectspring.exception.HttpError;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    /**
     * 파일 저장
     * @param multipartFileList 저장할 파일
     * @return 데이터베이스에 저장한 Image
     * @throws HttpError 파일 저장 과정 중에 IOException 발생 시 예외 던짐
     */
    List<Image> storeFileList(List<MultipartFile> multipartFileList) throws HttpError;

    /**
     * id에 해당하는 이미지의 저장 경로를 반환
     * @param id 저장 경로를 확인할 이미지의 id
     * @return 이미지의 저장 경로
     * @throws HttpError 해당하는 이미지가 없으면 발생 or visible 속성이 false면 발생
     */
    String fullPath(Long id) throws HttpError;
}
