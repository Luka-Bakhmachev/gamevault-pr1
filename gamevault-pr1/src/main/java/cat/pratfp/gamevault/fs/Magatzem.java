package cat.pratfp.gamevault.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class Magatzem {

    private static final DateTimeFormatter MARCA = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private final Path base;

    public Magatzem(Path base) {
        this.base = base;
    }

    public Path importacio() {
        return base.resolve("import");
    }

    public Path exportacio() {
        return base.resolve("export");
    }

    public Path copies() {
        return base.resolve("backup");
    }

    public static String marcaDeTemps() {
        return LocalDateTime.now().format(MARCA);
    }

    /**
     * Retorna la llista de tots els fitxers del magatzem ordenats per ruta.
     *
     * @return Llista de rutes (Path) de fitxers regulars
     * @throws IOException si hi ha un error en recórrer el directori
     */
    public List<Path> inventari() throws IOException {
        try (Stream<Path> stream = Files.walk(base)) {
            return stream.filter(Files::isRegularFile)
                    .sorted()
                    .toList();
        }
    }

    /**
     * PR1-02: Crea les carpetes import, export i backup si no existeixen.
     *
     * @throws IOException si hi ha un error de creació de directoris
     */
    public void preparaEstructura() throws IOException {
        Files.createDirectories(importacio());
        Files.createDirectories(exportacio());
        Files.createDirectories(copies());
    }

    /**
     * PR1-03: Realitza una còpia de seguretat del fitxer a la carpeta backup
     * amb el nom en format nom_marca.extensió.
     *
     * @param orig Ruta del fitxer original a copiar
     * @return Ruta del fitxer de la còpia creat a backup
     * @throws IOException si hi ha un error durant la còpia
     */
    public Path copiaSeguretat(Path orig) throws IOException {
        String filename = orig.getFileName().toString();
        int dotIndex = filename.lastIndexOf('.');

        String name = (dotIndex == -1) ? filename : filename.substring(0, dotIndex);
        String ext = (dotIndex == -1) ? "" : filename.substring(dotIndex);

        String newFilename = name + "_" + marcaDeTemps() + ext;
        Path desti = copies().resolve(newFilename);

        return Files.copy(orig, desti, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * PR1-04: Calcula la mida total en bytes de tots els fitxers del magatzem,
     * incloent-hi els de les subcarpetes.
     *
     * @return Mida total en bytes
     * @throws IOException si hi ha un error en recórrer el directori
     */
    public long midaTotal() throws IOException {
        try (Stream<Path> stream = Files.walk(base)) {
            return stream.filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0L;
                        }
                    })
                    .sum();
        }
    }
}