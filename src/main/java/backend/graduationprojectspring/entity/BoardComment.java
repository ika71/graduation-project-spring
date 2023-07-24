package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class BoardComment extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
