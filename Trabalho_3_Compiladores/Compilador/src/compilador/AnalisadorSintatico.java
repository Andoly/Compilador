/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;
import tabelas.Gramatica;
import tabelas.TabelaErros;
import tabelas.TabelaSintatica;
import tabelas.TabelaSimbolos;
import tipo.Sentenca;
import tipo.Simbolo;

public class AnalisadorSintatico {
    public AnalisadorLexico al;
    public Gramatica gr;
    public TabelaSintatica ts;
    public TabelaErros te;
    public TabelaSimbolos tds;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String DIRETORIO = "./arquivos/";
    public static final String ARQUIVOTEXTO = "PROGRAMA.c";
    public PrintWriter writer;
    public String cabecalho;
    
    public AnalisadorSintatico() throws FileNotFoundException, IOException {
        al = new AnalisadorLexico();
        gr = new Gramatica();
        ts = new TabelaSintatica();
        te = new TabelaErros();
        
        writer = new PrintWriter(DIRETORIO+ARQUIVOTEXTO, "UTF-8");
        System.out.println("Arquivo encontrado para gravacao dos dados");
        cabecalho = "#include<stdio.h>\r\n#include<stdlib.h>\r\ntypedef char literal[256];\r\nint main(){\r\n";
        System.out.println(ANSI_RED + cabecalho + ANSI_RED);
        writer.print(cabecalho);
    }
    
