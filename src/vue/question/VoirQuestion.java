package vue.question;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Question;

public class VoirQuestion extends JFrame {
    private Question question;

    public VoirQuestion(Question question) {
        this.question = question;
        this.setTitle(question.getEnonce());
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        // Création du panel qui contient le bouton retour
        JPanel panelHaut = new JPanel();
        panelHaut.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Bouton "Menu principal"
        String imageMenu = "./lib/icones/home.png";
        ImageIcon icon = new ImageIcon(imageMenu);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JButton btnMenu = new JButton(icon);
        btnMenu.addActionListener(e -> {
            Controleur.ouvrirAccueil();
            dispose();
        });
        panelHaut.add(btnMenu);

        // Ajout du bouton retour à la Frame
        this.add(panelHaut, gbc);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbcPanelPrincipal = new GridBagConstraints();
        gbcPanelPrincipal.gridx = 0;
        gbcPanelPrincipal.gridy = 1;
        gbcPanelPrincipal.anchor = GridBagConstraints.CENTER;
        gbcPanelPrincipal.fill = GridBagConstraints.BOTH;
        gbcPanelPrincipal.weightx = 1.0;
        gbcPanelPrincipal.weighty = 1.0;

        // Panel pour les points et le temps
        JPanel panelPointsEtTemps = new JPanel();
        panelPointsEtTemps.setLayout(new GridBagLayout());

        // Ajouter le texte des points
        JLabel textePoints = new JLabel(String.valueOf(question.getNbPoints()));

        // Ajouter les points à son panel
        JPanel panelPoints = new JPanel();
        panelPoints.setLayout(new BorderLayout());
        panelPoints.add(new JLabel("Nombre de points"), BorderLayout.NORTH);
        panelPoints.add(textePoints, BorderLayout.CENTER);
        panelPoints.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10)); // Espacement

        // Ajouter le texte du temps
        JLabel texteTemps = new JLabel();
        String minutes = String.format("%02d", question.getTemps() / 60);
        String secondes = String.format("%02d", question.getTemps() % 60);
        texteTemps.setText(minutes + ":" + secondes);

        // Ajouter le temps à son panel
        JPanel panelTemps = new JPanel();
        panelTemps.setLayout(new BorderLayout());
        panelTemps.add(new JLabel("Temps de réponse (min : sec)"), BorderLayout.NORTH);
        panelTemps.add(texteTemps, BorderLayout.CENTER);
        panelTemps.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0)); // Espacement

        GridBagConstraints gbcPointsEtTemps = new GridBagConstraints();
        gbcPointsEtTemps.gridx = 0;
        gbcPointsEtTemps.gridy = 0;
        gbcPointsEtTemps.insets = new java.awt.Insets(5, 5, 5, 5); // Ajouter des marges
        panelPointsEtTemps.add(panelPoints, gbcPointsEtTemps);
        gbcPointsEtTemps.gridx = 1;
        gbcPointsEtTemps.gridy = 0;
        panelPointsEtTemps.add(panelTemps, gbcPointsEtTemps);

        // Ajouter le panel points et temps au panel principal
        GridBagConstraints gbcPanelPointsEtTemps = new GridBagConstraints();
        gbcPanelPointsEtTemps.gridx = 0;
        gbcPanelPointsEtTemps.gridy = 0;
        gbcPanelPointsEtTemps.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelPointsEtTemps, gbcPanelPointsEtTemps);

        // Changer le texte de la difficulté selon sa valeur
        String difficulte = switch (question.getDifficulte()) {
            case 1 -> "Facile";
            case 3 -> "Difficile";
            case 4 -> "Très difficile";
            default -> "Moyenne";
        };

        // Panel pour bien placer le cercle de difficulté et mettre le titre
        JPanel panelDifficulte = new JPanel();
        panelDifficulte.setLayout(new BorderLayout());
        panelDifficulte.add(new JLabel("Difficulté"), BorderLayout.NORTH);
        panelDifficulte.add(new JLabel(difficulte), BorderLayout.CENTER);
        panelDifficulte.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0)); // Espacement

        // Placer le panel de difficulté juste en dessous du panel principal
        GridBagConstraints gbcPanelDifficulte = new GridBagConstraints();
        gbcPanelDifficulte.gridx = 0;
        gbcPanelDifficulte.gridy = 1;
        gbcPanelDifficulte.anchor = GridBagConstraints.CENTER;
        gbcPanelDifficulte.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(panelDifficulte, gbcPanelDifficulte);

        // Ajouter le panel principal à la fenêtre
        this.add(panelPrincipal, gbcPanelPrincipal);

        // Afficher la fenêtre
        this.setVisible(true);
    }
}
