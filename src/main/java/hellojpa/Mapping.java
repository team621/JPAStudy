package hellojpa;

import javax.persistence.EntityManager;
import java.util.List;

public class Mapping {

    public void idMapping(EntityManager em){
        Member member = new Member();
        member.setUsername("C");
        em.persist(member);
    }

    public void sqlMapping(EntityManager em){
        Team team = new Team();
        team.setName("TEAM A");
        em.persist(team);

        Member member = new Member();
        member.setUsername("MEMBER_1");
        //member.setTeamid(team.getId());
        em.persist(member);

        Member findMember = em.find(Member.class, member.getId());

        Team findTeam = em.find(Team.class, findMember.getTeam());

    }

    public void mapping(EntityManager em){
        Team team = new Team();
        team.setName("TEAM A");
        em.persist(team);

        Member member = new Member();
        member.setUsername("MEMBER_1");
        member.setTeam(team);
        em.persist(member);

        Member findMember = em.find(Member.class, member.getId());

        Team findTeam = findMember.getTeam();
        System.out.println("findTeam : " + findTeam.getName());

    }

    public void juinrel(EntityManager em){
        Team team = new Team();
        team.setName("TEAM A");
        em.persist(team);

        Member member = new Member();
        member.setUsername("MEMBER_1");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());
        List<Member> members = findMember.getTeam().getMembers();

        for(Member m : members){
            System.out.println("m = " + m.getUsername());
        }

    }

}