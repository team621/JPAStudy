package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//↓ 필수 (JPA에 사용되는 클래스인걸 표시, JPA가 관리)
//name 지정 가능, 지정안하면 클래스명을 사용
@Entity(name="Member")
//↓ 테이블 명을 지정 가능 (지정안하면 클래스명과 동일한 테이블과 매핑됨)
public class Member extends BaseEntity{

    //PK 값 설정
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //@Column(name="TEAM_ID")
    //private Long teamid;

    //지연로딩
    //@ManyToOne(fetch = FetchType.LAZY)
    //즉시로딩
    @ManyToOne(fetch = FetchType.EAGER)
    //insertable, updateable을 false로 설정 시 DB에 인서트, 업데이트가 안됨_읽기 전용
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name="LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name="MEMBER_PRODUCT")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        //team 추가 시 자동으로 자신을 team의 멤버로 추가하도록 set method 변경
        team.getMembers().add(this);
    }
}
