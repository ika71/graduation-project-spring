package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

/**
 * 이미지
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Image extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String originName; //이미지의 원래 이름

    @Column(nullable = false, unique = true, updatable = false)
    private String storeName; //파일로 저장된 이미지의 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; //게시글에 사용되는 이미지

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electronic_device_id")
    private ElectronicDevice electronicDevice;

    @Column
    private boolean visible = false; //이미지를 노출 할지 말지 결정 여부

    public Image(String originName, String storeName) {
        this.originName = originName;
        this.storeName = storeName;
    }

    public Optional<Board> getBoard() {
        return Optional.ofNullable(this.board);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setElectronicDevice(ElectronicDevice electronicDevice) {
        this.electronicDevice = electronicDevice;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
