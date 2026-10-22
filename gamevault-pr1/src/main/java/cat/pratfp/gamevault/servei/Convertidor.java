package cat.pratfp.gamevault.servei;

import cat.pratfp.gamevault.io.CsvJocDao;
import cat.pratfp.gamevault.io.JsonJocDao;
import cat.pratfp.gamevault.model.Joc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Convertidor {

    private final CsvJocDao csv = new CsvJocDao();
    private final JsonJocDao json = new JsonJocDao();

    private String extensio(Path p) {
        String nom = p.getFileName().toString();
        int i = nom.lastIndexOf('.');
        return (i == -1) ? "" : nom.substring(i + 1).toLowerCase();
    }

    public List<Joc> carrega(Path origen) throws CatalegException {
        try {
            return switch (extensio(origen)) {
                case "csv" -> csv.llegeix(origen).jocs();
                case "json" -> json.llegeix(origen);
                default -> throw new CatalegException("Format no suportat: " + origen.getFileName());
            };
        } catch (IOException e) {
            throw new CatalegException("No s'ha pogut llegir " + origen.getFileName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * PR1-10: Desa la llista de jocs al destinació segons la seva extensió.
     */
    public void desa(List<Joc> jocs, Path desti) throws CatalegException {
        try {
            switch (extensio(desti)) {
                case "csv" -> csv.escriu(desti, jocs);
                case "json" -> json.escriu(desti, jocs);
                default -> throw new CatalegException("Format no suportat: " + desti.getFileName());
            }
        } catch (IOException e) {
            throw new CatalegException("No s'ha pogut desar " + desti.getFileName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * PR1-10: Converteix un fitxer d'un format a un altre i retorna quants jocs s'han convertit.
     */
    public int converteix(Path origen, Path desti) throws CatalegException {
        List<Joc> jocs = carrega(origen);
        desa(jocs, desti);
        return jocs.size();
    }
}