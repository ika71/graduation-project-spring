package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.repository.BoardRepo;
import backend.graduationprojectspring.repository.ElectronicDeviceRepo;
import backend.graduationprojectspring.repository.query.BoardQueryRepo;
import backend.graduationprojectspring.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepo boardRepo;
    private final BoardQueryRepo boardQueryRepo;
    private final ElectronicDeviceRepo deviceRepository;

    @Transactional(readOnly = true)
    public List<Board> paging(int page, int size, Long deviceId){
        return boardQueryRepo.paging(page, size, deviceId);
    }
    @Transactional(readOnly = true)
    public Long totalCount(){
        return boardRepo.count();
    }
    public Board create(String title, String content, Long deviceId){
        ElectronicDevice device = deviceRepository.getReferenceById(deviceId);
        Board board = new Board(title, content, device);
        return boardRepo.save(board);
    }
}
