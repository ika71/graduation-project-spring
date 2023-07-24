package backend.graduationprojectspring.service;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.repository.BoardQueryRepository;
import backend.graduationprojectspring.repository.BoardRepository;
import backend.graduationprojectspring.repository.ElectronicDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final ElectronicDeviceRepository deviceRepository;

    @Transactional(readOnly = true)
    public List<Board> paging(int page, int size, Long deviceId){
        return boardQueryRepository.paging(page, size, deviceId);
    }
    @Transactional(readOnly = true)
    public Long totalCount(){
        return boardRepository.count();
    }
    public Board create(String title, String content, Long deviceId){
        ElectronicDevice device = deviceRepository.getReferenceById(deviceId);
        Board board = new Board(title, content, device);
        return boardRepository.save(board);
    }
}
