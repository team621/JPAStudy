package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Arrays;
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
            */

            //영속성 전이 설정 후
            em.persist(parent);

            //고아객체
            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

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

