/**
 * Clase encargada de realizar la ejecucion paso a paso del algoritmo de Bernstein.
 * @author aortega
 *
 */
package org.utp.fmin;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Fmin {
	private ClassSet attributes = new ClassSet();
	private DepSet dependencies = new DepSet();

	// Agregamos los atributos al objeto para procesar luego
	private boolean loadAttributes(String listaDeAtributos) {
		attributes.clear();
		String s = listaDeAtributos;
		StringTokenizer st = new StringTokenizer(s, ";\n");
		String data = "";
		if (st.hasMoreTokens()) {
			data = st.nextToken();
			st = new StringTokenizer(data, ",");
			while (st.hasMoreTokens()) {
				attributes.add(st.nextToken());
			}
			return true;
		} else
			return false;
	}

	private boolean loadDependencies(String listaDeDependencia) {
		dependencies.clear();
		String s = listaDeDependencia;
		StringTokenizer line = new StringTokenizer(s, ";\n");
		StringTokenizer dep;
		StringTokenizer at;
		String aDep = "";
		if (!line.hasMoreTokens()) // Verificamos si existen dependencias
			return false;
		while (line.hasMoreTokens()) { // si existe linea por analizar hacemos:
			aDep = line.nextToken(); // obtenemos la primera linea
			// proc one dep
			dep = new StringTokenizer(aDep, ">"); // Dividimos las dependencias
													// Izq y der
			FunDep d;
			ClassSet x = new ClassSet(); // lado izquierdo de las dependencias
			ClassSet y = new ClassSet(); // lado derecho de las dependencias
			if (dep.hasMoreTokens()) { // analizamos el lado IZQ de la
										// dependencia
				at = new StringTokenizer(dep.nextToken(), ","); // obtenemos
																// cada uno de
																// los atributos
				while (at.hasMoreTokens()) { // agregamos los atributos a nuetra
												// colecccion de objetos
					String at_tmp = at.nextToken();
					x.add(at_tmp);
					attributes.add(at_tmp);

				}
			} else
				return false;
			if (dep.hasMoreTokens()) {// analizamos el lado DER de la
										// dependencia
				at = new StringTokenizer(dep.nextToken(), ",");
				while (at.hasMoreTokens()) {
					String at_tmp = at.nextToken();
					y.add(at_tmp);
					attributes.add(at_tmp);
				}
			} else
				return false;
			d = new FunDep(x, y);
			dependencies.add(d);
		}// while
		return true;
	}

	public String ejecuta(String atributos, String dependencias) {

		String Salida = "";
		if (this.loadDependencies(dependencias)) {
			Salida = "Paso #1: Conjunto de dependencias funcionales originales:\n";
			Salida += dependencies.toString() + "\n";
			Salida += "Paso #2: Obtener el Lado Derecho Simple:\n";
			DepSet dp2 = dependencies.lds(); // Lado derecho simple
			Salida += dp2.toString() + "\n";
			Salida += "Paso #3: Eliminar Depedencias Redundantes:\n";
			DepSet dp3 = dp2.mcnr(); // Elimina Dependencias Redundantes
			Salida += dp3.toString() + "\n";
			Salida += "Paso #4: Eliminar atributos Izq Redundantes:\n";
			DepSet dp4 = dp3.fmin2();
			Salida += dp4.toString() + "\n";
			Salida += "Paso #5: Llaves candidatas\n";
			Salida += "Conjunto de atributos:  " + attributes.toString() + "\n";
			ClassSet cc = attributes.llavesCandidatas(dp4);
			Salida += cc.toString() + "\n";
			Salida += "Paso #6: Reunion DF con el mismo lado Izquierdo\n";
			DepSet dp5 = dp4.reunionDeDependencias();
			Salida += dp4.reunionDeDependencias() + "\n";
			Salida += "Paso #7: Crear Tablas\n";
			Salida += dp5.formatoTabla(dp5) + "\n";
			Salida += "Paso #8: Losless Descomposición\n";
			if (dp5.depLoss(dp5, cc)) {
				System.out.println("!Hay perdida!");
				Salida += dp5.formatoTabla(dp5) + "\n";
				Salida += this.agregaCandidata(cc);
			}

		} else
			Salida += "Error cargando atributos o dependencias!";
		return Salida;
	}
	// metodo para crear la tabla adicional( si alguna de llave candidata no aparece en las tablas creadas en paso 7)
	public String agregaCandidata(ClassSet cc) {
		String strTabla = "";
		// Iterator i = cc.iterator();
		System.out.println(cc.toHumanFormat());
		String key = cc.elementAt(0).toString();
		key = key.substring(0, key.length() - 1);
		key = key.substring(1, key.length());
		System.out.println(key);
		String tmp[] = key.split(",");
		strTabla += "\nCREATE TABLE R (\n";
		for (int i = 0; i < tmp.length; i++) {
			strTabla = strTabla + "      " + tmp[i] + "  INTEGER NOT NULL ,\n";
			// if (i < (this.size() - 1))
			// s = s + ",\n";
		}
		strTabla += " PRIMARY KEY ( ";
		for (int k = 0; k < tmp.length; k++) {
			strTabla += tmp[k];
			if (k < (tmp.length - 1))
				strTabla = strTabla + ",";
		}
		strTabla += "));";
		// ClassSet c = f.toRelation();
		// strTabla += "\nCREATE TABLE R(\n"+i.next()+ ");";

		return strTabla;

	}
}
