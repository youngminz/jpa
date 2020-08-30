package jpabook.model;

import jpabook.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 1001218 on 15. 4. 5..
 */
public class Main {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작

            long orderId = 0;

            {
                Member member = new Member();
                member.setCity("City");
                member.setName("Name");
                member.setStreet("Street");
                member.setZipcode("Zipcode");
                em.persist(member);

                Order order = new Order();
                order.setMember(member);
                order.setOrderDate(new Date());
                order.setStatus(OrderStatus.ORDER);
                em.persist(order);

                Item item = new Item();
                item.setName("물건 이름");
                item.setPrice(100000);
                item.setStockQuantity(10);
                em.persist(item);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setItem(item);
                orderItem.setOrderPrice(99990);
                orderItem.setCount(2);
                em.persist(orderItem);

                System.out.println(member);
                System.out.println(order);
                System.out.println(item);
                System.out.println(orderItem);

                orderId = order.getId();
            }

            {
                Order order = em.find(Order.class, orderId);
                OrderItem orderItem = order.getOrderItems().get(0);
                Item item = orderItem.getItem();

                System.out.println(item);
            }

            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

}
