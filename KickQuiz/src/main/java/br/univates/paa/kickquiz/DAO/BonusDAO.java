/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Bonus;

/**
 *
 * @author Avell G1310 MAX
 */
public class BonusDAO extends ModelDAO<Bonus> {

    @Override
    public Class getObject() {
        return Bonus.class;
    }
}
