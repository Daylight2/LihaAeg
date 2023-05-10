import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Gui {
    public void createAndShowGui() throws IOException {
        JFrame raam = new JFrame("Liha Küpsetusaja Kalkulaator");
        raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        raam.setSize(800, 600);

        Container sisuPaneel = raam.getContentPane();
        sisuPaneel.setLayout(new BorderLayout());

        JTextArea tekstiAla = new JTextArea();
        JScrollPane kerimisPaneel = new JScrollPane(tekstiAla);
        sisuPaneel.add(kerimisPaneel, BorderLayout.CENTER);

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

        sisuPaneel.add(aluminePaneel, BorderLayout.SOUTH);

        raam.setVisible(true);
        File tellimusteFail = new File("tellimused.txt");
        List<Tellimus> tellimused = Main.loeTellimused(tellimusteFail);
        tekstiAla.append("Hetkel sisestatud tellimused: \n");
        for (Tellimus tellimus : tellimused) {
            tekstiAla.append(tellimus + "\n");
        }


        arvutaNupp.addActionListener(e -> {
            double temperatuur = Double.parseDouble(temperatuuriVali.getText());
            boolean onKülm = külmMärkeruut.isSelected();
            tekstiAla.append("\nTemperatuur: " + temperatuur + " C, Külm: " + onKülm + "\n");

            Map<Integer, Tellimus> pikimKüpsetusaegTellimused = new HashMap<>();

            for (Tellimus tellimus : tellimused) {
                double küpsetusaeg = Liha.arvutaKüpsetusAeg(tellimus, temperatuur, onKülm);
                tellimus.getLiha().setKüpsetusaeg(küpsetusaeg);
                tekstiAla.append("Küpsetusaeg " + tellimus + "\n");

                int lauaNr = tellimus.getLauaNr();
                Tellimus praegunePikimTellimus = pikimKüpsetusaegTellimused.get(lauaNr);

                if (praegunePikimTellimus == null || tellimus.compareTo(praegunePikimTellimus) > 0) {
                    pikimKüpsetusaegTellimused.put(lauaNr, tellimus);
                }
            }

            tekstiAla.append("\nKauem aega võtvad tellimused laudade kaupa:\n");
            for (Integer lauaNr : pikimKüpsetusaegTellimused.keySet()) {
                Tellimus longestOrder = pikimKüpsetusaegTellimused.get(lauaNr);
                tekstiAla.append("Laud " + lauaNr + ": " + longestOrder + "\n");
            }
        });
    }
    }