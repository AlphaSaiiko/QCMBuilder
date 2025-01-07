package vue;

import controleur.Controleur;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import modele.CreationHTML;
import modele.Evaluation;
import modele.Notion;
import modele.Ressource;

public class TabEvaluation extends JFrame
{
	private DefaultTableModel model;
	private boolean isUpdating = false;
	private CreerQuestion creerQuestion;
	private boolean bChrono;
	private boolean bQuestion;

	public TabEvaluation(Ressource ressource, boolean bChrono) {
		// Créer la fenêtre principale
		setTitle("Évaluation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 300);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
	
		this.bChrono = bChrono;
		this.bQuestion = true;
	
		// Modèle pour le tableau
		String[] columnNames = { "Notion", "Sélectionner", "TF", "F", "M", "D" };
		model = new DefaultTableModel(columnNames, 0) {
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 1) {
					return Boolean.class;
				}
				return String.class;
			}
	
			public boolean isCellEditable(int row, int column) {
				if (row == getRowCount() - 1 && column == 1) {
					return false;
				}
				if (column != 1 && !(Boolean) getValueAt(row, 1)) {
					return false;
				}
				return column == 1 || column > 1;
			}
		};
	
		// Récupérer les notions de la ressource
		for (Notion notion : ressource.getEnsNotions()) {
			model.addRow(new Object[] { notion.getNom(), false, "", "", "", "" });
		}
	
		// Ajouter une ligne pour le résumé
		model.addRow(new Object[] { "Total", false, "", "", "", "" });
	
		// Ajouter un TableModelListener pour mettre à jour les sommes
		model.addTableModelListener(e -> {
			if (!isUpdating) {
				calculateSums();
			}
		});
	
		// Tableau
		JTable table = new JTable(model);
		table.setRowHeight(25);
	
		// Éditeurs personnalisés pour les colonnes
		TableColumn tfColumn = table.getColumnModel().getColumn(2);
		tfColumn.setCellEditor(new NumericCellEditor(ressource, this, table));
		table.getColumnModel().getColumn(3).setCellEditor(new NumericCellEditor(ressource, this, table));
		table.getColumnModel().getColumn(4).setCellEditor(new NumericCellEditor(ressource, this, table));
		table.getColumnModel().getColumn(5).setCellEditor(new NumericCellEditor(ressource, this, table));
	
