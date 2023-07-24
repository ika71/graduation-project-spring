package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

}
