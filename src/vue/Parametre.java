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

        // Création des labels
        JLabel Ressources = new JLabel("Ressources : ");
        JLabel Notions = new JLabel("Notions : ");

        // Création des boutons verts
        JButton ajtRessource = new JButton("Ajouter une ressource");
        ajtRessource.setBackground(Color.GREEN);
        ajtRessource.setPreferredSize(new Dimension(200, 50));

        JButton ajtNotion = new JButton("Ajouter une notion");
        ajtNotion.setBackground(Color.GREEN);
        ajtNotion.setPreferredSize(new Dimension(200, 50));

        // Création des boutons rouges
        JButton supprRessource = new JButton("Supprimer une ressource");
        supprRessource.setBackground(Color.RED);
        supprRessource.setPreferredSize(new Dimension(200, 50));

        JButton supprNotion = new JButton("Supprimer une notion");
        supprNotion.setBackground(Color.RED);
        supprNotion.setPreferredSize(new Dimension(200, 50));

        // Ajout des composants au panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCreation.add(Ressources, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCreation.add(ajtRessource, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        panelCreation.add(supprRessource, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCreation.add(Notions, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCreation.add(ajtNotion, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panelCreation.add(supprNotion, gbc);

        // Bouton de retour
        String imagePath = "QCMBuilder/lib/icones/home.png";
        File imageFile = new File(imagePath);
        ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JButton retourButton = new JButton(icon);
        retourButton.addActionListener(e -> {
            new Accueil();
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
