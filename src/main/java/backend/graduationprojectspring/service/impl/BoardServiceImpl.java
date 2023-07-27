package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.exception.NotExistsException;
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
    private final ElectronicDeviceRepo deviceRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Board> paging(int page, int size, Long deviceId){
        return boardQueryRepo.paging(page, size, deviceId);
    }
    @Override
    @Transactional(readOnly = true)
    public Long totalCount(){
        return boardRepo.count();
    }
    @Override
    public Board create(String title, String content, Long deviceId){
        ElectronicDevice device = deviceRepo.getReferenceById(deviceId);
        Board board = new Board(title, content, device);
        return boardRepo.save(board);
    }

    @Override
    public void update(Long boardId, String title, String content) {
        Board findBoard = boardRepo.findById(boardId)
                .orElseThrow(() -> new NotExistsException("해당하는 게시글이 존재하지 않습니다."));
        findBoard.update(title, content);
    }

    @Override
    public void delete(Long boardId) {
        boardRepo.deleteById(boardId);
    }
}
