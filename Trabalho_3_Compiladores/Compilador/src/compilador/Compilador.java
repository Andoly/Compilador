package compilador;

import java.io.FileNotFoundException;
import java.io.IOException;
import tipo.Simbolo;
import tabelas.TabelaSimbolos;

public class Compilador {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        AnalisadorSintatico as = new AnalisadorSintatico();
        as.sintatico();
        /*
        LEXICO
        AnalisadorLexico al = new AnalisadorLexico();
        Simbolo s = new Simbolo("null","null","null");
        while(s.getToken() != "EOF"){
            s = al.lexico();
            if(s.getToken() != "null" && s.getToken() != "ERRO"){
                System.out.println("LEXEMA: " + s.getLexema() + " TOKEN: " + s.getToken() + " TIPO: " + s.getTipo());
            }
            
        }
        */
    }
}
