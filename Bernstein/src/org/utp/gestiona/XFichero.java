package org.utp.gestiona;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.filechooser.FileNameExtensionFilter;

public class XFichero {

	private JFileChooser fileChooser;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"Archivo de Dependencias (*.dep)", "dep");
	private File file = null;
	private boolean isopen = false;// bandera de control para saber si se abrio
									// un archivo
	private ArrayList<String> contenido = new ArrayList<String>();// almacena los registros
													// leidos de *.dep
	private String depFun = "";

	// Constructor de clase
	public XFichero() {
	}

	// Retorna el nombre del archivo abierto
	public String getFileName() {
		if (file != null)
			return file.getName();
		else
			return "Sin Titulo";
	}

	/*
	 * Abre la caja de dialogo Guardar como Input: String de las dependencias
	 * funcionales minimas
	 */
	public void GuardarComo(String texto) {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.isopen = false;
			this.contenido.clear();

			if (escribir(fileChooser.getSelectedFile(), texto)) {
				JOptionPane.showMessageDialog(null, "Archivo '"
						+ fileChooser.getSelectedFile().getName()
						+ "' guardado ");
				this.isopen = true;
			}
		}
	}

	/*
	 * Actualiza nuevo registro al final de la lista input: String de la forma
	 * "campo1,campo2,campo3"
	 */
	public void Actualizar(String texto) {
		// Si existe archivo abierto
		if (this.file != null) {
			if (escribir(this.file, texto)) {
				JOptionPane.showMessageDialog(null,
						"Archivo '" + this.file.getName() + "' actualizado ");
			}
		} else // sino crear nuevo archivo
		{
			GuardarComo(texto);
		}
	}

	/*
	 * Muestra la ventana de dialogo Abrir archivo
	 */
	public void Abrir() {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		// fileChooser.setCurrentDirectory(new java.io.File("e:/"));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			leer(this.file);
			this.isopen = true;
		}
	}

	/*
	 * Función que escribe un registro en el archivo de texto Si el archivo ya
	 * contaba con registros re-escribe estos y al final escribe el nuevo
	 * registro
	 */
	private boolean escribir(File fichero, String texto) {
		boolean res = false;
		PrintWriter writer = null;
		try {
			String f = fichero.toString();
			// verifica que extension exista sino lo agrega
			if (!f.substring(f.length() - 4, f.length()).equals(".dep")) {
				f = f + ".dep";
				fichero = new File(f);
			}
			writer = new PrintWriter(fichero);
			// si hay un archivo abierto
			if (this.isopen) {
				writer.println(texto);
				this.contenido.add(texto);
			} else // esta guardando por primera vez
			{
				this.contenido.add(texto);
				writer.println(texto);
			}
			this.file = fichero;
			writer.close();
			res = true;
		} catch (FileNotFoundException ex) {
			System.out.println("Error:" + ex);
		} finally {
			writer.close();
		}
		return res;
	}

	/*
	 * Lee linea por linea un archivo de texto y almacena los registros en un
	 * ArrayList segun orden de lectura input: File
	 */
	public boolean leer(File fichero) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fichero));
			this.contenido.clear();
			this.depFun="";
			String linea;
			while ((linea = reader.readLine()) != null) {
				this.contenido.add(linea);
				this.depFun += linea + "\n";
			}

			return true;
		} catch (IOException ex) {
			System.out.println("Error: " + ex);
		} finally {
			try {
				reader.close();
			} catch (IOException ex) {
				System.out.println("Error: " + ex);
			}
		}
		return false;
	}

	public String getDepFun() {
		return depFun;
	}

	public void setDepFun(String depFun) {
		this.depFun = depFun;
	}

}
