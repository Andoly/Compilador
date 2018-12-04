/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelas;

import compilador.Utilitario;
import tipo.Simbolo;
import java.util.HashMap;
import java.util.Map;

public class TabelaSimbolos {
    public Map<String, Simbolo> tabelaSimbolos;

    public TabelaSimbolos() {
        this.tabelaSimbolos = new HashMap<String, Simbolo>();
        String[] simbolos = {
                            "inicio",
                            "varinicio",
                            "varfim",
                            "escreva",
                            "leia",
                            "se",
                            "entao",
                            "fimse",
                            "fim",
                            "inteiro",
                            "lit",
                            "real",
                            "TIPO",
                            "ARG",
                            "LD",
                            "OPRD",
                            "RCB",
                            "EXP_R"
                        };
        for(int i = 0; i < simbolos.length; i++){
            String tipo = "";
            if(simbolos[i].matches("inteiro")){
                tipo = "int";
            } else if(simbolos[i].matches("real")){
                tipo = "double";
            } else if(simbolos[i].matches("lit")){
                tipo = "literal";
            } else {
                tipo = " ";
            }
            Simbolo temp = new Simbolo(simbolos[i], simbolos[i], tipo);
            this.tabelaSimbolos.put(simbolos[i], temp);
        }
    }
    
}
