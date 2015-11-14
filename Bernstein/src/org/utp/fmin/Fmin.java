package org.utp.fmin;

import java.awt.event.ActionEvent;
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
					x.add(at.nextToken());
				}
			} else
				return false;
			if (dep.hasMoreTokens()) {// analizamos el lado DER de la
										// dependencia
				at = new StringTokenizer(dep.nextToken(), ",");
				while (at.hasMoreTokens()) {
					y.add(at.nextToken());
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
			DepSet dp4 = dp3.fmin2(); // Elimina redundancias de atributos del
										// lado izquierdo
			Salida += dp4.toString() + "\n\n";
            
			Salida += dp4.toFormat() + "\n";
			System.out.println("***"+dp4.toFormat());
		} else
			Salida += "Error cargando atributos o dependencias!";
		return Salida;
	}
}
