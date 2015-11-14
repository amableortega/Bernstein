package org.utp.fmin;

import java.util.*;

public class DepSet extends ClassSet {

	/**
	 * A partir de un conjunto de dependencias funcionales, se devuelve un nuevo
	 * conjunto de dependencias cada uno llamda "Lado Derecho Simple"
	 * 
	 * @return n
	 */
	public DepSet lds() {
		// Obtenemos el lado derecho simple de las dependencias

		DepSet n = new DepSet();
		Iterator i = this.iterator();
		while (i.hasNext()) { // Evaluamos cada dependencia funcional
			FunDep f = (FunDep) i.next();
			if (f.giveY().size() > 1) { // Determinamos si existen mas de un
										// atributo del lado Izq (y)
				for (int j = 0; j < f.giveY().size(); j++) {
					Object o = f.giveY().elementAt(j);// obtenemos cada uno de
														// los atributos de lado
														// Izq(y)
					ClassSet a = new ClassSet();
					a.add(o);
					FunDep c = new FunDep(f.giveX(), a);// Formamos una nueva
														// dependecia funcional
														// simple del tipo
														// "x->y"
					n.add(c);
				}
			} else {
				n.add(f);// Quiere decir que ya esta en su forma simple
			}
		}
		return n;

	}

	public DepSet fmin2() {
		// Elimina atributos redundantes del lado Izquierdo
		Iterator i = this.iterator();
		DepSet ret = new DepSet();
		// Analizamos cada Dependencia
		while (i.hasNext()) {
			FunDep fd = (FunDep) i.next();

			ClassSet cs = fd.giveX();
			if (cs.size() > 1) { // Solo analizamos las que tienen mas de un
									// atributo del lado izq
				cs = cs.reduction();
				System.out.println(cs.toString());
				// Encontramos los atributos candidatos
				ClassSet ch = new ClassSet();//
				Iterator i2 = cs.iterator();
				// Encontramos la clausura de cada atributo
				while (i2.hasNext()) {
					ClassSet n = (ClassSet) i2.next();
					ClassSet tmp = n.copy();
					if (tmp.closure(this).includes(fd.giveY()))
						ch.add(n);
				}

				if (ch.size() == 0) {// guardamos la dependencia funcional
					ret.add(fd);
				} else {
					ClassSet win = ch.min();
					FunDep w = new FunDep(win, fd.giveY());
					ret.add(w);
				}
			} else {
				ret.add(fd);
			}
		}
		// DepSet ret = (DepSet)this.copy();
		return ret;

	}

	public DepSet chop() {
		// eliminate redundant dependencies (FMIN STEP 3)
		Iterator i = this.iterator();
		DepSet ret = new DepSet();
		DepSet dec = this.copie();
		// traverse each dep.
		while (i.hasNext()) {
			FunDep fd = (FunDep) i.next();
			dec = dec.remove(fd);
			ClassSet tma = fd.giveX().copy();
			ClassSet clo = tma.closure(dec);
			if (!clo.includes(fd.giveY()))
				ret.add(fd);
		}

		return ret;

	}

	public DepSet mcnr() {
		// Eliminacion de dependencias redundantes
		Iterator i = this.iterator();
		DepSet ret = new DepSet();
		DepSet dp = this.copie(); // Sacamos una copia del conjunto de
									// dependecias funcionales LDS
		// traverse each dep.

		while (i.hasNext()) {

			FunDep fd = (FunDep) i.next(); // Analizamos la primera dependencia
			DepSet tmp = dp.remove(fd); // Eliminamos la dependencia que
										// analizamos, tmp representa el
										// conjunto
										// de dependecias restantes
			ClassSet tma = fd.giveX().copy(); // Obtengo el lado Izquierdo de la
												// primera dependencia funcional

			ClassSet clo = tma.closure(tmp); // Enviamos como parametro el resto
												// de dependencias
												//
			if (!clo.includes(fd.giveY())) // Verificamos si el lado derecho
											// esta contenida en la clausura
				ret.add(fd); // quiere decir que NO debemos conciderarla como
								// una dependencia
			else { // ya que seria redundante.

				dp = tmp;

			}

		}

		return ret;

	}

