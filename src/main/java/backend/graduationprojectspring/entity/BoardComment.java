package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardComment extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public BoardComment(String comment, Board board, Member member) {
        this.comment = comment;
        this.board = board;
        this.member = member;
    }
}
