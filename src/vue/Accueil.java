package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil extends JFrame implements ActionListener
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private JButton btn1, btn2, btn3;

	private JLabel lbl1;
	
	private JPanel panel1, panel2;


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Accueil()
	{
		setTitle("Accueil");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel supérieur avec le titre
		panel1 = new JPanel();
		lbl1 = new JLabel("Bienvenue dans l'Application QCM Builder");
		lbl1.setFont(new Font("Arial", Font.BOLD, 24));
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel1.add(lbl1);

		// Panel central avec les boutons
		panel2 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.CENTER;

		btn1 = new JButton("Créer un QCM");
		btn2 = new JButton("Créer une question");
		btn3 = new JButton("Paramètres");

		btn1.setFont(new Font("Arial", Font.PLAIN, 18));
		btn2.setFont(new Font("Arial", Font.PLAIN, 18));
		btn3.setFont(new Font("Arial", Font.PLAIN, 18));

		Dimension buttonSize = new Dimension(300, 50);
		btn1.setPreferredSize(buttonSize);
		btn2.setPreferredSize(buttonSize);
		btn3.setPreferredSize(buttonSize);

		btn1.addActionListener(e -> System.out.println("Créer un QCM"));
		btn2.addActionListener(e -> System.out.println("Créer une question"));
		btn3.addActionListener(e -> System.out.println("Paramètres"));

		panel2.add(btn1, gbc);
		panel2.add(btn2, gbc);
		panel2.add(btn3, gbc);

		// Ajouter les panels au frame
		setLayout(new BorderLayout());
		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.CENTER);

		setVisible(true);	
	}


	public void actionPerformed ( ActionEvent e )
	{
		
	}
	
	public static void main(String[] args) {
		new Accueil();
	}
}