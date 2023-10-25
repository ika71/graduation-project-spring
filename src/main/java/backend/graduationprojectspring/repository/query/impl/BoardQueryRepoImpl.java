package backend.graduationprojectspring.repository.query.impl;

import backend.graduationprojectspring.entity.Board;
import backend.graduationprojectspring.repository.dto.PreviewBoardDto;
import backend.graduationprojectspring.repository.query.BoardQueryRepo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static backend.graduationprojectspring.entity.QBoard.board;
import static backend.graduationprojectspring.entity.QElectronicDevice.electronicDevice;
import static backend.graduationprojectspring.entity.QImage.image;
import static backend.graduationprojectspring.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepoImpl implements BoardQueryRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PreviewBoardDto> paging(int page, int size, Long deviceId){
        return queryFactory
                .select(Projections.constructor(
                        PreviewBoardDto.class,
                        board.id,
                        board.title,
                        board.member.name,
                        board.view,
                        board.createdTime
                ))
                .from(board)
                .join(board.member, member)
                .where(board.electronicDevice.id.eq(deviceId))
                .orderBy(board.id.desc())
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }
    @Override
    public Optional<Board> findOneDetail(Long boardId) {
        Board fetchBoard = queryFactory
                .selectFrom(board)
                .join(board.member, member)
                .fetchJoin()
                .leftJoin(board.imageList, image)
                .fetchJoin()
                .where(board.id.eq(boardId))
                .fetchOne();

        return Optional.ofNullable(fetchBoard);
    }

    @Override
    public Map<Long, Long> countGroupByDevice(List<Long> deviceIdList) {
        List<Tuple> fetchList = queryFactory
                .select(board.electronicDevice.id,
                        board.count())
                .from(board)
                .where(board.electronicDevice.id.in(deviceIdList))
                .groupBy(electronicDevice)
                .fetch();

        //조회된 electronicDeviceId, boardCount를 맵으로 변환한다.
        Map<Long, Long> countBoardGroupByDeviceMap = new HashMap<>(fetchList.size());

        for (Tuple fetch : fetchList) {
            Long deviceId = fetch.get(board.electronicDevice.id);
            Long boardCount = fetch.get(board.count());
            countBoardGroupByDeviceMap.put(deviceId, boardCount);
        }
        //deviceId에 해당 하는 값이 없으면 Map에 0으로 넣는다.
        for (Long deviceId : deviceIdList) {
            countBoardGroupByDeviceMap.putIfAbsent(deviceId, 0L);
        }
        return countBoardGroupByDeviceMap;
    }
}
