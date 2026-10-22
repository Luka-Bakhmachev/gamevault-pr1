package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CauCataleg {

    /**
     * PR1-11: Serialitza la llista de jocs a un fitxer binari.
     *
     * @param desti Ruta del fitxer binari de destinació
     * @param jocs Llista de jocs a desar
     * @throws IOException si hi ha un error d'escriptura
     */
    public void desa(Path desti, List<Joc> jocs) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(desti))) {
            oos.writeObject(jocs);
        }
    }

    /**
     * PR1-11: Desserialitza la llista de jocs des d'un fitxer binari.
     *
     * @param origen Ruta del fitxer binari
     * @return Llista de jocs carregada
     * @throws IOException si hi ha un error de lectura
     */
    @SuppressWarnings("unchecked")
    public List<Joc> carrega(Path origen) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(origen))) {
            return (List<Joc>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("No s'ha trobat la classe en desserialitzar", e);
        }
    }
}