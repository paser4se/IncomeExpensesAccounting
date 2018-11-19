package at.htl.iea.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static Parser instance = null;

    private void Parser() {}

    public static Parser getInstance () {
        if (Parser.instance == null) {
            Parser.instance = new Parser();
        }
        return Parser.instance;
    }

    public void persist(String fileContent) throws ParseException {
        //alle zahlungen (vom csv-file) werden in dieser liste gespeichert (und persistiert in der DB)
        List<Zahlung> zahlungen = new ArrayList<>(); // besser als linked list
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy");

        String[] lines = fileContent.split("\n");
        String [] lineElements;
        for(int i = 1; i < lines.length; i++){ //1. zeile überspringen
            lineElements = lines[i].split(";");
            Zahlung z = new Zahlung(format.parse(lineElements[0].replaceAll("\"", "")), lineElements[1], lineElements[2], lineElements[3], lineElements[4], lineElements[5], replaceLast(lineElements[6].replaceAll("\\.", ""), ",", "."), lineElements[7], lineElements[8], lineElements[9], lineElements[10], format.parse(lineElements[11].replaceAll("\"", "")));
            z = entfernenVonHochkommasBeiZahlung(z, "\"", ""); // "18.10.2018" --> 18.10.018 ... "100,00" --> 100,00
            zahlungen.add(z);
        }

        Database.initJdbc();
        for (Zahlung z : zahlungen){
            Database.insertIntoDatabase(z); //daten in der db persistieren
        }


        //for(Zahlung z : zahlungen){
        //    System.out.println(z.toString()); //ausgabe der vitalen informationen einer zahlung
        //}

        //Database.teardownJdbc();
    }

    private static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length(), string.length());
        } else {
            return string;
        }
    }

    private static Zahlung entfernenVonHochkommasBeiZahlung(Zahlung z, String regex, String replacement) {
        //z.setBuchungsdatum(z.getBuchungsdatum().replaceAll(regex, replacement));
        z.setBetrag(z.getBetrag().replaceAll(regex, replacement));
        z.setBuchungstext(z.getBuchungstext().replaceAll(regex, replacement));
        z.setErsterfassungsreferenz(z.getErsterfassungsreferenz().replaceAll(regex, replacement));
        z.setNotiz(z.getNotiz().replaceAll(regex, replacement));
        z.setPartner_bankcode(z.getPartner_bankcode().replaceAll(regex, replacement));
        z.setPartner_bic(z.getPartner_bic().replaceAll(regex, replacement));
        z.setPartner_iban(z.getPartner_iban().replaceAll(regex, replacement));
        z.setPartner_kontonummer(z.getPartner_kontonummer().replaceAll(regex, replacement));
        z.setPartner_name(z.getPartner_name().replaceAll(regex, replacement));
        //z.setValutadatum(z.getValutadatum().replaceAll(regex, replacement));
        z.setWährung(z.getWährung().replaceAll(regex, replacement));
        return z;
    }

    // momentan NICHT in verwendung [löschen wenn keine verwendung vorhanden ist im späteren verlauf]
    private static String[] replaceCharacterFromArray(String[] lineElements, String regex, String newReplacement) {
        int len = lineElements.length;
        for (int i = 0; i < len; i++){
            lineElements[i] = lineElements[i].replaceAll(regex, newReplacement);
        }
        return lineElements;
    }
}
