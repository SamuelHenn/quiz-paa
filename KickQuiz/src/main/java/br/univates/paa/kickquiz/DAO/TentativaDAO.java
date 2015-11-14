/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Tentativa;

/**
 *
 * @author Avell G1310 MAX
 */
public class TentativaDAO extends ModelDAO<Tentativa> {

    @Override
    public Class getObject() {
        return Tentativa.class;
    }
}
