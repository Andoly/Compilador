package tabelas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class TabelaTransicao {
    public  int[][] afd;
    public Map<Character,Integer> simbolos;
    public Map<Integer,String> tokens;
    
    public TabelaTransicao(){
       
       //D L " { } ( ) ; < > + - * / = . E _ EOF \n \t ' '
       this.simbolos = new HashMap<Character,Integer>();
       this.simbolos.put('D', 0);
       this.simbolos.put('L', 1);
       this.simbolos.put('"', 2);
       this.simbolos.put('{', 3);
       this.simbolos.put('}', 4);
       this.simbolos.put('(', 5);
       this.simbolos.put(')', 6);
       this.simbolos.put(';', 7);
       this.simbolos.put('<', 8);
       this.simbolos.put('>', 9);
       this.simbolos.put('+', 10);
       this.simbolos.put('-', 11);
       this.simbolos.put('*', 12);
       this.simbolos.put('/', 13);
       this.simbolos.put('=', 14);
       this.simbolos.put('.', 15);
       this.simbolos.put('E', 16);
       this.simbolos.put('_', 18);
       this.simbolos.put('%', 19); //<-EOF
       this.simbolos.put('\n', 20);
       this.simbolos.put('\t', 21);
       this.simbolos.put(' ', 22);
       this.simbolos.put('@', 23);//Loop
       this.simbolos.put('!', 24);//Erro
       
       this.tokens = new HashMap<Integer,String>();
       this.tokens.put(1, "num");
       this.tokens.put(2, "num");
       this.tokens.put(5, "num");
       this.tokens.put(6, "PT_V");
       this.tokens.put(8, "literal");
       this.tokens.put(10, "Comentario");
       this.tokens.put(11, "id");
       this.tokens.put(12, "OPR");
       this.tokens.put(13, "OPR");
       this.tokens.put(14, "OPR");
       this.tokens.put(15, "OPR");
       this.tokens.put(16, "OPR");
       this.tokens.put(17, "OPM");
       this.tokens.put(18, "AB_P");
       this.tokens.put(19, "FC_P");
       this.tokens.put(20, "RCB");
       this.tokens.put(21, "ERRO");
       
       this.afd = new int[23][25];
       for(int i = 0; i < 23; i++ ){
           for(int j = 0; j < 25; j++){
               afd[i][j] = -1;
           }
       }
       
       this.afd[0][0] = 1;
       this.afd[0][1] = 11;
       this.afd[0][2] = 7;
       this.afd[0][3] = 9;
       this.afd[0][5] = 18;
       this.afd[0][6] = 19;
       this.afd[0][7] = 6;
       this.afd[0][8] = 12;
       this.afd[0][9] = 14;
       this.afd[0][10] = 17;
       this.afd[0][11] = 17;
       this.afd[0][12] = 17;
       this.afd[0][13] = 17;
       this.afd[0][14] = 16;
       this.afd[0][16] = 7;
       this.afd[0][20] = 0;
       this.afd[0][21] = 0;
       this.afd[0][22] = 0;
       
       this.afd[1][0] = 1;
       this.afd[1][15] = 2;
       this.afd[1][16] = 3;
       
       this.afd[2][0] = 2;
       this.afd[2][16] = 3;
       
       this.afd[3][10] = 4;
       this.afd[3][11] = 4;
       this.afd[3][0] = 5;
       
       this.afd[4][0] = 5;
       
       this.afd[5][0] = 5;
       
       this.afd[7][2] = 8;
       this.afd[7][23] = 7;
       
       this.afd[9][4] = 10;
       this.afd[9][23] = 9;
       
       this.afd[11][0] = 11;
       this.afd[11][1] = 11;
       this.afd[11][18] = 11;
       
       this.afd[12][9] = 13;
       this.afd[12][14] = 13;
       this.afd[12][11] = 20;
       
       this.afd[14][14] = 15;
    }
    
    public boolean isEstadoFinal(int estado){
        boolean[] estados = {
           false,
           true,
           true,
           false,
           false,
           true,
           true,
           false,
           true,
           false,
           true,
           true,
           true,
           true,
           true,
           true,
           true,
           true,
           true,
           true,
           true,
           true,
           true,
           true
        };
        return (estados[estado]);
    }
    
    public int retornaColuna(int estadoAtual, char caracterAtual){
        if((estadoAtual == 7 || estadoAtual == 9)){
            if(estadoAtual == 7 && caracterAtual == '"'){
                return this.simbolos.get(caracterAtual);
            }
            if(estadoAtual == 9 && caracterAtual == '}'){
                return this.simbolos.get(caracterAtual);
            }
            if(caracterAtual == '\uffff'){
                return this.simbolos.get('%');
            }else {
                return this.simbolos.get('@');
            }
        }
        else if(Character.isLetter(caracterAtual)){
            if(estadoAtual == 1 || estadoAtual == 2){
                return this.simbolos.get('E');
            }
            return this.simbolos.get('L');
        } else if(Character.isDigit(caracterAtual)){
            return this.simbolos.get('D');
        }
        else if(this.simbolos.containsKey(caracterAtual)){
            return this.simbolos.get(caracterAtual);
        } else { 
            return this.simbolos.get('!');
        }
    }
    
    public int retornaTransicao(int estadoAtual, char caracterAtual){
        return (this.afd[estadoAtual][this.retornaColuna(estadoAtual,caracterAtual)]);
    }
    
}
