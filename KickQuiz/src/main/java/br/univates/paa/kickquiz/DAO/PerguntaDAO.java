/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.DAO;

import br.univates.paa.kickquiz.model.Pergunta;

/**
 *
 * @author Avell G1310 MAX
 */
public class PerguntaDAO extends ModelDAO<Pergunta> {

    @Override
    public Class getObject() {
        return Pergunta.class;
    }
}
