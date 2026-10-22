package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;
import cat.pratfp.gamevault.model.ResultatCarrega;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CsvJocDao {

    private static final char SEPARADOR = ';';

    private static final DateTimeFormatter FORMAT_DATA =
            DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * PR1-06: Llegeix el fitxer CSV i separa els jocs vàlids
     * de les incidències.
     */
    public ResultatCarrega llegeix(Path p) throws IOException {

        List<Joc> jocs = new ArrayList<>();
        List<String> incidencies = new ArrayList<>();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(SEPARADOR)
                .withQuoteChar('"')
                .build();

        try (Reader reader = Files.newBufferedReader(
                p, StandardCharsets.UTF_8);
             CSVReader csv = new CSVReaderBuilder(reader)
                     .withCSVParser(parser)
                     .withSkipLines(1)
                     .build()) {

            String[] camps;
            int linia = 1;

            while (true) {
                try {
                    camps = csv.readNext();

                    if (camps == null) {
                        break;
                    }
                } catch (CsvValidationException e) {
                    linia++;
                    incidencies.add(
                            "línia " + linia + ": format CSV invàlid");
                    continue;
                }

                linia++;

                if (camps.length < 7) {
                    incidencies.add(
                            "línia " + linia + ": incompleta");
                    continue;
                }

                try {
                    int id = Integer.parseInt(camps[0].trim());
                    String titol = camps[1].trim();

                    /*
                     * El CSV s'escriu amb els valors de les columnes
                     * 3 i 4 intercanviats. Aquí desfem aquest canvi.
                     */
                    String estudi = camps[2].trim();
                    String plataforma = camps[3].trim();

                    LocalDate dataSortida;

                    try {
                        dataSortida = LocalDate.parse(
                                camps[4].trim(), FORMAT_DATA);
                    } catch (DateTimeParseException e) {
                        incidencies.add(
                                "línia " + linia + ": data mal formada");
                        continue;
                    }

                    double horesJugades;

                    try {
                        horesJugades = Double.parseDouble(
                                camps[5].trim());
                    } catch (NumberFormatException e) {
                        incidencies.add(
                                "línia " + linia
                                        + ": hores no és un número vàlid");
                        continue;
                    }

                    double nota;

                    try {
                        nota = Double.parseDouble(camps[6].trim());
                    } catch (NumberFormatException e) {
                        incidencies.add(
                                "línia " + linia
                                        + ": nota no és un número vàlid");
                        continue;
                    }

                    Joc joc = new Joc(
                            id,
                            titol,
                            estudi,
                            plataforma,
                            dataSortida,
                            horesJugades,
                            nota
                    );

                    jocs.add(joc);

                } catch (IllegalArgumentException e) {
                    incidencies.add(
                            "línia " + linia + ": " + e.getMessage());
                }
            }
        }

        return new ResultatCarrega(jocs, incidencies);
    }

    /**
     * PR1-07: Escriu la llista de jocs en format CSV.
     */
    public void escriu(Path p, List<Joc> jocs) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(
                p, StandardCharsets.UTF_8);
             CSVWriter csvWriter = new CSVWriter(
                     writer,
                     SEPARADOR,
                     '"',
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END)) {

            // La capçalera es manté exactament igual.
            String[] header = {
                    "id",
                    "titol",
                    "plataforma",
                    "estudi",
                    "dataSortida",
                    "horesJugades",
                    "nota"
            };

            csvWriter.writeNext(header, false);

            for (Joc joc : jocs) {

                String[] camps = aCamps(joc);

                /*
                 * Intercanviem les columnes 3 i 4 just abans
                 * d'escriure la fila.
                 */
                String temporal = camps[2];
                camps[2] = camps[3];
                camps[3] = temporal;

                csvWriter.writeNext(camps, false);
            }
        }
    }

    /**
     * Converteix un joc en els camps del CSV.
     */
    private String[] aCamps(Joc joc) {

        return new String[]{
                String.valueOf(joc.id()),
                joc.titol(),
                joc.plataforma(),
                joc.estudi(),
                joc.dataSortida().format(FORMAT_DATA),
                String.valueOf(joc.horesJugades()),
                String.valueOf(joc.nota())
        };
    }
}