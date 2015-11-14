package org.utp.fmin;

import java.util.*;

public class FunDep {

  private ClassSet x;
  private ClassSet y;


  public FunDep(ClassSet x, ClassSet y) {
    this.x = x;
    this.y = y;
  }

  public ClassSet giveX(){
    return x;
  }

  public ClassSet giveY(){
    return y;
  }

  public String toString(){
    return(x.toString() + " -> " + y.toString());
  }

  public String toHumanFormat(){
    return(x.toFPlusFormat() + "->" + y.toFPlusFormat());
  }

  public boolean equiv(FunDep f){
    return(this.giveX().equiv(f.giveX())&&this.giveY().equiv(f.giveY()));
  }

  public FunDep copy(){
    return (new FunDep(this.giveX().copy(), this.giveY().copy()));
  }


	public boolean isRelevant(ClassSet rel) {
		// returns true if all attributes in this fd
		// are present in the relation

		boolean ret = true;

		ClassSet all = this.giveX().unionSafe(this.giveY());

		Iterator i = all.iterator();
		while (i.hasNext()) {
			ClassSet c1 = new ClassSet(i.next());
			if (!rel.includes(c1))
				ret = false;
		}

		return ret;
	}


  public ClassSet toRelation(){
    return this.giveX().atomize().unionSafe(this.giveY().atomize());
  }

  public int size(){
    return this.giveX().size()+this.giveY().size();
  }

}



