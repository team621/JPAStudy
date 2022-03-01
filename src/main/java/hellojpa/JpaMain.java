package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            mapping.juinrel(em);



            //트랜잭션 커밋
            tx.commit();
        }catch (Exception e){
            //트랜잭션 롤백
            tx.rollback();
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

