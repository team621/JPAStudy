package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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

    private int age;

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

    //기간
    @Embedded
    private Period workPeriod;

    //주소
    @Embedded
    private Address Homeaddress;

    //↓ 값 타입 컬렉션
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();

    //주소
    //한 엔티티에서 같은 값을 사용할 때 사용하는 방법
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city",
                    column=@Column(name = "WORK_CITY")),
            @AttributeOverride(name="street",
                    column=@Column(name = "WORK_STREET")),
            @AttributeOverride(name="zipcode",
                    column=@Column(name = "WORK_ZIPCODE"))
    })
    private Address workddress;
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

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public List<MemberProduct> getMemberProducts() {
        return memberProducts;
    }

    public void setMemberProducts(List<MemberProduct> memberProducts) {
        this.memberProducts = memberProducts;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeaddress() {
        return Homeaddress;
    }

    public void setHomeaddress(Address homeaddress) {
        Homeaddress = homeaddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public Address getWorkddress() {
        return workddress;
    }

    public void setWorkddress(Address workddress) {
        this.workddress = workddress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
