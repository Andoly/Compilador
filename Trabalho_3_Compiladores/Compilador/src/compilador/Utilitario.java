/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;


public class Utilitario {
    public static char ASCIIParaChar(int codigo){
        char c = (char) codigo;
        return c;
    }
    public static String gerarToken(int[] caracteres, int charr) {
        if(caracteres != null){
            String s = null;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < caracteres.length; i++){
                s = Character.toString(Utilitario.ASCIIParaChar(caracteres[i]));
                sb.append(s);
            }
            return sb.toString();
        }
        return "";
    }
}


