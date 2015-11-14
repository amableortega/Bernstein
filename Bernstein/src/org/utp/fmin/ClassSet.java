package org.utp.fmin;

import java.util.*;
/**
 **/

public class ClassSet implements Set {

  protected Vector vector;

  public ClassSet() {
    this.vector = new Vector();
  }

  public ClassSet(Object o) {
    vector = new Vector();
    this.add(o);
  }

  //-----------------------------------------start of interface set
  public int size() {
    //throw new java.lang.UnsupportedOperationException("Method size() not yet implemented.");
    return vector.size();
  }

  public boolean isEmpty() {
    //throw new java.lang.UnsupportedOperationException("Method isEmpty() not yet implemented.");
    return vector.isEmpty();
  }

  public boolean contains(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
    //return vector.contains(o);
    Iterator i = this.iterator();
    while(i.hasNext()){
      if(i.next().toString().compareTo(o.toString()) == 0)
        return true;
    }
    return false;
  }

  public boolean containsNew(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
    //return vector.contains(o);
    if(!o.getClass().toString().endsWith("ClassSet")){
      Iterator i = this.iterator();
      while(i.hasNext()){
        if(i.next().toString().compareTo(o.toString()) == 0)
          return true;
      }
      return false;
    }
    else{
      return false;
    }
  }

  public Iterator iterator() {
    //throw new java.lang.UnsupportedOperationException("Method iterator() not yet implemented.");
    return vector.iterator();
  }

  public Object[] toArray() {
    //throw new java.lang.UnsupportedOperationException("Method toArray() not yet implemented.");
    return vector.toArray();
  }

  public Object[] toArray(Object[] a) {
    //throw new java.lang.UnsupportedOperationException("Method toArray() not yet implemented.");
    return vector.toArray(a);
  }

  public boolean add(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method add() not yet implemented.");
    if(!this.contains(o))
      return vector.add(o);
    else
      return true;
  }

  public boolean remove(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method remove() not yet implemented.");
    return vector.remove(o);
    /*Iterator i = this.iterator();
    Object del = null;
    while(i.hasNext()){
      Object ob = i.next();
      if(ob.toString().compareTo(o.toString()) == 0){
        del = ob;
      }
    }
    if(del != null)
      return vector.remove(del);
    else
      return false;*/
  }

