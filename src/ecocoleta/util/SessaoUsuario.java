package ecocoleta.util;

import ecocoleta.model.Usuario;

public class SessaoUsuario {

    private static Usuario usuarioLogado;

    public static void login(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static void logout() {
        usuarioLogado = null;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static boolean estaLogado() {
        return usuarioLogado != null;
    }
}