package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//↓ 필수 (JPA에 사용되는 클래스인걸 표시)
@Entity
//↓ 테이블 명을 지정 가능 (지정안하면 클래스명과 동일한 테이블과 매핑됨)
@Table(name="MEMBER")
public class Member {
    
    //PK 값 설정
    @Id
    private Long id;
    //↓ 컬럼 명을 지정 가능 (지정안하면 변수명과 동일한 컬럼과 매핑됨)
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
