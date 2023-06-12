package br.angulosistemas;

public enum HttpVerbo {
    METODO_GET      ("GET"),
    METODO_POST     ("POST"),
    METODO_PUT      ("PUT"),
    METODO_DELETE   ("DELETE"),
    METODO_PATCH    ("PATCH"),
    METODO_HEAD     ("HEAD"),
    METODO_CONNECT  ("CONNECT"),
    METODO_OPTIONS  ("OPTIONS"),
    METODO_TRACE    ("TRACE");

    private String nomeVerbo;
    HttpVerbo(String nome) {
        nomeVerbo = nome;
    }

    @Override
    public String toString() {
        return nomeVerbo;
    }
}
