package se.jrat.remover;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private JButton btnDelete;
	private DefaultTableModel model;
	
	public Renderer getRenderer() {
		return (Renderer) table.getDefaultRenderer(Object.class);
	}

	public Frame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/icon.png")));
		setTitle("jRAT Remover - " + Main.getVersion());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		model = new DefaultTableModel(null, new String[] { "Name/Startup file", "Value/File" }) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		
		table = new JTable();
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new Renderer());
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(319);
		scrollPane.setViewportView(table);
		
		JButton btnScan = new JButton("Scan");
		btnScan.setIcon(new ImageIcon(Frame.class.getResource("/icons/scanner--arrow.png")));
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run(true);
			}
		});
		
		btnDelete = new JButton("Delete instances");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run(false);
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(Frame.class.getResource("/icons/scanner--minus.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnDelete)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnScan, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
					.addGap(3))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnScan)
						.addComponent(btnDelete))
					.addGap(7))
		);
		contentPane.setLayout(gl_contentPane);
		
		setLocationRelativeTo(null);
	}
	
	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	public void run(boolean dryrun) {
		clear();
		
		List<Detection> detections = Main.remover.perform(dryrun);
		
		for (Detection d : detections) {
			model.addRow(new Object[] { d.getKey(), d.getValue() });
		}
		
		btnDelete.setEnabled(detections.size() > 0);
		if (detections.size() == 0) {
			Utils.info("jRAT Remover", "No results found when scanning!");
		} else {
			Utils.err("jRAT Remover", "Found " + detections.size() + " stubs while scanning!");
		}
	}
}
