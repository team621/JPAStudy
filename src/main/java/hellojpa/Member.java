package hellojpa;

import javax.persistence.*;
import java.util.Date;

//↓ 필수 (JPA에 사용되는 클래스인걸 표시, JPA가 관리)
//name 지정 가능, 지정안하면 클래스명을 사용
@Entity(name="Member")
//↓ 테이블 명을 지정 가능 (지정안하면 클래스명과 동일한 테이블과 매핑됨)
public class Member {

    //PK 값 설정
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //@Column(name="TEAM_ID")
    //private Long teamid;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

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
    }
}