	public ArrayList obcu() {
		// Eliminacion atributos
		Iterator i = this.iterator();

		ArrayList xlista = new ArrayList();
		ArrayList ylista = new ArrayList();
		int cantidad = 0;
		while (i.hasNext()) {
			FunDep fd = (FunDep) i.next(); // Analizamos la primera dependencia
			ClassSet tma = fd.giveX().copy(); // Obtengo el lado Izquierdo de la
			ClassSet tmay = fd.giveY().copy(); // Obtengo el lado Izquierdo de
												// la
			for (int x = 0; x < tma.size(); x++) {

				if (!xlista.contains(tma.elementAt(x).toString()))
					xlista.add(tma.elementAt(x).toString());

			}

			for (int x = 0; x < tmay.size(); x++) {

				if (!ylista.contains(tmay.elementAt(x).toString()))
					ylista.add(tmay.elementAt(x).toString());

			}

			Iterator yiterdador = ylista.iterator();
			
			while (yiterdador.hasNext()) {
				String elementoy = yiterdador.next().toString();

				Iterator xiterdador = xlista.iterator();
				while (xiterdador.hasNext()) {
					String elementox = xiterdador.next().toString();

					if (elementox.equals(elementoy))
						xiterdador.remove(); 												 
				}

			}

		}
		return xlista;
	}

	public DepSet copie() {
		DepSet ret = new DepSet();
		Iterator i = this.iterator();
		while (i.hasNext()) {
			FunDep f = (FunDep) i.next();
			ret.add(f.copy());
		}
		return ret;
	}

	public String toString() {
		return vector.toString();
	}

	public DepSet remove(FunDep fd) {
		// returns a new chopped DepSet
		Iterator i = this.iterator();
		DepSet c = new DepSet();
		while (i.hasNext()) {
			FunDep t = (FunDep) i.next();
			if (!fd.equiv(t))
				c.add(t);
		}
		return c;

	}

	public ClassSet yAts() {
		// turns this right side dep set into multiple attributes
		Iterator i = this.iterator();
		ClassSet ret = new ClassSet();
		while (i.hasNext()) {
			FunDep f = (FunDep) i.next();
			ret.add(f.giveY());
		}
		return ret;
	}

	public ClassSet xAts() {
		// turns this left side dep set into multiple attributes
		Iterator i = this.iterator();
		ClassSet ret = new ClassSet();
		while (i.hasNext()) {
			FunDep f = (FunDep) i.next();
			ret.add(f.giveX());
		}
		return ret;
	}

	public ClassSet allAts() {
		return this.xAts().union(this.yAts());
	}

	public DepSet getFPlus() {
		// clausurar cada atributo de la relacion y
		// generar dependencias que determinen cada atributo de la clausura
		// salvo el
		// mismo atributo.
		// y como genero deps con li compuesto? (brute force?)

		DepSet ret = new DepSet();
		ClassSet all = this.allAts().atomize();
		all = all.reduction();
		Iterator i = all.iterator();
		while (i.hasNext()) {
			ClassSet x = (ClassSet) i.next();
			ClassSet y = x.closure(this).minus(x);
			if (y.size() > 0) {
				FunDep fd = new FunDep(x, y);
				ret.add(fd);
			}
		}

		return ret;
	}

	public String toFormat() {
		String s = "";
		Iterator i = this.iterator();
		while (i.hasNext()) {
			FunDep fd = (FunDep) i.next();
			s = s + fd.giveX().toFPlusFormat() + ">" + fd.giveY().toFPlusFormat() + ";\n";
		}

		return s;
	}

	public String toHumanFormat() {
		String s = "";
		Iterator i = this.iterator();
		while (i.hasNext()) {
			FunDep fd = (FunDep) i.next();
			s = s + fd.toHumanFormat() + "\n";
		}

		return s;
	}

	public DepSet fmin() {
		DepSet dp = this.copie();
		return dp.lds().fmin2().mcnr();
	}

	public FunDep getGlobalDep(ClassSet rel) {
		FunDep ret = null;
		Iterator i = this.iterator();
		while (i.hasNext()) {
			FunDep fd = (FunDep) i.next();
			if (fd.toRelation().equiv(rel)) {
				ret = fd;
				break;
			}
		}
		return ret;
	}

}