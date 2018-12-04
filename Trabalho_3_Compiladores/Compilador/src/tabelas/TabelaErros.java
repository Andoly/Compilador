package tabelas;

import java.util.HashMap;
import java.util.Map;

public class TabelaErros {
    public Map<Integer,String> erros;
    public Map<Integer,String> errosSintaticos;
    public Map<Integer,String> errosSemanticos;
    public Map<Integer,String> msgErrosSintaticos;
    public TabelaErros(){
        this.erros = new HashMap<Integer,String>();
        this.erros.put(0, "Caracter inválido");
        this.erros.put(1, "Digito Invalido");
        this.erros.put(3, "Digito Invalido");
        this.erros.put(4, "Digito Invalido");
        this.erros.put(7, "Literal invalido - Aspas nao foram fechadas");
        this.erros.put(9, "Erro de comentario - Chaves nao foram fechadas");
        this.erros.put(11, "Identificador incorreto");
        
        this.errosSintaticos = new HashMap<Integer,String>();
        this.errosSintaticos.put(0,"PT_V");
        this.errosSintaticos.put(1,"AB_P");
        this.errosSintaticos.put(2,"FC_P");
        this.errosSintaticos.put(3,"fimse");
        this.errosSintaticos.put(4,"entao");
        this.errosSintaticos.put(5,"inicio");
        this.errosSintaticos.put(6,"varinicio");
        this.errosSintaticos.put(7,"TIPO");
        this.errosSintaticos.put(8,"Erro");
        this.errosSintaticos.put(9,"Erro");
        this.errosSintaticos.put(10,"varfim");
        this.errosSintaticos.put(11,"Erro");
        this.errosSintaticos.put(12,"Erro");
        this.errosSintaticos.put(13,"Erro");
        this.errosSintaticos.put(14,"Erro");
        
        this.msgErrosSintaticos = new HashMap<Integer,String>();
        this.msgErrosSintaticos.put(0,"Ponto e virgula esperado");
        this.msgErrosSintaticos.put(1,"Abre parenteses ou Operador de atribuicao esperado");
        this.msgErrosSintaticos.put(2,"Fecha parenteses esperado");
        this.msgErrosSintaticos.put(3,"Token fimse esperado");
        this.msgErrosSintaticos.put(4,"Token entao esperado");
        this.msgErrosSintaticos.put(5,"Token inicio esperado");
        this.msgErrosSintaticos.put(6,"Token varinicio esperado");
        this.msgErrosSintaticos.put(7,"Identificador com tipo indefinido");
        this.msgErrosSintaticos.put(8,"Identificador, varinicio ou varfim esperados");
        this.msgErrosSintaticos.put(9,"Tipos de dados, elementos de estrutura condicional (entao e fim), delimitacao de declaracao de variaveis, atribuicao, operadores (relacionais e aritmeticos), inicio de programa e declaracao de variaveis, parenteses ou ponto e virgula nao permitidos");
        this.msgErrosSintaticos.put(10,"Identificador ou token varfim esperados");
        this.msgErrosSintaticos.put(11,"EOF Esperado");
        this.msgErrosSintaticos.put(12,"Ponto e virgula ou operadores esperados");
        this.msgErrosSintaticos.put(13,"Tokens escreva,literal,num ou id esperados");
        this.msgErrosSintaticos.put(14,"Identificador ou numero esperados");
        
        this.errosSemanticos = new HashMap<Integer,String>();
        this.errosSemanticos.put(0, "Tipo do identificador invalido");
        this.errosSemanticos.put(1, "Variavel nao declarada");
        this.errosSemanticos.put(2, "Operadores com tipos incompativeis");
    }
    public String getErro(int estado){
        String erro = erros.get(estado);
        return erro;
    }
    public String getErroSemantico(int estado){
        String erro = errosSemanticos.get(estado);
        return erro;
    }
}
