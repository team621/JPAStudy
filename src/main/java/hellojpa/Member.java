package hellojpa;

import javax.persistence.*;
import java.util.Date;

//↓ 필수 (JPA에 사용되는 클래스인걸 표시, JPA가 관리)
//name 지정 가능, 지정안하면 클래스명을 사용
@Entity(name="Member")
//↓ 테이블 명을 지정 가능 (지정안하면 클래스명과 동일한 테이블과 매핑됨)
@Table(name="MEMBER")
public class Member {
    
    //PK 값 설정
    @Id
    private Long id;

    @Column(name = "name")
    private String username;

    private Integer age;

    //ENUMTYPE에서 ORDINAL을 사용하면 (DEFAULT) 위험_순서이기때문에 변경되면 DB와 안맞음
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;
    
    //DB와 연동하지 않을 때 사용
    @Transient
    private int temp;

    public Member(){}

    public Member(Long id, String username){
        this.id = id;
        this.username = username;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
