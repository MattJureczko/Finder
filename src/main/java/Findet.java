import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.StringReader;
import java.security.cert.CertificateException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Findet {
    private int numberOfJob;
    private int numberOfJava;
    private List<String> lista;
    private List<String> javaList;

    Findet()  {
        //odpala funkcje refresh
        refreshMe();
    }

    public int getNumberOfJob() {
        return numberOfJob;
    }

    public int getNumberOfJava() {
        return numberOfJava;
    }

    public List<String> getLista() {
        return lista;
    }

    public List<String> getJavaList() {
        return javaList;
    }

    //na podstawie listy wszystkich ofert, wypluwa liste tych, ktore zawieraja slowo "java"
    public static List<String> javaList(List<String> list) {
        List<String> javaList = new ArrayList<String>();
        for (String el : list) {
            if (el.toLowerCase().contains("java".toLowerCase())) {
                javaList.add(el);
            }
        }
        return javaList;
    }

    //wypisuje zawaartosc listy
    public static void printout(List<String> lista) {
        for (String el : lista) {
            System.out.println(el);
        }
    }



    //pobiera
    public void refreshMe()  {

        try {
            List<String> lista = searchMe();
            this.numberOfJob = lista.size();
            this.numberOfJava = countJava(lista);
            this.lista = lista;
            this.javaList = javaList(lista);
        } catch (Exception e) {
            System.out.println("nie udalo sie polaczyc, sprobuj ponownie.");
        }

    }




    public static int countJava(List<String> listOfJobs) {
        int counter = 0;
        for (String line : listOfJobs) {

            if (line.toLowerCase().contains("java".toLowerCase())) {
                counter++;
                //System.out.println(line);
            }
        }
        return counter;
    }

    public static List<String> searchMe() throws Exception {
        // configure the SSLContext with a TrustManager
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);
        String htmlCode = Jsoup.connect("https://hemmersbach.com/career/browse-jobs?country=Poland&location=Bielany%20Wroclawskie%40%40%40Wroclaw&skillProfile=IT&").get().html();
        org.jsoup.nodes.Document doc = (org.jsoup.nodes.Document) Jsoup.parse(htmlCode);
        BufferedReader bufreader = new BufferedReader(new StringReader(htmlCode));
        String line = new String();
        String className = new String("<h3 class=\"title\">");
        List<String> listOfJobs = new ArrayList<String>();


        while ((line = bufreader.readLine()) != null) {
            if (line.toLowerCase().contains(className.toLowerCase())) {
                listOfJobs.add(line.replace("           <h3 class=\"title\">", "").replace("</h3>", ""));
            }
        }
        return listOfJobs;
    }


    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}