/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelas;

import java.util.HashMap;
import java.util.Map;
import tipo.Sentenca;

public class Gramatica {
    public Map<Integer,Sentenca> sentencas;
    public Gramatica(){
        this.sentencas = new HashMap<Integer,Sentenca>();
        this.sentencas.put(1, new Sentenca("P'","P",1," "));
        this.sentencas.put(2, new Sentenca("P","inicio V A",3," "));
        this.sentencas.put(3, new Sentenca("V","varinicio LV",2," "));
        this.sentencas.put(4, new Sentenca("LV","D LV",2," "));
        this.sentencas.put(5, new Sentenca("LV","varfim;",2," "));
        this.sentencas.put(6, new Sentenca("D","id TIPO;",3," "));
        this.sentencas.put(7, new Sentenca("TIPO","int",1," "));
        this.sentencas.put(8, new Sentenca("TIPO","real",1," ")); 
        this.sentencas.put(9, new Sentenca("TIPO","lit",1," "));
        this.sentencas.put(10, new Sentenca("A","ES A",2," "));
        this.sentencas.put(11, new Sentenca("ES","leia id;",3," "));
        this.sentencas.put(12, new Sentenca("ES","escreva ARG;",3," "));
        this.sentencas.put(13, new Sentenca("ARG","literal",1," "));
        this.sentencas.put(14, new Sentenca("ARG","num",1," "));
        this.sentencas.put(15, new Sentenca("ARG","id",1," "));
        this.sentencas.put(16, new Sentenca("A","CMD A",2," "));
        this.sentencas.put(17, new Sentenca("CMD","id rcb LD;",4," "));
        this.sentencas.put(18, new Sentenca("LD","OPRD opm OPRD",3," "));
        this.sentencas.put(19, new Sentenca("LD","OPRD",1," "));
        this.sentencas.put(20, new Sentenca("OPRD","id",1," ")); 
        this.sentencas.put(21, new Sentenca("OPRD","num",1," "));
        this.sentencas.put(22, new Sentenca("A","COND A",2," "));
        this.sentencas.put(23, new Sentenca("COND","CABECALHO CORPO",2," "));
        this.sentencas.put(24, new Sentenca("CABECALHO","se(EXP_R)entao",5," "));
        this.sentencas.put(25, new Sentenca("EXP_R","OPRD opr OPRD",3," "));
        this.sentencas.put(26, new Sentenca("CORPO","ES CORPO",2," "));
        this.sentencas.put(27, new Sentenca("CORPO","CMD CORPO",2," "));
        this.sentencas.put(28, new Sentenca("CORPO","COND CORPO",2," "));
        this.sentencas.put(29, new Sentenca("CORPO","fimse",1," "));
        this.sentencas.put(30, new Sentenca("A","fim",1," "));
    }
}
