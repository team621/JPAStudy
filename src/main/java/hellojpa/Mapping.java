package hellojpa;

import javax.persistence.EntityManager;

public class Mapping {

    public static void idMapping(EntityManager em){
        Member member = new Member();
        member.setUsername("C");
        em.persist(member);
    }
}