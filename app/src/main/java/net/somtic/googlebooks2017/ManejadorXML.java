package net.somtic.googlebooks2017;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Salva on 05/01/2017.
 */

public class ManejadorXML extends DefaultHandler {

    private String totalResults;
    private StringBuilder cadena = new StringBuilder();

    public String getTotalResults() {
        return totalResults;
    }

    @Override
    public void startElement(String uri, String nombreLocal,
                             String nombreCualif, Attributes atributos)
            throws SAXException {

        cadena.setLength(0);

    }

    @Override
    public void characters(char ch[], int comienzo, int lon){
        cadena.append(ch, comienzo, lon);
    }

    @Override
    public void endElement(String uri, String nombreLocal,
                           String nombreCualif) throws SAXException {

        if (nombreLocal.equals("totalResults")) {
            totalResults = cadena.toString();
        }
    }
}