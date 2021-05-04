package com.hibernate.example.repository;

import com.hibernate.example.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Transactional
public class ItemRepository {

    private final SessionFactory sessionFactory;

    public ItemRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String save(Item item) {
        return (String) sessionFactory.getCurrentSession().save(item);
    }

    public void update(Item item) {
        sessionFactory.getCurrentSession().merge(item);
    }

    public Item getById(String id) {
        return sessionFactory.getCurrentSession().get(Item.class, id);
    }

    public Item getByIdSql(String id) {
        return sessionFactory.getCurrentSession()
                .createQuery("select i from Item i where i.id = :id", Item.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Item getByIdCriteriaQuery(String id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);

        Root<Item> item = query.from(Item.class);
        query.where(criteriaBuilder.equal(item.get("id"), id));
        return session.createQuery(query).getSingleResult();
    }
}
