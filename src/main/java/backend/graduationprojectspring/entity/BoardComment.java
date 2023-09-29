package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 게시글의 댓글
 */
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardComment extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment; //댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board; //댓글이 달린 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; //댓글을 작성한 유저

    public BoardComment(String comment, Board board, Member member) {
        this.comment = comment;
        this.board = board;
        this.member = member;
    }
}
