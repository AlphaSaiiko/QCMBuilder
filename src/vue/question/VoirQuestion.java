package vue.question;

import java.awt.*;
import javax.swing.*;

import controleur.Controleur;
import modele.option.Option;
import modele.option.OptionAssociation;
import modele.option.OptionElimination;
import modele.Question;

public class VoirQuestion extends JFrame 
{
	/**
	 * +----------+
	 * | ATTRIBUT |
	 * +----------+
	 */

	private Question question;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public VoirQuestion(Question question) 
	{
		this.question = question;

		this.setTitle                (question.getEnonce());
		this.setSize                 (800, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo   (null);
		this.setLayout               (new GridBagLayout());


		// Panel pour les attributs avec GridBagLayout
		JPanel panelAttributs = new JPanel();
		panelAttributs.setLayout(new GridBagLayout());


		// Contrainte commune pour chaque composant
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(10, 10, 10, 10);
		gbc.weightx = 1.0;


		// Ligne 1 : Panel Points
		gbc.gridx   = 0; // Colonne 1
		gbc.gridy   = 0; // Ligne 1
		gbc.weightx = 0.5;
		panelAttributs.add(new JLabel("Nombre de points"), gbc);

		gbc.gridx = 1; // Colonne 2
		panelAttributs.add(new JLabel(String.valueOf(question.getNbPoints())), gbc);


		// Ligne 2 : Panel Temps
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelAttributs.add(new JLabel("Temps de réponse (min : sec)"), gbc);

		gbc.gridx = 1; // Colonne 2
		String minutes  = String.format("%02d", question.getTemps() / 60);
		String secondes = String.format("%02d", question.getTemps() % 60);
		panelAttributs.add(new JLabel(minutes + ":" + secondes), gbc);


		// Ligne 3 : Panel Difficulté
		gbc.gridx = 0;
		gbc.gridy = 2;
		panelAttributs.add(new JLabel("Difficulté"), gbc);

		gbc.gridx = 1; // Colonne 2
		String difficulte = switch (question.getDifficulte()) 
		{
			case 1 -> "Très facile";
			case 3 -> "Moyenne";
			case 4 -> "Fifficile";
			default -> "Facile";
		};
		panelAttributs.add(new JLabel(difficulte), gbc);


		// Ligne 4 : Panel Type
		gbc.gridx = 0;
		gbc.gridy = 3;
		panelAttributs.add(new JLabel("Type"), gbc);

		gbc.gridx = 1; // Colonne 2
		String type = switch (question.getType()) 
		{
			case "QCMRM" -> "Réponses Multiples";
			case "QAE"   -> "Association";
			case "QAEPR" -> "Elimination";
			default      -> "Réponses Uniques";
		};
		panelAttributs.add(new JLabel(type), gbc);


		// Ajouter les options en dessous
		GridBagConstraints gbcLigne = new GridBagConstraints();

		gbcLigne.fill      = GridBagConstraints.HORIZONTAL;
		gbcLigne.insets    = new Insets(5, 5, 5, 5);
		gbcLigne.gridx     = 0; // Colonne 1
		gbcLigne.gridy     = 4;
		gbcLigne.gridwidth = 2;
		gbcLigne.weightx   = 1.0;

		JPanel panelLigne = new JPanel(new GridBagLayout());

		type = question.getType();

		for (int cpt = 0; cpt < question.getEnsOptions().size(); cpt++) 
		{
			JLabel optionLabel = new JLabel(question.getEnsOptions().get(cpt).getEnonce());
			optionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
			gbc.gridx = 0;
			gbc.gridy = cpt;

			if (type.equals("QAEPR"))
			{
				panelLigne.add(optionLabel, gbc);

				JLabel ordrePointsLabel  = new JLabel(
					"\t Ordre  : " + ((OptionElimination) question.getEnsOptions().get(cpt)).getOrdre()         +
					"  Points : "  + ((OptionElimination) question.getEnsOptions().get(cpt)).getNbPointsMoins()
				);

				ordrePointsLabel.setFont(new Font("Arial", Font.PLAIN, 18));

				JToggleButton boutonOption;
				boutonOption = new JRadioButton();
				
				boutonOption.setSelected(((OptionElimination) (question.getEnsOptions().get(cpt))).getEstReponse());
				boutonOption.setEnabled(false);

				gbc.gridx = 1;

				if (! boutonOption.isSelected())
					panelLigne.add(ordrePointsLabel, gbc);
				else
					panelLigne.add(new JLabel(), gbc);
				
				gbc.gridx  = 2;
				gbc.anchor = GridBagConstraints.EAST;
				panelLigne.add(boutonOption, gbc);
			}

			if (type.equals("QCMRU") || type.equals("QCMRM"))
			{
				panelLigne.add(optionLabel, gbc);
				JToggleButton boutonOption;

				if (type.equals("QCMRU"))
					boutonOption = new JRadioButton();
				else 
					boutonOption = new JCheckBox   ();

				boutonOption.setSelected(((Option) (question.getEnsOptions().get(cpt))).getEstReponse());
				boutonOption.setEnabled(false);
				
				gbc.gridx  = 1;
				gbc.anchor = GridBagConstraints.EAST;
				panelLigne.add(boutonOption, gbc);
			}

			if (type.equals("QAE") && cpt % 2 == 0)
			{
				optionLabel.setText(optionLabel.getText() + "->");

				panelLigne.add(optionLabel, gbc);
				
				JLabel associeLabel = new JLabel(  "<-" + ((OptionAssociation)(question.getEnsOptions().get(cpt))).getAssocie().getEnonce());
				associeLabel.setFont(new Font("Arial", Font.PLAIN, 18));

				gbc.gridx = 1;
				panelLigne.add(associeLabel, gbc);
			}
		}

		gbcLigne.gridy = 5;
		panelAttributs.add(panelLigne, gbcLigne);

		GridBagConstraints gbcPanelAttributs = new GridBagConstraints();
		gbcPanelAttributs.gridx   = 0                      ;
		gbcPanelAttributs.gridy   = 1                      ;
		gbcPanelAttributs.fill    = GridBagConstraints.BOTH;
		gbcPanelAttributs.weightx = 1.0                    ;
		gbcPanelAttributs.weighty = 1.0                    ;
		this.add(panelAttributs, gbcPanelAttributs);

		this.revalidate();
		this.repaint();
		this.setVisible(true);

	}
}
