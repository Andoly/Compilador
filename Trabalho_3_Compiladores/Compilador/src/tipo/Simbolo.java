package tipo;

public class Simbolo {
    private String lexema;
    private String token;
    public String tipo;

    public Simbolo(String lexema, String token, String tipo) {
        this.lexema = lexema;
        this.token = token;
        this.tipo = tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexama) {
        this.lexema = lexama;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
