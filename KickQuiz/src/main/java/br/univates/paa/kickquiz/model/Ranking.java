/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Avell G1310 MAX
 */
@Entity
@Table(name = "ranking_geral")
public class Ranking implements java.io.Serializable {

    @Id
    private int id_usuario;
    private String usuario;
    private String pontos;
    private String perguntas_acertadas;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

    public String getPerguntas_acertadas() {
        return perguntas_acertadas;
    }

    public void setPerguntas_acertadas(String perguntas_acertadas) {
        this.perguntas_acertadas = perguntas_acertadas;
    }

}