    public void sintatico() throws IOException{
        Simbolo a = null;
        Stack<Integer> pilhaSintatica = new Stack<>();
        Stack<Simbolo> pilhaSemantica = new Stack<>();
        
        int gotoEstado = 0;
        int estadoDeShiftGoto = 0;
        Simbolo tokenAtual = null;
        Sentenca s = null;
        String acao = "";
        int estado = 0;
        boolean erro = false;
        int Tx = 0;
        
        //Recebe um token do Analisador Lexico
        while(true){
            a = al.lexico();
            tokenAtual = a;
            if(a.getLexema() != "ERRO" && a.getLexema() != "null"){
                break;
            }
        }
        //System.out.println(ANSI_RED + "Simbolo atual: " + a.getLexema() + " | " + a.getToken() + " | " + (a.getTipo()== "" ? "vazio" : a.getTipo()) );
        //pilhaSemantica.push(a);
        //Inicializa o algoritmo empilhando o estado inicial
        pilhaSintatica.push(0);
        while(true){
            estado = pilhaSintatica.peek();
            acao = this.ts.t[estado][this.ts.retornaColuna(a.getToken())];
            
            if(isShift(acao)){
                pilhaSintatica.push(this.extrairEstado(acao));
                pilhaSemantica.push(a);
                //System.out.println(Arrays.toString(pilhaSemantica.toArray()));
                while(true){
                    if(erro){
                        a = tokenAtual;
                        erro = false;
                        break;
                    }
                    a = al.lexico();
                    tokenAtual = a;
                    if(!a.getToken().matches("ERRO") && !a.getToken().matches("null")){
                        break;
                    } else if(a.getToken().matches("ERRO")){
                        tratamentoDeErro(al.getLinha(),al.getColuna(),te.getErro(al.getEstado()),"lexico");
                        return;
                    }
                }
            } else if(isReduce(acao)){
                s = gr.sentencas.get(this.extrairEstado(acao));
                for(int i = 0; i < s.getTamanho(); i++){
                    pilhaSintatica.pop();
                }
                estado = pilhaSintatica.peek();
                gotoEstado = Integer.parseInt(this.ts.t[estado][this.ts.retornaColuna(s.getA())]);
                estadoDeShiftGoto = gotoEstado;
                pilhaSintatica.push(gotoEstado);
                System.out.println(s.getA() + "->" + s.getB());
                
                Simbolo tipo = null;
                Simbolo literal = null;
                Simbolo id = null;
                Simbolo ARG = null;
                Simbolo rcb = null;
                Simbolo ld = null;
                Simbolo oprd = null;
                Simbolo oprd2 = null;
                Simbolo opr = null;
                Simbolo num = null;
                Simbolo exp_r = null;
                
                String id_tipo = "";
                String tp = "";
                
                switch(this.extrairEstado(acao)){
                    
                    case 5:
                        writer.print("\r\n\r\n\r\n");
                    break;
                    case 6:
                        pilhaSemantica.pop();
                        tipo = this.al.ts.tabelaSimbolos.get(pilhaSemantica.pop().getLexema());
                        id_tipo = tipo.getTipo();
                        id = this.al.ts.tabelaSimbolos.get(pilhaSemantica.peek().getLexema());
                        id.setTipo(id_tipo);
                        this.al.ts.tabelaSimbolos.replace(pilhaSemantica.peek().getLexema(),id);
                        System.out.println(ANSI_RED + tipo.getTipo() + " " + id.getLexema() + ";");
                        writer.print(tipo.getTipo() + " " + id.getLexema() + ";" + "\r\n");
                        pilhaSemantica.pop();
                    break;
                    case 7:
                        literal = this.al.ts.tabelaSimbolos.get(pilhaSemantica.peek().getLexema());
                        tipo = new Simbolo("tipo","tipo",literal.getTipo());
                        pilhaSemantica.pop();
                        pilhaSemantica.push(tipo);
                        this.al.ts.tabelaSimbolos.put(tipo.getLexema(), tipo);
                    break;
                    case 8:
                        literal = this.al.ts.tabelaSimbolos.get(pilhaSemantica.peek().getLexema());
                        tipo = new Simbolo("tipo","tipo",literal.getTipo());
                        pilhaSemantica.pop();
                        pilhaSemantica.push(tipo);
                        this.al.ts.tabelaSimbolos.put(tipo.getLexema(), tipo);
                    break;
                    case 9:
                        literal = this.al.ts.tabelaSimbolos.get(pilhaSemantica.peek().getLexema());
                        tipo = new Simbolo("tipo","tipo",literal.getTipo());
                        pilhaSemantica.pop();
                        pilhaSemantica.push(tipo);
                        this.al.ts.tabelaSimbolos.put(tipo.getLexema(), tipo);
                    break;
                    case 11:
                        pilhaSemantica.pop();
                        id = this.al.ts.tabelaSimbolos.get(pilhaSemantica.peek().getLexema());
                        id_tipo = this.al.ts.tabelaSimbolos.get(pilhaSemantica.pop().getLexema()).getTipo();
                        
                        if(id_tipo.matches("literal")){
                            System.out.println(ANSI_RED + "scanf(\"%s\", " + id.getLexema() + ");" + "\r\n");
                            writer.print("scanf(\"%s\", " + id.getLexema() + ");" + "\r\n");
                        } else if(id_tipo.matches("int")){
                            System.out.println(ANSI_RED + "scanf(\"%d\", &" + id.getLexema() + ");" + "\r\n");
                            writer.print("scanf(\"%d\", &" + id.getLexema() + ");" + "\r\n");
                        } else if(id_tipo.matches("double")){
                            System.out.println(ANSI_RED + "scanf(\"%lf\", &" + id.getLexema() + ");" + "\r\n");
                            writer.print("scanf(\"%lf\", &" + id.getLexema() + ");" + "\r\n");
                        } else {
                            this.tratamentoDeErro(al.getLinha(), al.getColuna(), te.getErroSemantico(0), "semantico");
                        }
                    break;
                    case 12:
                        ARG = this.al.ts.tabelaSimbolos.get("ARG");
                        if(ARG.getTipo().matches("int")){
                            System.out.println(ANSI_RED + "printf(\"%d\"," + ARG.getLexema() + ");\r\n");
                            writer.print("printf(\"%d\"," + ARG.getLexema() + ");\r\n");
                        } else if(ARG.getTipo().matches("double")){
                            System.out.println(ANSI_RED + "printf(\"%lf\"," + ARG.getLexema() + ");\r\n");
                            writer.print("printf(\"%lf\"," + ARG.getLexema() + ");\r\n");
                        } else if(ARG.getTipo().matches("literal")){
                            System.out.println(ANSI_RED + "printf(\"%s\"," + ARG.getLexema() + ");\r\n");
                            writer.print("printf(\"%s\"," + ARG.getLexema() + ");\r\n");
                        }
                        writer.print("\r\n");
                    break;
                    case 13:
                        ARG = new Simbolo(pilhaSemantica.pop().getLexema(),"lit","literal");
                        this.al.ts.tabelaSimbolos.replace("ARG",ARG);
                        pilhaSemantica.push(ARG);
                    break;
                    case 14:
                        id = this.al.ts.tabelaSimbolos.get(pilhaSemantica.pop().getLexema());
                        ARG = id;
                        this.al.ts.tabelaSimbolos.replace("ARG",ARG);
                        pilhaSemantica.push(ARG);
                        
                    break;
                    case 15:
                        if(this.al.ts.tabelaSimbolos.containsKey(pilhaSemantica.peek().getLexema())){
                            id = this.al.ts.tabelaSimbolos.get(pilhaSemantica.pop().getLexema());
                            ARG = id;
                            this.al.ts.tabelaSimbolos.replace("ARG",ARG);
                            pilhaSemantica.push(ARG);
                        } else {
                            this.tratamentoDeErro(al.getLinha(), al.getColuna(), this.te.getErroSemantico(1), "semantico");
                        }
                    break;
                    case 17:
                        pilhaSemantica.pop();
                        ld = pilhaSemantica.pop();
                        opr = pilhaSemantica.pop();
                        id = pilhaSemantica.pop();
                        System.out.println("Tipo id: " + id.getTipo() + " " + "Tipo ld: " + ld.getTipo());
                        if(this.al.ts.tabelaSimbolos.containsKey(id.getLexema())){    
                            if(this.operadoresDoMesmoTipo(id, ld)){
                                System.out.println(ANSI_RED + id.getLexema() + opr.getTipo() + ld.getLexema() + ";");
                                writer.print(id.getLexema() + opr.getTipo() + ld.getLexema() + ";\r\n");
                            } else {
                                this.tratamentoDeErro(al.getLinha(), al.getColuna() , this.te.getErroSemantico(1), "semantico");
                            }
                        } else {
                            this.tratamentoDeErro(al.getLinha(), al.getColuna() , this.te.getErroSemantico(2), "semantico");
                        }
                    break;
                    case 18:
                        oprd2 = pilhaSemantica.pop();
                        opr = pilhaSemantica.pop();
                        oprd = pilhaSemantica.pop();
                        if(this.operadoresSaoEquivalentes(oprd, oprd2)){
                            tp = "int";
                            if(oprd.getTipo().matches("double")|| oprd2.getTipo().matches("double")){
                                tp = "double";
                            }
                            ld = new Simbolo("T"+Integer.toString(Tx),null,tp);
                            pilhaSemantica.push(ld);
                            System.out.println(ANSI_RED + ld.getLexema() + " = " + oprd.getLexema() + " " + opr.getLexema() + " " + oprd2.getLexema());
                            writer.print(ld.getLexema() + " = " + oprd.getLexema() + " " + opr.getLexema() + " " + oprd2.getLexema() + ";\r\n");
                            
                            Tx++;
                        }
                    break;
                    case 19:
                        oprd = pilhaSemantica.pop();
                        ld = oprd;
                        pilhaSemantica.push(oprd);
                    break;
                    case 20:
                        id = pilhaSemantica.pop();
                        if(this.al.ts.tabelaSimbolos.containsKey(id.getLexema())){
                            oprd = id;
                            pilhaSemantica.push(oprd);
                            
                        } else {
                            this.tratamentoDeErro(al.getLinha(), al.getColuna(), this.te.getErroSemantico(1), "semantico");
                        }
                    break;
                    case 21:
                        num = pilhaSemantica.pop();
                        oprd = num;
                        pilhaSemantica.push(oprd);
                    break;
                    case 23:
                        System.out.println(ANSI_RED + "}\r\n");
                        writer.print("}\r\n");
                    break;
                    case 24:
                        exp_r = this.al.ts.tabelaSimbolos.get("EXP_R");
                        System.out.println(ANSI_RED + "if(" + exp_r.getLexema() + "){\r\n");
                        writer.print("if(" + exp_r.getLexema() + "){\r\n");
                        
                    break;
                    case 25:
                        oprd2 = pilhaSemantica.pop();
                        opr = pilhaSemantica.pop();
                        oprd = pilhaSemantica.pop();
                        if(this.operadoresSaoEquivalentes(oprd, oprd2)){
                            tp = "int";
                            if(oprd.getTipo().matches("double") || oprd2.getTipo().matches("double")){
                                tp = "double";
                            }
                            exp_r = new Simbolo("T"+Integer.toString(Tx),"",tp);
                            this.al.ts.tabelaSimbolos.replace("EXP_R", exp_r);
                            pilhaSemantica.push(exp_r);
                            System.out.println(ANSI_RED + exp_r.getLexema() + " = " + oprd.getLexema() + " " + opr.getLexema() + " " + oprd2.getLexema());
                            writer.print(exp_r.getLexema() + " = " + oprd.getLexema() + " " + opr.getLexema() + " " + oprd2.getLexema() + ";\r\n");
                            
                            Tx++;
                        }
                    break;
                    case 30:
                        writer.print("return 0;\r\n}");
                        
                        writer.close();
                        
                        String variaveisTemporarias = "";
                        for(int i = 0; i < Tx; i++){
                            System.out.println(ANSI_RED + "int T" + i + ";\r\n");
                            variaveisTemporarias += "int T" + i + ";\r\n";
                        }
                        this.inserirTemporarias(this.cabecalho.length(),variaveisTemporarias.getBytes());
                    break;
                    default:
                        
                    break;
                }
            } else if(isAccept(acao)){
                System.out.println("\nAceitacao");
                writer.close();
                break;
            } else {
                int estadoErro = pilhaSintatica.peek();
                System.out.println("\n" + ANSI_RED + "Estado no automato: " + estadoErro + ANSI_RED);
                acao = this.ts.t[estadoErro][this.ts.retornaColuna(a.getToken())];
                String tipoErro = "";
                String msgErro = "";
                tipoErro = te.errosSintaticos.get(this.extrairEstado(acao));
                int est = 0;
                msgErro = te.msgErrosSintaticos.get(this.extrairEstado(acao));
                this.tratamentoDeErro(al.getLinha(),al.getColuna(),msgErro,"sintatico");
                switch(tipoErro){
                    case "PT_V":
                        acao = this.ts.t[estadoErro][this.ts.retornaColuna(tipoErro)];
                        if(this.isShift(acao)){
                            est = this.extrairEstado(acao);
                            System.out.println("Estado empilhaSintaticado: " + est);
                            pilhaSintatica.push(est);
                        } else if(this.isReduce(acao)){
                            a = new Simbolo(";","PT_V","");
                            erro = true;
                        }
                    break;
                    case "FC_P":
                        a = new Simbolo(")","FC_P","");
                        erro = true;
                    break;
                    case "entao":
                        a = new Simbolo("entao","entao","");
                        erro = true;
                    break;
                    case "inicio":
                        a = new Simbolo("inicio","inicio","");
                        erro = true;
                    break;
                    case "varinicio":
                        a = new Simbolo("varinicio","varinicio","");
                        erro = true;
                    break;
                    case "varfim":
                        a = new Simbolo("varfim","varfim","");
                        erro = true;
                    break;
                    default:
                        System.out.println("Estado de Shift Goto: " + estadoDeShiftGoto);
                        System.out.println(Arrays.toString(pilhaSintatica.toArray()));
                        while(pilhaSintatica.peek() != estadoDeShiftGoto){
                            pilhaSintatica.pop();
                        }
                        while(true){
                            if(a.getToken() != "null" && a.getToken() != "ERRO"){
                                acao = this.ts.t[pilhaSintatica.peek()][this.ts.retornaColuna(a.getToken())];
                                if(this.isShift(acao)){
                                    break;
                                } else if(a.getToken() == "EOF"){
                                    System.out.println(ANSI_RED + "Nao foi possivel recuperar do erro" + ANSI_RED);
                                    return;
                                }
                            } else if(a.getToken() == "ERRO"){
                                this.tratamentoDeErro(al.getLinha(), al.getColuna(), te.getErro(al.getEstado()), "lexico");
                                return;
                            } 
                            a = al.lexico();
                            System.out.println("Para sair do erro: " + a.getLexema());
                        }
                    break;
                }
            }
        }
    }
    
