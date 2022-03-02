package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    //양방향 관계를 설정해도 DB에는 영향을 주지 않음
    //아래가 양방향 관계 설정 코드
    //mappedby는 MEMBER 클래스의 변수명
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
    
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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
