package cat.pratfp.gamevault;

import cat.pratfp.gamevault.io.IndexHores;
import cat.pratfp.gamevault.model.Joc;
import cat.pratfp.gamevault.servei.CatalegException;
import cat.pratfp.gamevault.servei.Convertidor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Les teves proves (PR1-14).
 */
class ProvesPropiesTest {

    @TempDir
    Path tmp;

    @Test
    @DisplayName("PR1-14 · Convertidor llança CatalegException quan es carrega un fitxer .json inexistent")
    void primeraProva() throws Exception {
        Convertidor convertidor = new Convertidor();
        Path inexistent = tmp.resolve("fitxer_inexistent.json");

        assertThrows(CatalegException.class, () -> convertidor.carrega(inexistent));
    }

    @Test
    @DisplayName("PR1-14 · IndexHores acumula hores de forma acurada en un mateix registre")
    void segonaProva() throws Exception {
        Path dat = tmp.resolve("test_index.dat");
        IndexHores index = new IndexHores(dat);

        Joc j = new Joc(1, "Hades", "Supergiant", "PC", LocalDate.now(), 10.0, 9.5);
        index.escriu(0, j);

        index.sumaHores(0, 5.0);
        index.sumaHores(0, 2.5);

        assertEquals(17.5, index.llegeixHores(0));
    }
}