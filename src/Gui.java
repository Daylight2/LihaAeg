import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Gui {
    public void createAndShowGui() throws IOException {
        // Loo aken ja määra selle omadused
        JFrame raam = new JFrame("Liha Küpsetusaja Kalkulaator");
        raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        raam.setSize(800, 600);

        // Loo peamine sisupaneel
        Container sisuPaneel = raam.getContentPane();
        sisuPaneel.setLayout(new BorderLayout());

        // Loo teksti ala ja kerimispaneel
        JTextArea tekstiAla = new JTextArea();
        JScrollPane kerimisPaneel = new JScrollPane(tekstiAla);
        sisuPaneel.add(kerimisPaneel, BorderLayout.CENTER);

        // Loo alumine paneel ja selle elemendid
        JPanel aluminePaneel = new JPanel();
        aluminePaneel.setLayout(new GridLayout(1, 4));

        JLabel temperatuuriSilt = new JLabel("Ahju temperatuur (C):");
        aluminePaneel.add(temperatuuriSilt);

        JTextField temperatuuriVali = new JTextField();
        // Lisame InputVerifier'i temperatuuri väljale, et kontrollida väärtuste vahemikku
        temperatuuriVali.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                try {
                    double temperatuur = Double.parseDouble(textField.getText());
                    return temperatuur >= 100 && temperatuur <= 500;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        aluminePaneel.add(temperatuuriVali);

        JCheckBox külmMärkeruut = new JCheckBox("Liha külm");
        aluminePaneel.add(külmMärkeruut);

        JButton arvutaNupp = new JButton("Arvuta küpsetusaeg");
        aluminePaneel.add(arvutaNupp);

        // Lisa alumine paneel peamisele sisupaneelile
        sisuPaneel.add(aluminePaneel, BorderLayout.SOUTH);

        // Näita akent
        raam.setVisible(true);

        // Lae tellimuste fail ja loe tellimused
        File tellimusteFail = new File("tellimused.txt");
        List<Tellimus> tellimused = Main.loeTellimused(tellimusteFail);
        tekstiAla.append("Hetkel sisestatud tellimused: \n");
        for (Tellimus tellimus : tellimused) {
            tekstiAla.append(tellimus + "\n");
        }


        // Määra "arvutaNupp" nupule tegevus
        arvutaNupp.addActionListener(e -> {
            if (temperatuuriVali.getInputVerifier().verify(temperatuuriVali)) {
                // Leia kasutaja poolt sisestatud temperatuur ja külmuse info
                double temperatuur = Double.parseDouble(temperatuuriVali.getText());
                boolean onKülm = külmMärkeruut.isSelected();
                tekstiAla.append("\nTemperatuur: " + temperatuur + " C, Külm: " + onKülm + "\n");

                // Käi läbi kõik tellimused
                for (Tellimus tellimus : tellimused) {
                    Liha uueKüpsetusajaga = tellimus.getLiha();
                    // Arvuta uus küpsetusaeg ja määra see tellimusele
                    uueKüpsetusajaga.külmutatudLiha(onKülm);
                    uueKüpsetusajaga.ahjuTemperatuur(temperatuur);
                    tellimus.setLiha(uueKüpsetusajaga);

                    tekstiAla.append("Uus küpsetusaeg: " + tellimus + "\n");
                }

                //loome kujutuse laudade kaupa tellimuste hoidmiseks funktsiooniga "laudadeTellimused"
                Map<Integer, List<Tellimus>> lauad = Main.laudadeTellimused(tellimused);
                /* sorteerime saadud tellimused funktsiooniga "sorteeriLauad", mis kasutab klassis Tellimus olevat "compareTo" meetodit
                ehk sorteerib tellimused vastavalt liha küpsetusaegadele kahanevalt */
                lauad = Main.sorteeriLauad(lauad);
                Tellimus pikimTellimus = tellimused.get(0); //määrame algseks pikimaks tellimuseks esimese listis oleva tellimuse

                // Trüki pikima küpsetusajaga tellimused laudade kaupa
                tekstiAla.append("\nAllpool on toodud iga laua pikima küpsetusajaga tellimus:\n");
                for (Integer lauaNr : lauad.keySet()) {
                    // Leia pikim tellimus antud laualt
                    List<Tellimus> lauaTellimused = lauad.get(lauaNr);
                    Tellimus longestOrder = lauaTellimused.get(0);
                    // Lisa tellimuse info teksti alasse
                    tekstiAla.append("Laud " + lauaNr + ": " + longestOrder + "\n");

                    if (longestOrder.compareTo(pikimTellimus) > 0) {
                        pikimTellimus = longestOrder;
                    }
                }

                tekstiAla.append("\nKõige kauem võtab aega " + pikimTellimus + "\n");


                try {
                    //kirjutame juhised funktsiooniga "kirjutaJuhised" iga laua kohta liha küpsetamiseks (küpsetusaegade põhjal)
                    Main.kirjutaJuhised(lauad);
                    //edukal failikirjutamisel väljastame teate
                    tekstiAla.append("Juhised kirjutatud tekstifaili!");
                } catch (IOException r){
                    tekstiAla.append("Faili kirjutamine ebaõnnestus.");
                }


            } else {
                // Kuvame hoiatusteate, kui temperatuur ei ole lubatud vahemikus
                JOptionPane.showMessageDialog(raam,
                        "Sisestatud temperatuur peab olema vahemikus 100 kuni 500",
                        "Vigane temperatuur",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
