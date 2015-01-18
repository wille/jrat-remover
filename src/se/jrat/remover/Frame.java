package se.jrat.remover;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;

import java.awt.Desktop;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	public static Frame instance;
	
	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private JButton btnDelete;
	
	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}
	
	public Renderer getRenderer() {
		return (Renderer) table.getDefaultRenderer(Object.class);
	}

	public Frame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/icon.png")));
		instance = this;
		setTitle("jRAT Remover - " + Main.getVersion());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		table = new JTable();
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new Renderer());
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name/Startup file", "Value/File"
			}
		));
		table.getColumnModel().getColumn(1).setPreferredWidth(319);
		scrollPane.setViewportView(table);
		
		JButton btnScan = new JButton("Scan");
		btnScan.setIcon(new ImageIcon(Frame.class.getResource("/icons/scanner--arrow.png")));
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.remover.scan();
			}
		});
		
		btnDelete = new JButton("Delete instances");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.fixer.fix();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(Frame.class.getResource("/icons/scanner--minus.png")));
		
		JLabel lblHttpredpoisncom = new JLabel("http://redpois0n.com");
		lblHttpredpoisncom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("http://redpois0n.com"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		lblHttpredpoisncom.setForeground(Color.BLUE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblHttpredpoisncom)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDelete)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnScan, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
					.addGap(3))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnScan)
						.addComponent(btnDelete)
						.addComponent(lblHttpredpoisncom))
					.addGap(7))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public JButton getFixButton() {
		return btnDelete;
	}
}
