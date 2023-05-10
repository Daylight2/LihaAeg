import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gui gui = new Gui();
            try {
                gui.createAndShowGui();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public static List<Tellimus> loeTellimused(File failinimi) throws IOException {
        List<Tellimus> tellimused = new ArrayList<>();
        InputStream fail = new FileInputStream(failinimi);
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(fail, "UTF-8"))) {

            String rida;
            int lauaNr = 0;
            String lihaTüüp;
            double lihaKaal = 0;
            String küpsusAste;
            boolean onLisandiga;
            String suurus;

            while ((rida = bf.readLine()) != null) {
                String[] tükid = rida.split(";");
                lauaNr = Integer.parseInt(tükid[0]);
                lihaTüüp = tükid[1];
                if (rida.contains("@")) { //lisandiga tellimused
                    onLisandiga = true;
                    if (lihaTüüp.equalsIgnoreCase("veiseliha")) { //veiseliha on ainus, millel määrata erinev (ehk failist tulenev) soovitud küpsusastet
                        suurus = tükid[2];
                        String[] jupid = tükid[3].split("@");
                        küpsusAste = jupid[0];
                    } else { //teiste lihatüüpide (kana, kalkun ja siga) korral võiks soovituslik küpsusaste olla määratud "well-done"
                        String[] jupid = tükid[2].split("@");
                        suurus = jupid[0];
                        küpsusAste = "well-done";
                    }
                } else {
                    onLisandiga = false;
                    if (lihaTüüp.equalsIgnoreCase("veiseliha")) { //veiseliha on ainus, millel määrata erinev (ehk failist tulenev) soovitud küpsusastet
                        suurus = tükid[2];
                        küpsusAste = tükid[3];
                    } else { //teiste lihatüüpide (kana, kalkun ja siga) korral võiks soovituslik küpsusaste olla määratud "well-done"
                        suurus = tükid[2];
                        küpsusAste = "well-done";
                    }
                }

                if (suurus.equalsIgnoreCase("väike")) { //kuna küpsetusaja määramisel eristame kaalusid suurem või väiksem 500grammist, siis määrame vastavalt "väike praad" 350g ning "suur praad" 550G
                    lihaKaal = 350;
                } else {
                    lihaKaal = 550;
                }

                Tellimus tellimus = new Tellimus(lauaNr, lihaTüüp, küpsusAste, lihaKaal, onLisandiga); //loome tellimused
                tellimused.add(tellimus); //lisame tellimused listi
            }
        }
        return tellimused;
    }

    //laudade kaupa tellimuste lisamine kujutusse (HashMap)
    public static Map<Integer, List<Tellimus>> laudadeTellimused(List<Tellimus> tellimused) {
        Map<Integer, List<Tellimus>> lauad = new HashMap<>();
        for (Tellimus tellimus : tellimused) {
            int lauaNr = tellimus.getLauaNr();
            if (lauad.containsKey(lauaNr)) { //kui sellise laua numbriga tellimus juba on, lisame listi
                List<Tellimus> lauaTellimused = lauad.get(lauaNr);
                lauaTellimused.add(tellimus);
                lauad.put(lauaNr, lauaTellimused);
            } else { //kui sellise laua numbriga tellimust ei olnud, loome uue listi
                List<Tellimus> lauaTellimused = new ArrayList<>();
                lauaTellimused.add(tellimus);
                lauad.put(lauaNr, lauaTellimused);
            }
        }
        return lauad;
    }
}
