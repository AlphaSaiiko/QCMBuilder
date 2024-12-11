package vue;

import javax.swing.*;
import java.awt.*;

public class QuestionReponseUnique extends JFrame
{
	public QuestionReponseUnique()
	{
		// Créer le panel principal
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Panel pour la question
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

		// Ajouter le label "Question"
		JLabel questionLabel = new JLabel("Question");
		questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		questionPanel.add(questionLabel);

		// Ajouter un champ de texte pour écrire la question
		JTextArea questionArea = new JTextArea(10, 80);
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setFont(new Font("Arial", Font.PLAIN, 18));
		questionArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, questionArea.getPreferredSize().height));
		questionPanel.add(questionArea);

		// Ajouter un panel pour les images de poubelles, champ de texte et case
		// à cocher
		JPanel trashPanel1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.anchor = GridBagConstraints.WEST;

		// Redimensionner l'icône de poubelle
		ImageIcon trashIcon = new ImageIcon(new ImageIcon("QCMBuilder/lib/icones/delete.png").getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JLabel trashLabel1 = new JLabel(trashIcon);
		trashPanel1.add(trashLabel1, gbc1);

		gbc1.gridx = 1;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.weightx = 1.0;

		// Augmenter la taille du JTextField
		JTextField textField1 = new JTextField(50);
		textField1.setFont(new Font("Arial", Font.PLAIN, 18));
		trashPanel1.add(textField1, gbc1);

		gbc1.gridx = 2;
		gbc1.fill = GridBagConstraints.NONE;
		gbc1.weightx = 0;

		// Augmenter la taille de la case à cocher
		JCheckBox checkBox1 = new JCheckBox();
		checkBox1.setPreferredSize(new Dimension(30, 30));
		trashPanel1.add(checkBox1, gbc1);

		questionPanel.add(trashPanel1);

		// Ajouter un deuxième panel pour les images de poubelles, champ de
		// texte et case à cocher
		JPanel trashPanel2 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(5, 5, 5, 5);
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.anchor = GridBagConstraints.WEST;

		JLabel trashLabel2 = new JLabel(trashIcon);
		trashPanel2.add(trashLabel2, gbc2);

		gbc2.gridx = 1;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.weightx = 1.0;

		JTextField textField2 = new JTextField(50);
		textField2.setFont(new Font("Arial", Font.PLAIN, 18));
		trashPanel2.add(textField2, gbc2);

		gbc2.gridx = 2;
		gbc2.fill = GridBagConstraints.NONE;
		gbc2.weightx = 0;

		JCheckBox checkBox2 = new JCheckBox();
		checkBox2.setPreferredSize(new Dimension(30, 30));
		trashPanel2.add(checkBox2, gbc2);

		questionPanel.add(trashPanel2);

		// Ajouter le panel de question au panel principal
		mainPanel.add(questionPanel, BorderLayout.NORTH);

		// Ajouter tout dans le conteneur principal
		add(mainPanel, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Réponse Unique");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(QuestionReponseUnique::new);
	}
}