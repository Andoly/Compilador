package compilador;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

import tabelas.*;
import tipo.Simbolo;

public class AnalisadorLexico {
    private BufferedReader leitor;
    private FileInputStream fis;
    private int linhaAtual;
    private int colunaAtual;
    private int estado;
    private long tamanhoTotalArquivo;
    final static String DIRETORIO = "./arquivos/";
    final static String ARQUIVO = "FILE.ALG";
    private TabelaTransicao tt;
    public TabelaSimbolos ts;
    private TabelaErros te;
    public String[] codigoFonte;
    public int tamanhoCodigo;
    
    AnalisadorLexico() throws FileNotFoundException{
        tt = new TabelaTransicao();
        ts = new TabelaSimbolos();
        te = new TabelaErros();
        
        this.linhaAtual = 0;
        this.colunaAtual = 0;
        this.estado = 0;
        try {
            this.fis = new FileInputStream(DIRETORIO + ARQUIVO);
            this.leitor = new BufferedReader(new InputStreamReader(this.fis, "UTF-8"));
            System.out.println("Arquivo aberto com sucesso !\n");
            fis.getChannel().position(0);
            this.tamanhoTotalArquivo = fis.getChannel().size();
            
            
            String linha = this.leitor.readLine();
            int i = 1;
            while(linha != null){
                i++;
                linha = this.leitor.readLine();
            }
            this.tamanhoCodigo = i;
            
            fis.getChannel().position(0);
            this.codigoFonte = new String[i];
            
            i = 0;
            StringBuilder sb = new StringBuilder();
            linha = this.leitor.readLine();
            sb.append(linha);
            sb.append('\n');
            codigoFonte[i] = sb.toString();
            i = 1;
            while((linha = this.leitor.readLine()) != null){
                sb.setLength(0);
                sb.append(linha);
                sb.append('\n');
                codigoFonte[i] = sb.toString();
                i++;
            }
            this.codigoFonte[i] = "";
            this.codigoFonte[i] = "\uffff";
        } catch(IOException e){
		System.err.printf("Nao foi possivel abrir o arquivo !\n %s.\n", e.getMessage());
        } finally {
            try {
                leitor.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public Simbolo lexico() throws IOException{
        String linhaCodigo = this.codigoFonte[this.linhaAtual];
        this.estado = 0;
        int transicao = 0;
        String lexema = "";
        
        while(true){
            /*
            System.out.println("Linha Atual: " + this.linhaAtual);
            System.out.println("Coluna Atual: " + this.colunaAtual);
            */
            linhaCodigo = this.codigoFonte[this.linhaAtual];
            char caracterAtual = linhaCodigo.charAt(this.colunaAtual);
            if(caracterAtual == '\uffff'){
                if(this.estado == 7 || this.estado == 9){
                    return new Simbolo(te.getErro(this.estado) + "\nLinha:" + (this.linhaAtual+1) + "\nColuna: " + this.colunaAtual + "\n","ERRO","");
                }
                return new Simbolo("EOF","EOF","");
            }
            transicao = tt.retornaTransicao(this.estado, caracterAtual);
            /*
            System.out.println("this.estado Atual: " + this.estado);
            System.out.println("Caracter Atual: " + caracterAtual);
            System.out.println("Proximo this.estado: " + transicao);
            */
            if(transicao < 0){
                if(tt.isEstadoFinal(this.estado)){
                    if(tt.tokens.get(this.estado) == "id"){
                        if(!ts.tabelaSimbolos.containsKey(lexema)){
                            ts.tabelaSimbolos.put(lexema, new Simbolo(lexema,tt.tokens.get(this.estado),""));
                        }
                    }
                    break;
                } else {
                    return new Simbolo(te.getErro(this.estado) + "\nLinha:" + (this.linhaAtual+1) + "\nColuna: " + this.colunaAtual + "\n","ERRO","");
                }
            } else if(transicao == 0){
                lexema = "";
                if(caracterAtual == '\n' || caracterAtual == '\r'){
                    this.colunaAtual = 0;
                    this.linhaAtual++;
                    break;
                }
                else {
                    this.colunaAtual++;
                }
            } else {
                if((this.estado == 7 || this.estado == 9) && caracterAtual == '\n'){
                   lexema += caracterAtual;
                   this.colunaAtual = 0;
                   this.linhaAtual++;
                   this.estado = transicao;
                } else {
                   lexema += caracterAtual;
                   this.estado = transicao;
                   this.colunaAtual++;
                }
            }
        }
        if(lexema == ""){
            return new Simbolo("null","null","null");
        }
        if(ts.tabelaSimbolos.containsKey(lexema)){
            Simbolo s = ts.tabelaSimbolos.get(lexema);
            return new Simbolo(s.getLexema(),s.getToken(),s.getTipo());
        }
        String tipo = "";
        switch(tt.tokens.get(this.estado)){
            case "num":
                if(this.estado == 1){
                    tipo = "int";
                } else if(this.estado == 2 || this.estado == 5){
                    tipo = "double";
                }
            break;
            case "literal":
                tipo = "literal";
            break;
            case "OPM":
            case "OPR":
                tipo = lexema;
            break;
            case "RCB":
                tipo = "=";
            break;
            default:
                tipo = " ";
            break;
        }
        return new Simbolo(lexema,tt.tokens.get(this.estado),tipo);
    }
    
    public int getLinha(){
        return this.linhaAtual;
    }
    
    public int getColuna(){
        return this.colunaAtual;
    }
    
    public int getEstado(){
        return this.estado;
    }
    
    public void setLinha(int linha){
        this.linhaAtual = linha;
    }
    
    public void setColuna(int coluna){
        this.colunaAtual = coluna;
    }
    
}
 