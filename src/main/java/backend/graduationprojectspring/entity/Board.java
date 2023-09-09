package backend.graduationprojectspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시글
 */
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
    private String title; //게시글 제목

    @Column(nullable = false)
    @Lob
    private String content; //게시글 내용

    @Column
    private long view; //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    private ElectronicDevice electronicDevice; //게시글과 연결되어 있는 전자제품

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //게시글 작성자

    @OneToMany(mappedBy = "board")
    private List<Image> imageList = new ArrayList<>(); //게시글에 올라간 이미지

    public Board(String title, String content, ElectronicDevice electronicDevice, Member member) {
        this.title = title;
        this.content = content;
        this.electronicDevice = electronicDevice;
        this.member = member;
    }

    /**
     * 제목과 내용을 수정한다.
     * @param title 수정된 후에 제목
     * @param content 수정된 후에 내용
     */
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    /**
     * 조회수를 1 증가시킨다.
     */
    public void increaseView(){
        this.view = view + 1;
    }
}
