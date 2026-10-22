package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JsonJocDao {

    private ObjectMapper creaMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * PR1-08: Configura ObjectMapper i escriu la llista de jocs en JSON.
     *
     * @param p Ruta del fitxer JSON de destinació
     * @param jocs Llista de jocs a escriure
     * @throws IOException si hi ha un error d'escriptura
     */
    public void escriu(Path p, List<Joc> jocs) throws IOException {
        ObjectMapper mapper = creaMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(p.toFile(), jocs);
    }

    /**
     * PR1-09: Llegeix el fitxer JSON i retorna una List<Joc>.
     *
     * @param p Ruta del fitxer JSON
     * @return Llista de jocs llegits
     * @throws IOException si hi ha un error de lectura
     */
    public List<Joc> llegeix(Path p) throws IOException {
        ObjectMapper mapper = creaMapper();
        return mapper.readValue(p.toFile(), new TypeReference<List<Joc>>() {});
    }
}