		// Ajouter des renderers personnalisés
		TableCellRenderer colorRenderer = new ColorCircleRenderer();
		table.getColumnModel().getColumn(2).setCellRenderer(colorRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(colorRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(colorRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(colorRenderer);
	
		TableCellRenderer checkBoxRenderer = new CheckBoxRenderer();
		table.getColumnModel().getColumn(1).setCellRenderer(checkBoxRenderer);
	
		// Ajouter le tableau dans un panneau défilant
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	
		// Panneau du bas pour les résumés et bouton
		JPanel bottomPanel = new JPanel(new BorderLayout());
	
		// Bouton "Générer l'évaluation"
		JButton generateButton = new JButton("Générer l'évaluation");
		generateButton.addActionListener(e -> {



			generateTabEvaluation(ressource);
			if(this.bQuestion)
			{
				dispose(); // Fermer la fenêtre actuelle
				new Accueil(); // Ouvrir la page d'accueil
			}
		});
	
		bottomPanel.add(generateButton, BorderLayout.CENTER);
	
		add(bottomPanel, BorderLayout.SOUTH);
	
		// Rendre la fenêtre visible
		setVisible(true);
	
		// Calculer les sommes initiales
		calculateSums();
	}
	

	private int getNumberOfQuestions(Notion notion, String difficulty) {
		switch (difficulty) {
			case "TF":
				return notion.getNbQuestionTresFacile();
			case "F":
				return notion.getNbQuestionFacile();
			case "M":
				return notion.getNbQuestionMoyen();
			case "D":
				return notion.getNbQuestionDifficile();
			default:
				return 0;
		}
	}
	

	private void calculateSums()
	{
		isUpdating = true;
		int rowCount = model.getRowCount();
		int columnCount = model.getColumnCount();
		int[] sums = new int[columnCount];

		for (int row = 0; row < rowCount - 1; row++)
		{
			for (int col = 2; col < columnCount; col++)
			{
				String value = (String) model.getValueAt(row, col);
				if (value != null && !value.isEmpty())
				{
					try
					{
						sums[col] += Integer.parseInt(value);
					} catch (NumberFormatException e)
					{
						// Ignorer les valeurs non numériques
					}
				}
			}
		}

		// Mettre à jour la ligne de résumé
		for (int col = 2; col < columnCount; col++)
		{
			model.setValueAt(String.valueOf(sums[col]), rowCount - 1, col);
		}

		// Calculer la somme totale
		int totalSum = 0;
		for (int col = 2; col < columnCount; col++)
		{
			totalSum += sums[col];
		}
		model.setValueAt("Total: " + totalSum, rowCount - 1, 0);
		isUpdating = false;
	}

	private void generateTabEvaluation(Ressource ressource)
	{
		// Collecter les notions sélectionnées
		List<Notion> selectedNotions = new ArrayList<>();
		String chemin ="html";
		Evaluation evaluation = new Evaluation(ressource, this.bChrono, chemin);

		for (int row = 0; row < model.getRowCount() - 1; row++)
		{
			Boolean isSelected = (Boolean) model.getValueAt(row, 1);
			if (isSelected)
			{
				String notionName = (String) model.getValueAt(row, 0);
				Notion not = Notion.trouverNotionParNom(notionName, ressource);
				selectedNotions.add(not);

				// Récupérer les valeurs numériques des colonnes TF, F, M, D
				int tfValue = model.getValueAt(row, 2) != null && !((String) model.getValueAt(row, 2)).isEmpty() ? Integer.parseInt((String) model.getValueAt(row, 2)) : 0;
				int fValue = model.getValueAt(row, 3) != null && !((String) model.getValueAt(row, 3)).isEmpty() ? Integer.parseInt((String) model.getValueAt(row, 3)) : 0;
				int mValue = model.getValueAt(row, 4) != null && !((String) model.getValueAt(row, 4)).isEmpty() ? Integer.parseInt((String) model.getValueAt(row, 4)) : 0;
				int dValue = model.getValueAt(row, 5) != null && !((String) model.getValueAt(row, 5)).isEmpty() ? Integer.parseInt((String) model.getValueAt(row, 5)) : 0;

				if (tfValue == 0 && fValue == 0 && mValue == 0 && dValue == 0)
				{
					JOptionPane.showMessageDialog(this, "Veuillez entrer au moins une valeur pour la notion " + notionName + ".", "Erreur",
							JOptionPane.ERROR_MESSAGE);
					this.bQuestion = false;
					return;
				}


				Controleur.recupererQuestion(evaluation, not, tfValue, fValue, mValue, dValue);
			}
		}
		this.bQuestion = true;

		if (selectedNotions.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins une notion.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

					// Créer une fenêtre de dialogue pour la sélection d'un dossier
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Sélectionnez l'emplacement pour créer un dossier");
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Limiter la sélection aux dossiers
					
					int userSelection = fileChooser.showDialog(null, "Créer un dossier");
			
					// Si l'utilisateur a sélectionné un dossier
					File selectedDirectory = null;
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						selectedDirectory = fileChooser.getSelectedFile();
					} else {
						// Si l'utilisateur n'a pas sélectionné de dossier, utiliser le répertoire "Documents" de l'utilisateur
						selectedDirectory = new File(System.getProperty("user.home"), "Documents");
					}
			
					// Demander à l'utilisateur de saisir un nom pour le nouveau dossier
					String folderName = JOptionPane.showInputDialog("Entrez le nom du dossier :");
		
					chemin = "";
					
					if (folderName != null && !folderName.trim().isEmpty()) {
						// Créer le dossier dans l'emplacement choisi
						File newDirectory = new File(selectedDirectory, folderName);
						
						if (newDirectory.mkdir()) { // Utilisation de mkdir() pour créer un dossier
							JOptionPane.showMessageDialog(null, "Dossier créé avec succès : " + newDirectory.getAbsolutePath());
							chemin = newDirectory.getAbsolutePath();
						} else {
							JOptionPane.showMessageDialog(null, "Échec de la création du dossier. Vérifiez les permissions.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Le nom du dossier ne peut pas être vide.");
					}

					evaluation.setChemin(chemin);

		//HTML
		CreationHTML html = new CreationHTML(evaluation);
	}

	

	// Classes internes pour les renderers et éditeurs (identiques à votre code)
	class ColorCircleRenderer extends JLabel implements TableCellRenderer
	{
		private final Color[] colors = { Color.GREEN, Color.CYAN, Color.RED, Color.GRAY };

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{
			setText((String) value);
			setOpaque(false);
			setHorizontalAlignment(SwingConstants.CENTER);

			return new JComponent()
			{
				protected void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					Graphics2D g2d = (Graphics2D) g;

					int diameter = Math.min(getWidth(), getHeight());
					int x = (getWidth() - diameter) / 2;
					int y = (getHeight() - diameter) / 2;

					if (value != null && !value.toString().isEmpty())
					{
						try
						{
							Integer.parseInt(value.toString());
							g2d.setColor(colors[column - 2]);
							g2d.fillOval(x, y, diameter, diameter);
						} catch (NumberFormatException ignored)
						{
						}
					}

					g2d.setColor(Color.BLACK);
					FontMetrics fm = g2d.getFontMetrics();
					String text = value != null ? value.toString() : "";
					int textX = (getWidth() - fm.stringWidth(text)) / 2;
					int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
					g2d.drawString(text, textX, textY);
				}
			};
		}
	}

	class NumericCellEditor extends DefaultCellEditor {
		private final JTextField textField;
		private final Ressource ressource;
		private final TabEvaluation tabEvaluation;
		private final JTable table;
	
		public NumericCellEditor(Ressource ressource, TabEvaluation tabEvaluation, JTable table) {
			super(new JTextField());
			textField = (JTextField) getComponent();
			this.ressource = ressource;
			this.tabEvaluation = tabEvaluation;
			this.table = table;
		}
	
		public boolean stopCellEditing() {
			String value = textField.getText();
			try {
				int intValue = Integer.parseInt(value);
	
				// Récupérer la ligne courante et la colonne de la cellule éditée
				int row = table.getEditingRow();
				int column = table.getEditingColumn();
	
				// Récupérer la notion correspondant à la ligne
				String notionName = (String) model.getValueAt(row, 0);
				Notion notion = Notion.trouverNotionParNom(notionName, ressource);
	
				// Vérifier si la notion est trouvée
				if (notion == null) {
					JOptionPane.showMessageDialog(null, "La notion " + notionName + " est introuvable.", "Erreur",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
	
				// Définir la difficulté en fonction de la colonne
				String difficulty = "";
				switch (column) {
					case 2: difficulty = "TF"; break;
					case 3: difficulty = "F"; break;
					case 4: difficulty = "M"; break;
					case 5: difficulty = "D"; break;
				}
	
				// Récupérer le nombre de questions disponibles pour la notion et la difficulté
				int maxQuestions = tabEvaluation.getNumberOfQuestions(notion, difficulty);
	
				// Vérifier si la valeur entrée est supérieure au nombre de questions disponibles
				if (intValue > maxQuestions) {
					intValue = maxQuestions;
					textField.setText(String.valueOf(intValue));
					JOptionPane.showMessageDialog(null, "La valeur entrée dépasse le nombre de questions disponibles. Valeur ajustée à " + maxQuestions, "Erreur",
							JOptionPane.WARNING_MESSAGE);
				}
	
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return super.stopCellEditing();
		}
	}
	

	class CheckBoxRenderer extends JCheckBox implements TableCellRenderer
	{
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{
			setSelected((Boolean) value);
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	}
}
