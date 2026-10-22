Preguntes de Reflexio i Disseny (README.md)

1. Per que usem un record per a Joc en lloc d una classe tradicional?
Resposta:
Utilitzem un record perque Joc es un contenidor de dades immutable. En ser un record, Java genera automaticament els metodes de lectura (id(), titol(), etc.), equals(), hashCode() i toString(), reduint drasticament el codi repetitiu (boilerplate). A mes, la immutabilitat garanteix la seguretat en operacions de lectura i la integritat de les dades en tota l aplicacio.
2. Quin avantatge te utilitzar RandomAccessFile respecte a llegir tot el fitxer en memoria?
Resposta:
RandomAccessFile permet accedir i modificar qualsevol posicio o registre del fitxer en temps constant mitjanant el metode seek(), sense haver de carregar tot el fitxer en memoria RAM. Aquest enfocament es molt mes eficient en memoria i en temps d execucio quan es treballa amb fitxers de gran volum o quan nomes es vol llegir o actualitzar un camp especific (in-place update).
3. Quina diferencia hi ha entre la serialitzacio Java (CauCataleg) i el format JSON (JsonJocDao)?
Resposta:

* Serialitzacio Java (CauCataleg): Produeix un format binari propi de Java. Es molt rapid de processar dins d un entorn Java, pero no es llegible per humans ni es facilment portable a altres llenguatges de programacio.
* Format JSON (JsonJocDao): Produeix un format de text dades estructurat que es llegible per humans, estandard i totalment interoperable entre diferents plataformes i llenguatges de programacio (Python, JavaScript, Go, etc.).

Resum de Commits del Projecte (Per a la memoria de l exercici)

Part A (PR1-02, PR1-03, PR1-04): Gestio del sistema de fitxers i estructura de directoris
Commit: PR1-02, PR1-03 i PR1-04: gestio del magatzem de fitxers i subdirectoris
Part B (PR1-01, PR1-05): Model de dades Joc i registre d operacions (logging)
Commit: PR1-01 i PR1-05: model Joc immutable i registre d operacions
Part C (PR1-06, PR1-07): DAO de fitxers CSV amb parser OpenCSV i gestio d incidencies
Commit: PR1-06 i PR1-07: lectura de CSV amb gestio d incidencies i escriptura
Part D (PR1-08, PR1-09, PR1-10): DAO JSON amb Jackson i convertidor de formats
Commit: PR1-08, PR1-09 i PR1-10: processament de JSON i convertidor entre CSV i JSON
Part E (PR1-11): Cau binari amb serialitzacio d objectes Java
Commit: PR1-11: cau binari amb serialitzacio d objectes Java
Part F (PR1-12, PR1-13): Acces directe i actualitzacio in-place amb RandomAccessFile
Commit: PR1-12 i PR1-13: indexacio d hores amb RandomAccessFile i actualitzacio in-place
Part G (PR1-14): Proves meves de Convertidor i IndexHores
Commit: PR1-14: afegides proves unitaries propies per a Convertidor e IndexHores