    public void tratamentoDeErro(int linha, int coluna, String acao, String tipo){
        System.out.println("\n" + ANSI_RED + "Erro encontrado na linha: " + linha + ", na coluna " + coluna + ANSI_RED);
        if(tipo.matches("lexico")){
            System.out.println(ANSI_RED +"Erro Lexico: " + acao + ANSI_RED);
        }
        if(tipo.matches("sintatico")){
            System.out.println(ANSI_RED + "Erro sintatico: " + acao + ANSI_RED);
        }
        if(tipo.matches("semantico")){
            System.out.println(ANSI_RED + "Erro semantico: " + acao + ANSI_RED);
        }
    }
    
    public boolean isError(String valor){
        return (valor.matches("^E[0-9]+$"));
    }
    
    public boolean isShift(String valor){
        return (valor.matches("^S[0-9]+$"));
    }
    
    public boolean isReduce(String valor){
        return (valor.matches("^R[0-9]+$"));
    }
    
    public boolean isAccept(String valor){
        return (valor.matches("ACC"));
    } 
    
    public int extrairEstado(String valor){
        return Integer.parseInt(valor.replaceAll("\\D+",""));
    }
    
    public void imprimirPilha(Stack<Simbolo> s){
        for(Simbolo a : s){
            System.out.println(ANSI_BLUE + "Lexema: " + a.getLexema() + " | " + "Token: " + " | " + "Tipo: " + a.getTipo());
        }
        System.out.println("\r\n");
    }
    
