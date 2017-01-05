package net.somtic.googlebooks2017;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    class BuscarGoogle extends AsyncTask<String, Void, String> {

        private ProgressDialog progreso;

        @Override
        protected void onPreExecute() {
            progreso = new ProgressDialog(MainActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Accediendo a Google...");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(String... palabras) {
            try {
                return resultadosSW(palabras[0]);
            } catch(Exception e) {
                cancel(true);
                Log.e("HTTP", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String res) {
            progreso.dismiss();
            salida.append(res + "\n");
        }


        @Override
        protected void onCancelled() {
            progreso.dismiss();
            salida.append("Error al conectar\n");
        }

    }

    private EditText entrada;
    private TextView salida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText) findViewById(R.id.EditText01);
        salida = (TextView) findViewById(R.id.TextView01);

    }

    public void buscar(View view){
        String palabras = entrada.getText().toString();
        salida.append(palabras + "--");
        new BuscarGoogle().execute(palabras);
    }

    private String resultadosSW(String palabras) throws Exception {
        URL url =
                new URL("http://books.google.com/books/feeds/volumes?q=\""
                        + URLEncoder.encode(palabras, "UTF-8") + "\"");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        ManejadorXML manejadorXML = new ManejadorXML();
        reader.setContentHandler(manejadorXML);
        reader.parse(new InputSource(url.openStream()));
        return manejadorXML.getTotalResults();
    }
}
