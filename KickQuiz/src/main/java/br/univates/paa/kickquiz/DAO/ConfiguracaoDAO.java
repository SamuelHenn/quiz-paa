/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Configuracao;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Avell G1310 MAX
 */
public class ConfiguracaoDAO extends ModelDAO<Configuracao> {

    @Override
    public Class getObject() {
        return Configuracao.class;
    }

    public Configuracao getByChave(String chave) {
        session.beginTransaction();
        Query query = session.createQuery("from Configuracao where chave = :chave");
        query.setParameter("chave", chave);
        List list = query.list();
        session.getTransaction().commit();
        return list.size() == 1 ? (Configuracao) list.get(0) : null;
    }
}
