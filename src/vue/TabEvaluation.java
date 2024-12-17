package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import modele.Ressource;
import modele.Evaluation;
import modele.Notion;

public class TabEvaluation extends JFrame
{
	private DefaultTableModel model;
	private boolean isUpdating = false;
	private CreerQuestion creerQuestion;

	public TabEvaluation(Ressource ressource)
	{
		this.creerQuestion = new CreerQuestion();

		// Créer la fenêtre principale
		setTitle("Évaluation");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 300);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		// Modèle pour le tableau
		String[] columnNames = { "Notion", "Sélectionner", "TF", "F", "M", "D" };
		model = new DefaultTableModel(columnNames, 0)
		{
			@Override
			public Class<?> getColumnClass(int columnIndex)
			{
				if (columnIndex == 1)
				{
					return Boolean.class;
				}
				return String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column)
			{
				if (row == getRowCount() - 1 && column == 1)
				{
					return false;
				}
				if (column != 1 && !(Boolean) getValueAt(row, 1))
				{
					return false;
				}
				return column == 1 || column > 1;
			}
		};

		// Récupérer les notions de la ressource
		for (Notion notion : ressource.getEnsNotions())
		{
			model.addRow(new Object[] { notion.getNom(), false, "", "", "", "" });
		}

		// Ajouter une ligne pour le résumé
		model.addRow(new Object[] { "Total", false, "", "", "", "" });

		// Ajouter un TableModelListener pour mettre à jour les sommes
		model.addTableModelListener(e -> {
			if (!isUpdating)
			{
				calculateSums();
			}
		});

		// Tableau
		JTable table = new JTable(model);
		table.setRowHeight(25);

		// Éditeurs personnalisés pour les colonnes
		TableColumn tfColumn = table.getColumnModel().getColumn(2);
		tfColumn.setCellEditor(new NumericCellEditor());
		table.getColumnModel().getColumn(3).setCellEditor(new NumericCellEditor());
		table.getColumnModel().getColumn(4).setCellEditor(new NumericCellEditor());
		table.getColumnModel().getColumn(5).setCellEditor(new NumericCellEditor());

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
		generateButton.addActionListener(e -> generateTabEvaluation(ressource));

		bottomPanel.add(generateButton, BorderLayout.CENTER);

		add(bottomPanel, BorderLayout.SOUTH);

		// Rendre la fenêtre visible
		setVisible(true);

		// Calculer les sommes initiales
		calculateSums();
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
		for (int row = 0; row < model.getRowCount() - 1; row++)
		{
			Boolean isSelected = (Boolean) model.getValueAt(row, 1);
			if (isSelected)
			{
				String notionName = (String) model.getValueAt(row, 0);
				selectedNotions.add(new Notion(notionName, ressource));
			}
		}

		if (selectedNotions.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins une notion.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Créer l'objet Evaluation
		Evaluation evaluation = new Evaluation(ressource, selectedNotions.get(0)); // Exemple
																					// :
																					// la
																					// première
																					// notion
		for (Notion notion : selectedNotions)
		{
			evaluation.ajouterNotion(notion);
		}

		// Envoyer l'évaluation
		sendEvaluation(evaluation);
	}

	private void sendEvaluation(Evaluation evaluation)
	{
		JOptionPane.showMessageDialog(this, "Évaluation générée et envoyée avec succès : " + evaluation.getLienEval());
	}

	// Classes internes pour les renderers et éditeurs (identiques à votre code)
	class ColorCircleRenderer extends JLabel implements TableCellRenderer
	{
		private final Color[] colors = { Color.GREEN, Color.CYAN, Color.RED, Color.GRAY };

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{
			setText((String) value);
			setOpaque(false);
			setHorizontalAlignment(SwingConstants.CENTER);

			return new JComponent()
			{
				@Override
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

	class NumericCellEditor extends DefaultCellEditor
	{
		private final JTextField textField;

		public NumericCellEditor()
		{
			super(new JTextField());
			textField = (JTextField) getComponent();
		}

		@Override
		public boolean stopCellEditing()
		{
			String value = textField.getText();
			try
			{
				Integer.parseInt(value);
			} catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return super.stopCellEditing();
		}
	}

	class CheckBoxRenderer extends JCheckBox implements TableCellRenderer
	{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{
			setSelected((Boolean) value);
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	}
}
