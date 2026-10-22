package cat.pratfp.gamevault.io;

import cat.pratfp.gamevault.model.Joc;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class IndexHores {

    public static final int MIDA_REGISTRE = 92;
    public static final int LONG_TITOL = 40;
    private final Path fitxer;

    public IndexHores(Path fitxer) {
        this.fitxer = fitxer;
    }

    public void escriu(int posicio, Joc j) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(fitxer.toFile(), "rw")) {
            raf.seek((long) posicio * MIDA_REGISTRE);
            raf.writeInt(j.id());
            String fix = String.format("%-" + LONG_TITOL + "s", j.titol()).substring(0, LONG_TITOL);
            raf.writeChars(fix);
            raf.writeDouble(j.horesJugades());
        }
    }

    /**
     * PR1-12: Llegeix només les hores d'un registre fent seek al camp directament.
     */
    public double llegeixHores(int posicio) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(fitxer.toFile(), "r")) {
            long offset = (long) posicio * MIDA_REGISTRE + 84;
            raf.seek(offset);
            return raf.readDouble();
        }
    }

    /**
     * PR1-13: Suma hores extres al registre indicat sense modificar la resta de bytes.
     * Retorna el nou total d'hores jugades.
     */
    public double sumaHores(int posicio, double extra) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(fitxer.toFile(), "rw")) {
            long offset = (long) posicio * MIDA_REGISTRE + 84;
            raf.seek(offset);
            double actual = raf.readDouble();

            double nouTotal = actual + extra;
            raf.seek(offset);
            raf.writeDouble(nouTotal);
            return nouTotal;
        }
    }

    /**
     * Retorna el nombre total de registres emmagatzemats al fitxer binari.
     */
    public int nombreRegistres() throws IOException {
        File f = fitxer.toFile();
        if (!f.exists()) {
            return 0;
        }
        return (int) (f.length() / MIDA_REGISTRE);
    }
}