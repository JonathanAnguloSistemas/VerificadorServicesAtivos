package br.angulosistemas;

public enum HttpHeader {
    TEXT_PLAIN          ("text/plain"),
    TEXT_HTML           ("text/html"),
    APPLICATION_JSON    ("application/json"),
    APPLICATION_XML     ("application/xml"),
    IMAGE_JPEG          ("image/jpeg"),
    IMAGE_GIF           ("image/gif");

    private String nomeHeader;
    HttpHeader(String nome){nomeHeader = nome;}

    @Override
    public String toString() { return nomeHeader; }
}
