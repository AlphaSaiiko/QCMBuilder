package vue;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class Parametre extends JFrame {

    /*
     *  +--------------+
     *  | CONSTRUCTEUR |
     *  +--------------+
     */
    public Parametre() {
        setTitle("Paramètres");

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout());

        // Création d'un panel gérant la création de question
        JPanel panelCreation = new JPanel();
        panelCreation.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Création des boutons verts
        JButton ajtRessource = new JButton("Ajouter une ressource");
        ajtRessource.setBackground(Color.GREEN);
        ajtRessource.setPreferredSize(new Dimension(200, 50));
        ajtRessource.addActionListener(e -> {
            // Code pour lancer la création d'une notion
            CreerRessource creerRessource = new CreerRessource();
            creerRessource.setVisible(true);
        });


        JButton ajtNotion = new JButton("Ajouter une notion");
        ajtNotion.setBackground(Color.GREEN);
        ajtNotion.setPreferredSize(new Dimension(200, 50));
        ajtNotion.addActionListener(e -> {
            // Code pour lancer la création d'une notion
            CreerNotion creerNotion = new CreerNotion();
            creerNotion.setVisible(true);
        });

        // Ajout des composants au panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCreation.add(ajtRessource, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCreation.add(ajtNotion, gbc);

        // Bouton de retour
        String imagePath = "QCMBuilder/lib/icones/home.png";
        File imageFile = new File(imagePath);
        ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JButton retourButton = new JButton(icon);
        retourButton.addActionListener(e -> {
            Accueil accueil = new Accueil();
            accueil.setVisible(true);
            dispose();
        });
        JPanel panelRetour = new JPanel();
        panelRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelRetour.add(retourButton);

        // Ajout des panels au panel principal
        panelprincipal.add(panelRetour, BorderLayout.NORTH);
        panelprincipal.add(panelCreation, BorderLayout.CENTER);

        this.add(panelprincipal);
        this.setSize(700, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Parametre();
    }
}
