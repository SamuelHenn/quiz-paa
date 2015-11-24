/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Permissoes;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Avell G1310 MAX
 */
public class PermissoesDAO extends ModelDAO<Permissoes> {

    @Override
    public Class getObject() {
        return Permissoes.class;
    }

    public boolean temPermissao(Permissoes p) {
        session = HibernateUtil.getSession();
        session.beginTransaction();
        Query query = session.createQuery("from Permissoes where botao = :botao and objeto = :objeto and permissao = :permissao");
        query.setParameter("botao", p.getBotao());
        query.setParameter("objeto", p.getObjeto());
        query.setParameter("permissao", p.getPermissao());
        List list = query.list();
        session.getTransaction().commit();
        return list.size() >= 1;
    }
}
