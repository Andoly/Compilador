/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipo;


public class Sentenca {
    public String A;
    public String B;
    public int tamanho;
    public String tipo;

    public Sentenca(String A, String B, int tamanho, String tipo) {
        this.A = A;
        this.B = B;
        this.tamanho = tamanho;
        this.tipo = tipo;
    }

    public String getA() {
        return A;
    }

    public void setA(String A) {
        this.A = A;
    }

    public String getB() {
        return B;
    }

    public void setB(String B) {
        this.B = B;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getTipo() {
        return tipo;
    }
}
