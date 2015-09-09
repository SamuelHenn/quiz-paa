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
public class UsuarioDAO {

    private SessionFactory session = HibernateUtil.getSessionFactory();

    public void save(Usuario u) {
        Session session = this.session.getCurrentSession();
        session.persist(u);
    }

    public void delete(Usuario u) {
        Session session = this.session.getCurrentSession();
        if (null != u) {
            session.delete(u);
        }
    }

    public List<Usuario> listAll() {
        Session session = this.session.getCurrentSession();
        List<Usuario> personsList = session.createQuery("from " + Usuario.class.getName()).list();
        return personsList;
    }

    public Usuario getById(int id) {
        Session session = this.session.getCurrentSession();
        Usuario u = (Usuario) session.load(Usuario.class, new Integer(id));
        return u;
    }

}
