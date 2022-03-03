package hellojpa;

import javax.persistence.*;
//many to many 사용 시 연결 테이블
@Entity
public class MemberProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int count;
    private int price;


}