  public boolean containsAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method containsAll() not yet implemented.");
    return vector.containsAll(c);
  }

  public boolean addAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method addAll() not yet implemented.");
    return vector.addAll(c);
  }

  public boolean retainAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method retainAll() not yet implemented.");
    return vector.retainAll(c);
  }

  public boolean removeAll(Collection c) {
    //throw new java.lang.UnsupportedOperationException("Method removeAll() not yet implemented.");
    return vector.removeAll(c);
  }

  public void clear() {
    //throw new java.lang.UnsupportedOperationException("Method clear() not yet implemented.");
    vector.clear();
  }

  public boolean equals(Object o) {
    //throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
    return vector.equals(o);
  }

  //----------------------------------------------------end of interface set

  /*public ClassSet sort(){
    ClassSet ret = new ClassSet;

  }*/

  public boolean equiv(ClassSet s){
    return(this.includes(s) && s.includes(this));
  }

  public boolean includes(ClassSet s){
    Iterator i = s.iterator();
    boolean flag = true;
    while(i.hasNext()){
      if(!this.contains(i.next()))
        flag = false;
    }
    return flag;
  }

  public boolean includes(Object o){
    ClassSet s = new ClassSet();
    s.add(o);
    return this.includes(s);

  }

  public ClassSet union(ClassSet s){
    //warning: overwrites this object
    Iterator i = s.iterator();
    //ClassSet cs = this.copy();
    while(i.hasNext()){
      Object o = i.next();
      if(!this.contains(o))
        this.add(o);
    }
    return this;
  }

  public ClassSet unionSafe(ClassSet s){
    ClassSet ret = this.copy();
    Iterator i = s.iterator();
    //ClassSet cs = this.copy();
    while(i.hasNext()){
      Object o = i.next();
      if(!ret.contains(o))
        ret.add(o);
    }
    return ret;
  }

  public ClassSet intersect(ClassSet s){
  //returns a new set
    Iterator i = s.iterator();
    ClassSet cs = new ClassSet();
    while(i.hasNext()){
      Object o = i.next();
      if(s.contains(o) && this.contains(o))
        cs.add(o);
    }
    return cs;
  }


  public ClassSet minus(ClassSet s){
  //returns a new set
    ClassSet ret = new ClassSet();
    Iterator i = this.iterator();
    while(i.hasNext()){
      Object n = i.next();
      if(!s.includes(n))
        ret.add(n);
    }
    return ret;
  }

  public ClassSet minus(Object ob){
    ClassSet s = new ClassSet();
    s.add(ob);
    return this.minus(s);
  }

  public Object elementAt(int pos){
    return vector.elementAt(pos);
  }


  public ClassSet reduction(){
    //returns a new set
    ClassSet r = new ClassSet();

    for(int i = 0; i < this.size(); i++){
      ClassSet c = this.minus(this.elementAt(i));
      if(!c.isEmpty()){
        r.add(c);
        ClassSet re = c.reduction();
        if(!r.includes(re))
          r.union(re);
      }
    }

    return r;
  }

  public String toString(){
    return vector.toString();
  }

  public ClassSet copy(){
    Iterator i = this.iterator();
    ClassSet c = new ClassSet();
    while(i.hasNext()){
      c.add(i.next());
    }
    return c;
  }

  public ClassSet min(){
  //returns a new set
    Iterator i = this.iterator();
    ClassSet c = new ClassSet();
    int min = 1000000;
    while(i.hasNext()){
      ClassSet n = (ClassSet)i.next();
      if(n.size()<min){
        c = n;
        min = n.size();
      }
    }
    return c;
  }

  public ClassSet closure(DepSet deps){
  //this method returns a new set
      ClassSet cl = this.copy(); //Copia del lado izquierdo x
      ClassSet cl2 = new ClassSet(); //vacio

      while(!cl.equiv(cl2)){ //Si existe una dependencia que analizar
        cl2 = cl.copy();
        Iterator i = deps.iterator();
        while(i.hasNext()){//Iteramos sobre la dependecias restantes
          FunDep d = (FunDep)i.next();

          if( cl.includes(d.giveX()) ){ //El lado izquierdo de la dependencia, esta incluida
        	                            //
            //ClassSet tmp = cl.copy();
            cl.union(d.giveY());       //Agrego el atributo a la clausura de la dependencia
            //cl = (ClassSet)
          }
        }

      }
      return cl;
  }

  public ClassSet bruteKeys(DepSet deps){
    ClassSet all = this.reduction();
    ClassSet keys = new ClassSet();
    Iterator i = all.iterator();
    while(i.hasNext()){
      ClassSet n = (ClassSet)i.next();
      if(n.closure(deps).equiv(this))
        keys.add(n);
    }
    //The whole relation is always a superkey!!!
    keys.add(this);
    return keys;
  }



  public DepSet depLoss(DepSet dfs, ClassSet pr){
    DepSet ret = new DepSet();
    Object vec[] = pr.toArray();
    Iterator it = dfs.iterator();
    while(it.hasNext()){
      FunDep f = (FunDep)it.next();
      ClassSet z = f.giveX();
      ClassSet temp = new ClassSet();
      while(!z.equiv(temp)){
        temp = z.copy();
        for(int i=0;i<pr.size();i++){
          ClassSet cl = z.intersect((ClassSet)vec[i]).closure(dfs);
          z = z.unionSafe(cl.intersect((ClassSet)vec[i]));
        }
      }
      if(!z.includes(f.giveY()))
        ret.add(f);
    }

    return ret;
  }


  public boolean depLoss(FunDep fd, DepSet dfs, ClassSet pr){
    //DepSet ret = new DepSet();
    boolean ret = true;
    Object vec[] = pr.toArray();

      FunDep f = fd;
      ClassSet z = f.giveX();
      ClassSet temp = new ClassSet();
      while(!z.equiv(temp)){
        temp = z.copy();
        for(int i=0;i<pr.size();i++){
          ClassSet cl = z.intersect((ClassSet)vec[i]).closure(dfs);
          z = z.unionSafe(cl.intersect((ClassSet)vec[i]));
        }
      }
      if(!z.includes(f.giveY()))
        ret = false;


    return ret;
  }



  public DepSet getRelevantDeps(DepSet dfs){
  //"this" is a single projection
  //uses F+ & fminStep1 !!!!!
    DepSet ret = new DepSet();
    DepSet dfsPlus = dfs.getFPlus().lds();
    Iterator i = dfsPlus.iterator();
    while(i.hasNext()){
      FunDep f = (FunDep)i.next();
      if(f.isRelevant(this)){
        ret.add(f);
      }
    }

    return ret;
  }

  public String toFPlusFormat(){
    String s = "";
    for(int i = 0;i<this.size();i++){
      s = s + this.elementAt(i).toString();
      if(i<(this.size()-1))
        s = s + ",";
    }
    //s = s + ";";
    return s;
  }

  public String toHumanFormat(){
    String s = "(";
    for(int i = 0;i<this.size();i++){
      s = s + this.elementAt(i).toString();
      if(i<(this.size()-1))
        s = s + ",";
    }
    s = s + ")";
    return s;
  }

  public ClassSet atomize(){
    ClassSet ret = new ClassSet();
    Iterator i = this.iterator();
    while(i.hasNext()){
      Object o = i.next();
      String st = o.getClass().toString();
      if(st.endsWith("ClassSet")){
        ret = ret.unionSafe(((ClassSet)o).atomize());
      }
      else
        ret.add(o);
    }

    return ret;
  }

}