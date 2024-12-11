package vue;

import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import modele.Ressource;

public class CreerEvaluation extends JFrame {

    public CreerEvaluation() {
        setTitle("Génération d'évaluation");

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        String imageRet = "./lib/icones/home.png";
        ImageIcon icon = new ImageIcon(imageRet);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JButton retourButton = new JButton(icon);
        retourButton.addActionListener(e -> {
            new Accueil();
            dispose();
        });
        JPanel panelRetour = new JPanel();
        panelRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelRetour.add(retourButton);

        JPanel linePanel = new JPanel(new GridBagLayout());
        GridBagConstraints lineGbc = new GridBagConstraints();
        lineGbc.insets = new Insets(10, 10, 10, 10);

        lineGbc.gridx = 0;
        lineGbc.gridy = 0;
        lineGbc.anchor = GridBagConstraints.WEST;
        linePanel.add(new JLabel("Ressource"), lineGbc);

        lineGbc.gridx = 1;
        lineGbc.gridy = 0;
        lineGbc.anchor = GridBagConstraints.CENTER;
        JComboBox<String> ressources = new JComboBox<>(Ressource.getNomsRessources());
        linePanel.add(ressources, lineGbc);

        lineGbc.gridx = 2;
        lineGbc.gridy = 0;
        lineGbc.anchor = GridBagConstraints.CENTER;
        String imagePath = "./lib/icones/timer.png";
		File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon timerIcon = new ImageIcon(imagePath);
            JLabel timerLabel = new JLabel(timerIcon);
            linePanel.add(timerLabel, lineGbc);
        } else {
            JLabel errorLabel = new JLabel("Image non trouvée : " + imagePath);
            errorLabel.setForeground(Color.RED);
            linePanel.add(errorLabel, lineGbc);
        }

        lineGbc.gridx = 3;
        lineGbc.gridy = 0;
        lineGbc.anchor = GridBagConstraints.CENTER;
        JRadioButton ouiButton = new JRadioButton("Oui");
        JRadioButton nonButton = new JRadioButton("Non");

        ButtonGroup group = new ButtonGroup();
        group.add(ouiButton);
        group.add(nonButton);

        JPanel radioPanel = new JPanel(new GridBagLayout());
        GridBagConstraints radioGbc = new GridBagConstraints();
        radioGbc.gridx = 0;
        radioGbc.gridy = GridBagConstraints.RELATIVE;
        radioGbc.insets = new Insets(5, 5, 5, 5);
        radioGbc.anchor = GridBagConstraints.CENTER;

        radioPanel.add(ouiButton, radioGbc);
        radioPanel.add(nonButton, radioGbc);

        linePanel.add(radioPanel, lineGbc);

        panelPrincipal.add(panelRetour, BorderLayout.NORTH);
        panelPrincipal.add(linePanel, BorderLayout.CENTER);

        JButton creerButton = new JButton("Créer une nouvelle évaluation");
        JPanel panelBouton = new JPanel();
        panelBouton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBouton.add(creerButton);

        panelPrincipal.add(panelBouton, BorderLayout.SOUTH);

        this.add(panelPrincipal);
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        creerButton.addActionListener(e -> {
            String selectedRessource = (String) ressources.getSelectedItem();
            if (selectedRessource != null) {
                Ressource ressource = Ressource.trouverRessourceParNom(selectedRessource);
                if (ressource != null) {
                    String[] notions = ressource.getNomsNotions();
                    new Evaluation(notions);
                    CreerEvaluation.this.dispose();
                }
            }
        });

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new CreerEvaluation();
    }
}
