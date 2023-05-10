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

        JLabel temperatuuriSilt = new JLabel("Temperatuur (C):");
        aluminePaneel.add(temperatuuriSilt);

        JTextField temperatuuriVali = new JTextField();
        aluminePaneel.add(temperatuuriVali);

        JCheckBox külmMärkeruut = new JCheckBox("Külm");
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
        // Leia kasutaja poolt sisestatud temperatuur ja külmuse info
            double temperatuur = Double.parseDouble(temperatuuriVali.getText());
            boolean onKülm = külmMärkeruut.isSelected();
            tekstiAla.append("\nTemperatuur: " + temperatuur + " C, Külm: " + onKülm + "\n");

            // Loome kaardi pikimate küpsetusaegadega tellimustest
            Map<Integer, Tellimus> pikimKüpsetusaegTellimused = new HashMap<>();

            // Käi läbi kõik tellimused
            for (Tellimus tellimus : tellimused) {
                // Arvuta küpsetusaeg ja määra see tellimusele
                double küpsetusaeg = Liha.arvutaKüpsetusAeg(tellimus, temperatuur, onKülm);
                tellimus.getLiha().setKüpsetusaeg(küpsetusaeg);
                tekstiAla.append("Küpsetusaeg " + tellimus + "\n");

                // Leia laua number
                int lauaNr = tellimus.getLauaNr();
                // Leia praegune pikim tellimus laualt
                Tellimus praegunePikimTellimus = pikimKüpsetusaegTellimused.get(lauaNr);

                // Kontrolli, kas praegune tellimus on pikema küpsetusajaga kui praegune pikim tellimus
                if (praegunePikimTellimus == null || tellimus.compareTo(praegunePikimTellimus) > 0) {
                    pikimKüpsetusaegTellimused.put(lauaNr, tellimus);
                }
            }

            // Trüki pikima küpsetusajaga tellimused laudade kaupa
            tekstiAla.append("\nKauem aega võtvad tellimused laudade kaupa:\n");
            for (Integer lauaNr : pikimKüpsetusaegTellimused.keySet()) {
                // Leia pikim tellimus antud laualt
                Tellimus longestOrder = pikimKüpsetusaegTellimused.get(lauaNr);
                // Lisa tellimuse info teksti alasse
                tekstiAla.append("Laud " + lauaNr + ": " + longestOrder + "\n");
            }

        });
    }
}