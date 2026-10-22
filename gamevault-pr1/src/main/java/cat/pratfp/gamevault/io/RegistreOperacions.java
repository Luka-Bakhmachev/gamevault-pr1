package cat.pratfp.gamevault.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistreOperacions {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Path fitxer;

    public RegistreOperacions(Path fitxer) {
        this.fitxer = fitxer;
    }

    /**
     * PR1-05: Registra una operació al fitxer de log en mode APPEND.
     */
    public void registra(String accio) throws IOException {
        String timestamp = LocalDateTime.now().format(FORMAT);
        String linia = timestamp + " | " + accio + System.lineSeparator();

        Files.writeString(
                fitxer,
                linia,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }
}