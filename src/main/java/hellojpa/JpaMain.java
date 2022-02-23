package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        //DataBase 연결 등 기초 셋팅, 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //엔티티 매니저 생성 한 트랜잭션 마다 꼭 생성 소멸 필요
        EntityManager em = emf.createEntityManager();

        //트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //Insert
            //persis(em);
            //Select
            //find(em);
            //Delete
            //remove(em);
            //Update
            //update(em);

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
        member.setName("HelloC");
        em.persist(member);
    }

    //Select
    public static void find(EntityManager em){
        Member findMmeber = em.find(Member.class, 2L);
        System.out.println("findMmeber.getId() : " + findMmeber.getId());
        System.out.println("findMmeber.getName() : " + findMmeber.getName());
    }

    //Delete
    public static void remove(EntityManager em){
        Member findMmeber = em.find(Member.class, 2L);
        em.remove(findMmeber);
    }

    //Update
    public static void update(EntityManager em){
        Member findMmeber = em.find(Member.class, 1L);
        findMmeber.setName("HelloJPA");
    }


}
