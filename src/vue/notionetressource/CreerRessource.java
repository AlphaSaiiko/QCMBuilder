package vue.notionetressource;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;

public class CreerRessource extends JFrame
{
    /**
     *  +--------------+
     *  | CONSTRUCTEUR |
     *  +--------------+
     */

    public CreerRessource()
    {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // JTextArea pour la clé de la ressource
        JTextArea texteCle = new JTextArea(1, 20);
        texteCle.setBorder(BorderFactory.createTitledBorder("Clé de la ressource"));
        gbc.gridx = 0;
        gbc.gridy = 0; // Position en haut
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;
        gbc.weighty = 0;
        panelPrincipal.add(texteCle, gbc);

        // JTextArea pour le titre de la ressource
        JTextArea texteTitre = new JTextArea(1, 20);
        texteTitre.setBorder(BorderFactory.createTitledBorder("Titre de la ressource"));
        gbc.gridx = 0;
        gbc.gridy = 1; // Position en dessous du texteCle
        panelPrincipal.add(texteTitre, gbc);

        // JButton pour ajouter une ressource à côté du titre
        JButton btnAjouter = new JButton("Ajouter");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.2;
        gbc.weighty = 0;
        panelPrincipal.add(btnAjouter, gbc);

        // Récupérer le contenu des JTextArea et créer une nouvelle ressource
        btnAjouter.addActionListener(e -> {
            String titreRessource = texteTitre.getText();
            String cleRessource = texteCle.getText();
            if (!titreRessource.trim().isEmpty() && !cleRessource.trim().isEmpty()) {
                Controleur.creerRessource(titreRessource, cleRessource); // Assurez-vous que votre méthode accepte également la clé
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Le titre et la clé de la ressource ne peuvent pas être vides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ajout du panel principal à la frame et configuration de cette dernière
        this.add(panelPrincipal);
        this.setTitle("Créer une ressource");
        this.setSize(500, 200); // Ajuster la taille pour inclure le nouveau champ
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Main
    public static void main(String[] args)
    {
        new CreerRessource();
    }
}
