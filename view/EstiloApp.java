package ecocoleta.view;

public class EstiloApp {

    // Cores principais
    public static final String VERDE_ESCURO  = "#2E7D32";
    public static final String VERDE_MEDIO   = "#388E3C";
    public static final String VERDE_CLARO   = "#4CAF50";
    public static final String VERDE_BG      = "#E8F5E9";
    public static final String BRANCO        = "#FFFFFF";
    public static final String CINZA_TEXTO   = "#616161";
    public static final String CINZA_BORDA   = "#BDBDBD";
    public static final String VERMELHO      = "#D32F2F";

    // Estilos CSS inline reutilizáveis
    public static String botaoPrimario() {
        return "-fx-background-color: " + VERDE_ESCURO + ";"
             + "-fx-text-fill: white;"
             + "-fx-font-size: 14px;"
             + "-fx-font-weight: bold;"
             + "-fx-background-radius: 8;"
             + "-fx-cursor: hand;"
             + "-fx-padding: 10 20;";
    }

    public static String botaoSecundario() {
        return "-fx-background-color: transparent;"
             + "-fx-text-fill: " + VERDE_ESCURO + ";"
             + "-fx-font-size: 13px;"
             + "-fx-border-color: " + VERDE_ESCURO + ";"
             + "-fx-border-radius: 8;"
             + "-fx-background-radius: 8;"
             + "-fx-cursor: hand;"
             + "-fx-padding: 8 16;";
    }

    public static String botaoPerigo() {
        return "-fx-background-color: " + VERMELHO + ";"
             + "-fx-text-fill: white;"
             + "-fx-font-size: 13px;"
             + "-fx-background-radius: 8;"
             + "-fx-cursor: hand;"
             + "-fx-padding: 8 16;";
    }

    public static String campotexto() {
        return "-fx-background-radius: 8;"
             + "-fx-border-radius: 8;"
             + "-fx-border-color: " + CINZA_BORDA + ";"
             + "-fx-padding: 8;"
             + "-fx-font-size: 13px;";
    }

    public static String topBar() {
        return "-fx-background-color: " + VERDE_ESCURO + ";"
             + "-fx-padding: 12 16;";
    }

    public static String card() {
        return "-fx-background-color: white;"
             + "-fx-background-radius: 10;"
             + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 6, 0, 0, 2);"
             + "-fx-padding: 14;";
    }

    public static String labelTitulo() {
        return "-fx-font-size: 20px;"
             + "-fx-font-weight: bold;"
             + "-fx-text-fill: " + VERDE_ESCURO + ";";
    }

    public static String labelSecao() {
        return "-fx-font-size: 13px;"
             + "-fx-text-fill: " + CINZA_TEXTO + ";";
    }
}
