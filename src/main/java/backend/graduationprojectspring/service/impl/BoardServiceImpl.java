package backend.graduationprojectspring.service.impl;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.entity.ElectronicDevice;
import backend.graduationprojectspring.entity.Member;
import backend.graduationprojectspring.exception.NotExistsException;
import backend.graduationprojectspring.repository.BoardRepo;
import backend.graduationprojectspring.repository.ElectronicDeviceRepo;
import backend.graduationprojectspring.repository.MemberRepo;
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
    private final MemberRepo memberRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Board> paging(int page, int size, Long deviceId){
        return boardQueryRepo.paging(page, size, deviceId);
    }
    @Override
    @Transactional(readOnly = true)
    public Long totalCountByDeviceId(Long deviceId){
        return boardQueryRepo.totalCountByDeviceId(deviceId);
    }

    @Override
    public Board findOneDetail(Long id) {
        Board findBoard = boardQueryRepo.findOneDetail(id)
                .orElseThrow(() -> new NotExistsException("해당 게시글이 존재하지 않습니다."));
        findBoard.increaseView();
        return findBoard;
    }

    @Override
    public Board create(String title, String content, Long deviceId, Long memberId){
        ElectronicDevice deviceProxy = deviceRepo.getReferenceById(deviceId);
        Member memberProxy = memberRepo.getReferenceById(memberId);
        Board board = new Board(title, content, deviceProxy, memberProxy);
        return boardRepo.save(board);
    }

    @Override
    public void update(Long boardId, String title, String content, Long requestMemberId) {
        Board findBoard = boardRepo.findById(boardId)
                .orElseThrow(() -> new NotExistsException("해당하는 게시글이 존재하지 않습니다."));
        if(findBoard.getMember().getId().equals(requestMemberId)){
            findBoard.update(title, content);
        }
    }

    @Override
    public void delete(Long boardId, Long requestMemberId) {
        Board findBoard = boardRepo.findById(boardId)
                .orElseThrow(() -> new NotExistsException("해당하는 게시글이 없습니다."));
        if(findBoard.getMember().getId().equals(requestMemberId)){
            boardRepo.deleteById(boardId);
        }
    }
}
