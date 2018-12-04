/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TabelaSintatica {
    public String[][] t;
    public Map<String,Integer> colunasTabela;
    public static final String DIRETORIO = "./arquivos/";
    public static final String ARQUIVO = "tabela.csv";

    public TabelaSintatica() throws FileNotFoundException, IOException {
        BufferedReader leitor = null;
        t = new String[60][40];
        try {
            colunasTabela = new HashMap<String,Integer>();
            String linha = "";
            leitor = new BufferedReader(new FileReader(DIRETORIO+ARQUIVO));
            //System.out.println("Abri a tabela!");
            int i = 0;
            while ((linha = leitor.readLine()) != null) {
                String a[] = linha.split(",");
                for(int j = 0; j < a.length; j++){
                    if(i == 0){
                        this.colunasTabela.put(a[j],j);
                    } else {
                        //System.out.println(a[j]);
                        this.t[i-1][j] = a[j];
                    }
                }
                i++;
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } finally {
            if(leitor != null){
                try {
                    leitor.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public int retornaColuna(String simbolo){
        return this.colunasTabela.get(simbolo);
    }
    
}
