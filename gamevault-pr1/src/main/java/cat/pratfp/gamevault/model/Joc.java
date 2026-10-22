package cat.pratfp.gamevault.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa un joc del catàleg de GameVault.
 */
public record Joc(
        int id,
        String titol,
        String estudi,
        String plataforma,
        LocalDate dataSortida,
        double horesJugades,
        double nota
) implements Serializable {

    /**
     * PR1-01: Validació del constructor compacte de Joc.
     */
    public Joc {
        if (titol == null || titol.isBlank()) {
            throw new IllegalArgumentException("El títol no pot estar buit");
        }
        if (horesJugades < 0) {
            throw new IllegalArgumentException("Les hores no poden ser negatives");
        }
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("La nota ha d'anar de 0 a 10");
        }
    }
}y