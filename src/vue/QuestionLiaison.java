package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modele.Question;

public class QuestionLiaison extends JFrame
{
	private JPanel mainPanel;
	private JPanel questionPanel;
	private JPanel buttonPanel;
	private JButton enregistrerButton;
	private JButton ajouterButton;
	private JTextArea sujetTextArea;
	private int ajoutCounter = 0; // Compteur pour le nombre d'ajouts

	public QuestionLiaison(Question question)
	{
		mainPanel = new JPanel(new BorderLayout());
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		ajouterButton = new JButton("Ajouter");
		ajouterButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (ajoutCounter < 5)
				{
					ajoutCounter++;
					// Code pour ajouter un nouvel élément
					JPanel newElement = new JPanel();
					newElement.setLayout(new FlowLayout());

					// Ajouter une icône au début
					ImageIcon startIconImage = new ImageIcon(
							new ImageIcon("/home/saiiko/IUT/QCMBuilder3.0/QCMBuilder/lib/icones/delete.png").getImage()
									.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
					JLabel startIcon = new JLabel(startIconImage);
					startIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					startIcon.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							questionPanel.remove(newElement);
							questionPanel.revalidate();
							questionPanel.repaint();
							ajoutCounter--;
						}
					});
					newElement.add(startIcon);

					newElement.add(new JLabel("Question " + ajoutCounter + ":"));
					newElement.add(new JTextField(10));
					newElement.add(new JLabel("Réponse " + ajoutCounter + ":"));
					newElement.add(new JTextField(10));

					// Ajouter une icône à la fin
					ImageIcon endIconImage = new ImageIcon(
							new ImageIcon("/home/saiiko/IUT/QCMBuilder3.0/QCMBuilder/lib/icones/delete.png").getImage()
									.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
					JLabel endIcon = new JLabel(endIconImage);
					endIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					endIcon.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							questionPanel.remove(newElement);
							questionPanel.revalidate();
							questionPanel.repaint();
							ajoutCounter--;
						}
					});
					newElement.add(endIcon);

					questionPanel.add(newElement);
					questionPanel.revalidate();
					questionPanel.repaint();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajouter plus de 5 éléments.");
				}
			}
		});
		buttonPanel = new JPanel();
		buttonPanel.add(ajouterButton);
		enregistrerButton = new JButton("Enregistrer");
		enregistrerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Logique pour enregistrer les questions et les réponses
				String sujet = sujetTextArea.getText();
				System.out.println("Sujet: " + sujet);

				for (Component component : questionPanel.getComponents())
				{
					if (component instanceof JPanel)
					{
						JPanel panel = (JPanel) component;
						JTextField questionField = (JTextField) panel.getComponent(2);
						JTextField reponseField = (JTextField) panel.getComponent(4);
						String question = questionField.getText();
						String reponse = reponseField.getText();
						// Enregistrer la question et la réponse
						System.out.println("Question: " + question + ", Réponse: " + reponse);
					}
				}
			}
		});
		buttonPanel.add(enregistrerButton);
		mainPanel.add(new JScrollPane(questionPanel), BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		sujetTextArea = new JTextArea(5, 20);
		mainPanel.add(new JScrollPane(sujetTextArea), BorderLayout.NORTH);
		add(mainPanel);
		setTitle("Question Liaison");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}