package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //DataBase 연결 등 기초 셋팅, 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //엔티티 매니저 생성 한 트랜잭션 마다 꼭 생성 소멸 필요
        EntityManager em = emf.createEntityManager();

        //트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //매핑 클래스
        Mapping mapping = new Mapping();

        try{
            //벌크연산, 쿼리 한번으로 여러 테이블 로우 변경하는 방법
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원1");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            int resultCount = em.createQuery("update Member m set m.age = 20").executeUpdate();

            System.out.println("resultCount = " + resultCount);
            //벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
            //벌크 연산 -> 영속성 컨텍스트 초기화


            /*
            //엔티티 직접 사용
            Member member = new Member();
            member.setUsername("hi");
            em.persist(member);

            String query = "select m from Member m where m.id = :memberId";
            //기본키 값을 사용함
            //String query2 = "select count(m) from Member m";

            em.flush();
            em.clear();

            Member singleResult = em.createQuery(query, Member.class).setParameter("memberId", member.getId()).getSingleResult();
            //Member resultList2 = em.createQuery(query2, Member.class).setParameter("memberId", member.getId()).getSingleResult();


            System.out.println("resultList = " + singleResult.getUsername());
            //System.out.println("resultList = " + resultList2)

            //네임드 쿼리, 어플리케이션 실행 시점에 오류를 잡을 수 있음
            List<Member> resultList = em.createNamedQuery("Member.findByUserName", Member.class).setParameter("username", "hi").getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1.getUsername());
            }
*/



            //페치 조인 대상에는 별칭을 줄 수 없다. (가급적 사용  x)
            //컬렉션을 페치 조인하면 페이징 api사용불가능
            //페치 조인이 우선권을 가짐
            //페치 조인은 한번 더 보기
            //다형성
            /*
            String query = "select i from item i where type(i) in (?,?)";
            String query2 = "select i from item i where treat(i as Book).auther = 'kim'";
            */

            /*
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원1");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            //일반
            String query = "select m from Member m";
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
            
            //for (Member member : resultList) System.out.println("member.getUsername() + member.getTeam().getName() = " + member.getUsername() + "," +member.getTeam().getName());

            em.flush();
            em.clear();

            //패치 조인
            String query2 = "select m from Member m join fetch m.team";
            List<Member> resultList1 = em.createQuery(query2, Member.class).getResultList();

            //for(Member member : resultList1) System.out.println("member.getUsername() + member.getTeam().getName() = " + member.getUsername() + "," +member.getTeam().getName());

            //컬렉션 패치 조인
            String query3 = "select t from Team t join fetch t.members";
            List<Team> resultList2 = em.createQuery(query3, Team.class).getResultList();

            //두개가 중복됨
            /*
            for(Team team : resultList2) {
                System.out.print("member.getUsername() + member.getTeam().getName() = " + team.getName() + "|" + team.getMembers().size());
                for (Member member : team.getMembers()) System.out.print(" - member = " + member.getUsername() + " ");
                System.out.println();
            }
            */
            //중복 제거, sql 이후로 jpa 어플리케이션에서 중복을 제거 (원래 sql에서는 제거되는 로우가 아님_모두 같지 않기 때문)
            /*
            String query4 = "select distinct t from Team t join fetch t.members";
            List<Team> resultList3 = em.createQuery(query4, Team.class).getResultList();

            for(Team team : resultList3) {
                System.out.print("member.getUsername() + member.getTeam().getName() = " + team.getName() + "|" + team.getMembers().size());
                for (Member member : team.getMembers()) System.out.print(" - member = " + member.getUsername() + " ");
                System.out.println();
            }
            */
            //패치 조인 ☆☆☆☆
            /*
            //경로 표현식
            Member member = new Member();
            member.setUsername("!111");
            member.setAge(10);
            member.setType(MemberType.USER);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.team from Member m";
            String query2 = "select m from Team t join members m";

            //실무에선 묵시적 조인 사용 x , 명시적 조인 추천
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();

            for (Team s : resultList) System.out.println("s = " + s);
            */
            /*
            Member member = new Member();
            member.setUsername(null);
            member.setAge(10);
            member.setType(MemberType.USER);

            em.persist(member);

            String query = "select m.username, 'HEELO', TRUE from Member m"+
                            " where m.type = :type";
            List<Object[]> resultList = em.createQuery(query).setParameter("type", MemberType.ADMIN).getResultList();

            for(Object[] objects : resultList){
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }

            String query2 = "select " +
                                "case when m.age <= 10 then '학생요금' " +
                                "when m.age >= 60 then '경로요금' " +
                                "else '일반요금' " +
                                "end " +
                            "from Member m";
            List<String> resultList1 = em.createQuery(query2, String.class).getResultList();

            for(String s : resultList1) System.out.println("s = " + s);

            String query3 = "select coalesce(m.username, '이름 없는 회원') as username from Member m";
            List<String> resultList2 = em.createQuery(query3, String.class).getResultList();

            for(String s : resultList2) System.out.println("s = " + s);

            String query4 = "select function(group_concat, m.username) from Member m";

           //List<String> resultList3 = em.createQuery(query4).getResultList();
            //for (String s : resultList3) System.out.println("s = " + s);
*/

            /*
            //페이징
            //setFirstResult / setMaxResults
            for(int i=0; i< 100; i++) {
                Member member = new Member();
                member.setUsername("member1");
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class).setFirstResult(0).setMaxResults(10).getResultList();

            System.out.println("resultList.size() = " + resultList.size());

            for (Member member1 : resultList) System.out.println("member1 = " + member1.getAge());

            //조인

            Team team = new Team();
            team.setName("tem");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("uu");
            member1.setAge(10);
            member1.setTeam(team);

            em.persist(member1);

            //String query = "select m from Member m inner join m.team t";
            //String query = "select m from Member m left outer join m.team t";
            String query = "select m from Member m, team t";
            TypedQuery<Member> query1 = em.createQuery(query, Member.class);
            System.out.println("query1 = " + query1.getResultList());

            //서브쿼리
            //WHERE 절, HAVING절, SELECT절에서만 사용가능함, FROM 불가능
            */

            /*
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember.getId());
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            */
            /*
            Member findMember = em.getReference(Member.class, member1.getId());
            System.out.println("findMember.getClass() = " + findMember.getClass());
            //↑ class hellojpa.Member$HibernateProxy$4w5rWSxP <<<<-- 가짜 엔티티 객체(프록시)
            System.out.println("findMember.getId() = " + findMember.getId());
            //↑ 여기까진 DB에 셀렉트 쿼리를 날리지 않음
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            //↑ 이미 프록시객체에 값이 있기 때문에 쿼리를 날리지 않음
            */
            /*
            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.find(Member.class, member2.getId());
            Member m3 = em.getReference(Member.class, member3.getId());

            System.out.println("(member1.getClass() == member2.getClass()) = " + (m1.getClass() == m2.getClass())); //true
            System.out.println("(member1.getClass() == member2.getClass()) = " + (m1.getClass() == m3.getClass())); //false
            System.out.println("(m1 instanceof Member) = " + (m1 instanceof Member)); //true
            System.out.println("(m1 instanceof Member) = " + (m3 instanceof Member)); //true
/*
            //프로젝션(SELECT)
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(2);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("SELECT m FROM Member as m", Member.class);
            List<Member> resultList = query.getResultList();

            //아래는 비추천 (조인으로인해 성능 저하 가능성), 직접 조인을 해서 사용할 것(명시할 것), 이후에 학습 예정
            TypedQuery<Team> query1 = em.createQuery("SELECT distinct m.team FROM Member as m", Team.class);
            List<Team> resultList1 = query1.getResultList();

            Member member1 = resultList.get(0);
            System.out.println("========================================================");
            //생성자를 통해서 호출
            List<MemberDTO> resultList2 = em.createQuery("SELECT new jpql.MemberDTO(m.username, m.age) FROM Member as m", MemberDTO.class).getResultList();

            MemberDTO memberDTO = resultList2.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
*/


            //JPQL
            //typequery vs query
/*
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("SELECT m FROM Member as m where m.id = 10", Member.class);
            TypedQuery<String> query1 = em.createQuery("SELECT m.username FROM Member as m", String.class);
            Query query2 = em.createQuery("SELECT m.username, m.id FROM Member as m");
            TypedQuery<Member> query3 = em.createQuery("SELECT m FROM Member as m where m.username = :username", Member.class);
            query3.setParameter("username", "member1");

            System.out.println("==========================================================");
            List<Member> resultList1 = query3.getResultList();
            System.out.println("==========================================================");
            System.out.println("singleResult1 = " + resultList1.get(0).getUsername());
            System.out.println("==========================================================");

            //getResultList 결과가 없으면 빈 리스트 반환 (null exception 고려  안해도 됨)
            //getSingleResult 결과가 하나가 아니면 무조건 exception 발생, 없으면 null, 두개 이상이어도 익셉션
            List<Member> resultList = query.getResultList();
            //Member singleResult = query.getSingleResult();

            for(Member member1 : resultList) System.out.println("member1 = " + member1);
*/


            /*
            //JPQL
            List<Member> result = (List<Member>) em.createQuery(
                    "select m from Member m where m.username like '%kim'", Member.class
            ).getResultList();

            //Criteria (동적 쿼리 사용하기 편함), 잘안씀, 유지보수 어려움, 복잡함
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> where = query.select(m).where(cb.equal(m.get("username"), "kim"));

            List<Member> resultList = em.createQuery(where).getResultList();

            //QueryDSL 문자가 아닌 자바코드로 jpql을 작성 가능, 컴파일 시점에 오류 발견 가능, 동적쿼리 작성이 편리, 실무 사용 권장, 셋팅을 해아함

            //네이티브 SQL 기본 SQL을 날리는 것
            em.createNativeQuery("select id, city, street from member").getResultList();
            */
            /*
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeaddress(new Address("homeCity1", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street", "10000"));
            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            System.out.println("============================");

            List<Address> addressHistory = findMember.getAddressHistory();

            for(Address address : addressHistory){
                System.out.println("addressHistory = " + address.getCity());
            }

            //이건 안됨
            findMember.getHomeaddress().setCity("HOMETOWN");

            //이대로 해야 업데이트
            Address add = findMember.getHomeaddress();
            findMember.setHomeaddress(new Address("newCity", add.getStreet(), add.getZipcode()));
            
            //치킨을 한식으로 변경
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
            findMember.getAddressHistory().add(new Address("new1", "street", "10000"));
            */
            /*
            //임베디드 타입
            Member member = new Member();
            member.setUsername("Hell");

            Address address = new Address();
            address.setCity("ANSAN");
            member.setHomeaddress(address);
            
            em.persist(member);
             */
            /*
            //영속성 전이(CASCADE), 고아객체
            Parent parent = new Parent();
            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);
            parent.addChild(child2);

            //원래 방식
            /*
            em.persist(parent);
            em.persist(child1);
            em.persist(child2);

            //영속성 전이 설정 후
            em.persist(parent);

            //고아객체
            em.flush();
            em.clear();
            
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            */
            /*
            //즉시로딩(EAGER) 지연로딩(LAZY)
            Member member1 = new Member();
            member1.setUsername("MEMBER1");
            Team team = new Team();

            team.setName("teamA");
            member1.setTeam(team);

            em.persist(team);
            em.persist(member1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());

            System.out.println("findMember.getClass() = " + findMember.getTeam().getClass());

            System.out.println("============================");
            findMember.getTeam().getName();
            System.out.println("============================");

            */

            //프록시
            /*
            Member member1 = new Member();
            member1.setUsername("HELLO1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("HELLO2");
            em.persist(member2);

            Member member3 = new Member();
            member2.setUsername("HELLO3");
            em.persist(member3);

            em.flush();
            em.clear();
            */
            /*
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember.getId());
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            */
            /*
            Member findMember = em.getReference(Member.class, member1.getId());
            System.out.println("findMember.getClass() = " + findMember.getClass());
            //↑ class hellojpa.Member$HibernateProxy$4w5rWSxP <<<<-- 가짜 엔티티 객체(프록시)
            System.out.println("findMember.getId() = " + findMember.getId());
            //↑ 여기까진 DB에 셀렉트 쿼리를 날리지 않음
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            //↑ 이미 프록시객체에 값이 있기 때문에 쿼리를 날리지 않음
            */
            /*
            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.find(Member.class, member2.getId());
            Member m3 = em.getReference(Member.class, member3.getId());

            System.out.println("(member1.getClass() == member2.getClass()) = " + (m1.getClass() == m2.getClass())); //true
            System.out.println("(member1.getClass() == member2.getClass()) = " + (m1.getClass() == m3.getClass())); //false
            System.out.println("(m1 instanceof Member) = " + (m1 instanceof Member)); //true
            System.out.println("(m1 instanceof Member) = " + (m3 instanceof Member)); //true
            */
            /*
            Member m1 = em.find(Member.class, member1.getId());
            System.out.println("m1.getClass() = " + m1.getClass());
            */
            /*
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("refe.getClass() = " + reference.getClass());
            //System.out.println("(m1==refe) = " + (m1 == reference));

            //프록시 초기화 여부
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(reference) = " + emf.getPersistenceUnitUtil().isLoaded(reference));
            Hibernate.initialize(reference); //강제 초기화
            reference.getUsername();
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(reference) = " + emf.getPersistenceUnitUtil().isLoaded(reference));
            */
            //트랜잭션 커밋
            tx.commit();
        }catch (Exception e){
            //트랜잭션 롤백
            tx.rollback();
            e.printStackTrace();
        }finally {
            //엔티티 매니저 종료
            em.close();
        }

        //엔티티 매니저 팩토리 종료
        emf.close();
    }

    //Insert
    public static void persis(EntityManager em){
        Member member = new Member();
        member.setId(3L);
        member.setUsername("HelloC");
        em.persist(member);
    }

    //Select
    public static void find(EntityManager em){
        Member findMmeber = em.find(Member.class, 2L);
        System.out.println("findMmeber.getId() : " + findMmeber.getId());
        System.out.println("findMmeber.getName() : " + findMmeber.getUsername());
    }

    //Delete
    public static void remove(EntityManager em){
        Member findMmeber = em.find(Member.class, 2L);
        em.remove(findMmeber);
    }

    //Update
    public static void update(EntityManager em){
        Member findMmeber = em.find(Member.class, 1L);
        findMmeber.setUsername("HelloJPA");
    }

    //JPQL
    public static void jpql(EntityManager em){
        List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
        for(Member member : result) System.out.println("Member : " + member.getUsername());
    }

    //영속성
    public static void persistance(EntityManager em){
        //비영속 상태
        Member member = new Member();
        member.setId(101L);
        member.setUsername("HelloJPA");

        //영속 상태, 1차 캐시에 올림
        System.out.println("=== Before ===");
        em.persist(member);
        System.out.println("=== After ===");

        Member findMember = em.find(Member.class, 101L);
        //SELECT QUERY가 날아가지 않음 (1차 캐시에서 가져옴)
        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.name = " + findMember.getUsername());
    }

    //영속성2, 동일성
    public static void persistance2(EntityManager em){
        //SELECT QUERY가 한번만 날아감, 1번에서 1차캐시에 올림
        Member findMember1 = em.find(Member.class, 101L);
        Member findMember2 = em.find(Member.class, 101L);

        System.out.println("result : " + (findMember1 == findMember2));
    }

    //쓰기 지연
    public static void delay(EntityManager em){
        //Member member1 = new Member(150L, "A");
        //Member member2 = new Member(160L, "B");

        //em.persist(member1);
        //em.persist(member2);
        //이후로 쿼리가 날아감, ↑ 여기서 1차캐시에 insert 쿼리가 올라감
        System.out.println("====================");
    }

    //변경감지
    public static void dirtyCheking(EntityManager em){
        Member member = em.find(Member.class, 150L);
        member.setUsername("ZZZZZZ");
        System.out.println("========================");
    }

    //준영속
    public static void semiPersitence(EntityManager em){
        Member member = em.find(Member.class, 150L);
        member.setUsername("AAAAA");

        em.detach(member); //특정 엔티티만 준영속 상태로 만듦
        em.clear(); //모든 엔티티를 준영속 상태로 만듦 (영속성 컨텍스트를 초기화)
        em.close(); //영속성 컨텍스트를 닫으면 다 날아가기 때문
        System.out.println("========================");
        //select 쿼리만 날아감, update 쿼리가 날아가지 않음 (1차 캐시에서 제거)
    }
}

