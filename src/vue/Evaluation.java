package vue;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class Evaluation extends JFrame
{
    private DefaultTableModel model;
    private boolean isUpdating = false;

    public Evaluation(String[] notions)
    {
        setTitle("Évaluation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        String[] columnNames = { "Notion", "Sélectionner", "TF", "F", "M", "D" };
        Object[][] data = new Object[notions.length][6];
        for (int i = 0; i < notions.length; i++) {
            data[i][0] = notions[i];
            data[i][1] = false;
            data[i][2] = "";
            data[i][3] = "";
            data[i][4] = "";
            data[i][5] = "";
        }

        model = new DefaultTableModel(data, columnNames)
        {
            public Class<?> getColumnClass(int columnIndex)
            {
                if (columnIndex == 1) {
                    return Boolean.class;
                }
                return String.class;
            }

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

        model.addRow(new Object[] { "Total", false, "", "", "", "" });

        model.addTableModelListener(new TableModelListener()
        {
            public void tableChanged(TableModelEvent e)
            {
                if (!isUpdating)
                {
                    calculateSums();
                }
            }
        });

        JTable table = new JTable(model);
        table.setRowHeight(25);

        TableColumn tfColumn = table.getColumnModel().getColumn(2);
        tfColumn.setCellEditor(new DefaultCellEditor(new JTextField()));

        TableCellRenderer colorRenderer = new ColorCircleRenderer();
        table.getColumnModel().getColumn(2).setCellRenderer(colorRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(colorRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(colorRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(colorRenderer);

        TableCellRenderer checkBoxRenderer = new CheckBoxRenderer();
        table.getColumnModel().getColumn(1).setCellRenderer(checkBoxRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton generateButton = new JButton("Générer l'évaluation");
        generateButton.addActionListener(e -> generateEvaluation());

        bottomPanel.add(generateButton, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

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

        for (int col = 2; col < columnCount; col++)
        {
            model.setValueAt(String.valueOf(sums[col]), rowCount - 1, col);
        }

        int totalSum = 0;
        for (int col = 2; col < columnCount; col++)
        {
            totalSum += sums[col];
        }
        model.setValueAt("Total: " + totalSum, rowCount - 1, 0);
        isUpdating = false;
    }

    private void generateEvaluation()
    {
        JOptionPane.showMessageDialog(this, "Évaluation générée !");
    }
}

class ColorCircleRenderer extends JLabel implements TableCellRenderer
{
    private int column;

    public ColorCircleRenderer()
    {
        setOpaque(false);
        setHorizontalAlignment(CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column)
    {
        this.column = column;
        setText(value != null ? value.toString() : "");
        return this;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (!getText().isEmpty())
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getColorForColumn(column));
            int diameter = Math.min(getWidth(), getHeight()) - 5;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            g2d.fillOval(x, y, diameter, diameter);
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(getText());
            int textHeight = fm.getAscent();
            g2d.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 3);
        }
    }

    private Color getColorForColumn(int column)
    {
        switch (column)
        {
            case 2:
                return Color.GREEN;
            case 3:
                return Color.CYAN;
            case 4:
                return Color.RED;
            case 5:
                return Color.GRAY;
            default:
                return Color.WHITE;
        }
    }
}

class CheckBoxRenderer extends JCheckBox implements TableCellRenderer
{
    public CheckBoxRenderer()
    {
        setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column)
    {
        if (row == table.getRowCount() - 1)
        {
            return new JLabel("");
        }
        else
        {
            setSelected(value != null && (Boolean) value);
            return this;
        }
    }
}
