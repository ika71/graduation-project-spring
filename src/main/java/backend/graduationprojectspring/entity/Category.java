package backend.graduationprojectspring.entity;

import backend.graduationprojectspring.entity.Base.Base;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Category extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
