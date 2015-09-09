/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Usuario;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Avell G1310 MAX
 */
public abstract class ModelDAO<T> {

    private SessionFactory session = HibernateUtil.getSessionFactory();

    public void save(T t) {
        Session session = this.session.getCurrentSession();
        session.persist(t);
    }

    public void delete(T t) {
        Session session = this.session.getCurrentSession();
        if (null != t) {
            session.delete(t);
        }
    }

    /*public List<T> listAll() {
        Session session = this.session.getCurrentSession();
        List<T> personsList = session.createQuery("from " + <T>.class.getName()).list();
                
                (Class<T>)
                
        return personsList;
    }

    public T getById(int id) {
        Session session = this.session.getCurrentSession();
        T u = (T) session.load(Class<T>, new Integer(id));
        return u;
    }*/
}