    public boolean operadoresSaoEquivalentes(Simbolo A, Simbolo B){
        if(A.getTipo().matches("literal") || B.getTipo().matches("literal")){
            return false;
        } else if(A.getTipo().matches("int") || A.getTipo().matches("double")){
            if(B.getTipo().matches("int") || B.getTipo().matches("double")){
                return true;
            }
        }
        return false;
    }
    
    public boolean operadoresDoMesmoTipo(Simbolo A, Simbolo B){
        if(A.getTipo().matches(B.getTipo())){
            return true;
        }
        return false;
    }
    
    String filename = DIRETORIO + ARQUIVOTEXTO;
    int offset = 5;
    byte[] content = ("\t hi").getBytes();

    private void inserirTemporarias(long offset, byte[] content) throws IOException {
        RandomAccessFile r = new RandomAccessFile(DIRETORIO+ARQUIVOTEXTO, "rw");
        RandomAccessFile rtemp = new RandomAccessFile(DIRETORIO+ARQUIVOTEXTO+"Temp", "rw");
        long fileSize = r.length(); 
        FileChannel sourceChannel = r.getChannel();
        FileChannel targetChannel = rtemp.getChannel();
        sourceChannel.transferTo(offset, (fileSize - offset), targetChannel);
        sourceChannel.truncate(offset);
        r.seek(offset);
        r.write(content);
        long newOffset = r.getFilePointer();
        targetChannel.position(0L);
        sourceChannel.transferFrom(targetChannel, newOffset, (fileSize - offset));
        sourceChannel.close();
        targetChannel.close();
        rtemp.close();
        r.close();
    }
    
}
    