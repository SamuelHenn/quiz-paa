/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Usuario;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Avell G1310 MAX
 */
public class UsuarioDAO extends ModelDAO<Usuario> {

    @Override
    public Class getObject() {
        return Usuario.class;
    }

    public Usuario checkLogin(Usuario u) {
        session.beginTransaction();
        Query query = session.createQuery("from Usuario where login = :login and senha = :senha");
        query.setParameter("login", u.getLogin());
        query.setParameter("senha", u.getSenha());
        List list = query.list();
        session.getTransaction().commit();
        return list.size() == 1 ? (Usuario) list.get(0) : null;
    }
}
