/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Pergunta;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Avell G1310 MAX
 */
public class PerguntaDAO {

    private Session session = HibernateUtil.getSession();

    public void save(Pergunta u) {
        session.beginTransaction();
        session.persist(u);
        session.getTransaction().commit();
    }

    public void delete(Pergunta u) {
        session.beginTransaction();
        if (null != u) {
            session.delete(u);
        }
        session.getTransaction().commit();
    }

    public List<Pergunta> listAll() {
        session.beginTransaction();
        List<Pergunta> personsList = session.createQuery("from " + Pergunta.class.getName()).list();
        session.getTransaction().commit();
        return personsList;
    }

    public Pergunta getById(int id) {
        session.beginTransaction();
        Pergunta u = (Pergunta) session.get(Pergunta.class, new Integer(id));
        session.getTransaction().commit();
        return u;
    }

}
