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
public class Board extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ElectronicDevice electronicDevice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Board(String title, String content, ElectronicDevice electronicDevice) {
        this.title = title;
        this.content = content;
        this.electronicDevice = electronicDevice;
    }
}