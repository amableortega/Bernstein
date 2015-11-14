package org.utp.gestiona;

import org.utp.fmin.Fmin;

public class Interfaz extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7491550698972740060L;
	
	

	// instancia de clase
	private XFichero file;

	// Elementos de la interzas
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuSalir;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPopupMenu.Separator jSeparator1;
	private javax.swing.JMenuItem mAbrir;

	private javax.swing.JMenuItem mGuardarComo;
	private javax.swing.JMenuItem mSgte;
	
	

	// End of variables declaration//GEN-END:variables

	/** Creates new form interfaz */
	public Interfaz() {
	
		
		initComponents();
		this.setLocationRelativeTo(null);
		// crea la instancia pasando como parametros los controles JTextField
		file = new XFichero();
		this.setTitle("Gestion de Archivos");
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		this.setSize(800,600); 
		this.setResizable(true); 
		this.setVisible(true); 
		
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();

		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		mAbrir = new javax.swing.JMenuItem();

		mGuardarComo = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JPopupMenu.Separator();
		jMenuSalir = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		mSgte = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jPanel1.setLayout(new java.awt.GridBagLayout());

		jMenu1.setText("Archivo");

		mAbrir.setText("Cargar Dependencias  *.dep");
		mAbrir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mAbrirActionPerformed(evt);
			}
		});
		jMenu1.add(mAbrir);

		mGuardarComo.setText("Generar F+...");
		mGuardarComo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mGuardarComoActionPerformed(evt);
			}
		});
		jMenu1.add(mGuardarComo);
		jMenu1.add(jSeparator1);

		jMenuSalir.setText("Salir");
		jMenu1.add(jMenuSalir);

		jMenuBar1.add(jMenu1);

		mSgte.setText("Siguiente");
		mSgte.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mSgteActionPerformed(evt);
			}
		});
		jMenu2.setText("Herramientas");
		jMenu2.add(mSgte);

		//jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void mGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mGuardarComoActionPerformed
		Fmin fmin = new Fmin();
		String datos = fmin.ejecuta("", this.file.getDepFun());
		this.file.GuardarComo(datos);
		// this.setTitle("" + file.getFileName() + "");
	}// GEN-LAST:event_mGuardarComoActionPerformed

	private void mAbrirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mAbrirActionPerformed
		this.file.Abrir();
		this.setTitle("" + file.getFileName() + "");
	}// GEN-LAST:event_mAbrirActionPerformed

	private void mSgteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mSgteActionPerformed
	// file.Siguiente();
	}// GEN-LAST:event_mSgteActionPerformed

	public javax.swing.JLabel getjLabel1() {
		return jLabel1;
	}

	public void setjLabel1(javax.swing.JLabel jLabel1) {
		this.jLabel1 = jLabel1;
	}

	public javax.swing.JLabel getjLabel2() {
		return jLabel2;
	}

	public void setjLabel2(javax.swing.JLabel jLabel2) {
		this.jLabel2 = jLabel2;
	}

	public javax.swing.JLabel getjLabel3() {
		return jLabel3;
	}

	public void setjLabel3(javax.swing.JLabel jLabel3) {
		this.jLabel3 = jLabel3;
	}

	public javax.swing.JLabel getjLabel4() {
		return jLabel4;
	}

	public void setjLabel4(javax.swing.JLabel jLabel4) {
		this.jLabel4 = jLabel4;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Interfaz().setVisible(true);
			}
		});
	}

}
