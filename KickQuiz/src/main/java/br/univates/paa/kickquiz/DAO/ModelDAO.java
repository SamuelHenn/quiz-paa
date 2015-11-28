/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Avell G1310 MAX
 */
public abstract class ModelDAO<T> {

    public Session session = HibernateUtil.getSession();

    public void save(T t) {
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.saveOrUpdate(t);// persist(t);
        session.getTransaction().commit();
    }

    public void delete(T t) {
        session.beginTransaction();
        if (null != t) {
            session.delete(t);
        }
        session.getTransaction().commit();
    }

    public List<T> listAll() {
        session.beginTransaction();
        List<T> personsList = session.createQuery("from " + getObject().getName()).list();
        session.getTransaction().commit();
        return personsList;
    }

    public T getById(int id) {
        session.beginTransaction();
        T t = (T) session.get(getObject(), new Integer(id));
        session.getTransaction().commit();
        return t;
    }

    public abstract Class getObject();
}
