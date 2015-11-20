/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Avell G1310 MAX
 */
@Entity
@Table(name = "perguntas")
public class Pergunta implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    private String descricao;
    private int dificuldade;
    @OneToMany(mappedBy = "pergunta", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Resposta> respostas;
    
    public void ordenarRespostas()
    {
        long seed = System.nanoTime();
        Collections.shuffle(respostas, new Random(seed));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public void addResposta(Resposta r) {
        if (this.respostas == null) {
            this.respostas = new ArrayList<Resposta>();
        }
        r.setPergunta(this);
        this.respostas.add(r);
    }

    public Resposta getRespostaCerta() {
        for (Resposta r : respostas)
            if (r.isFl_correta())
                return r;
        
        return null;
    }

}
