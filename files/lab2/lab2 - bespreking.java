public class Yard{
	public void reoderConatainers(){
		while (!yard.isSafe()){
			Container c = yard.findWrong()
			q1.moveSafe(c);
		}

		public void isSafe(){
			// voor alle niet lege slots doe controle
		}

		public void x(){
			for (Container c: containers){
				Set<Slot> set = c.getSlots();
				checkHmaxSlot(c);
				checkHsafeSlot(c);
				checkCompatibilitySlot(c);
			}
		}
		public Slot findSafe(Container c){
			boolean b1=false,b2=false,b3=false;
			for (Slot s: slots){
				b1=s.checkLength(c);
				b2=s.checkWeight(c);
				b3=s.checkHeight(c);
				if (b1 && b2 && b3)	return s
			}


		}

		public Container findWrong(){

			//controlleer op bovenste gewicht =1
			//

			return c;
		}
	}
}

public class Container{

	public Set<Slot> getSlots(){
		//...
		return new Set<Slot>();
	}

	public void move(Slot s){
		//Can be placed on s (previously checked)
	}
}

public class Crane{

	public void moveSafe(Container container){

		Slot s = c.getSlot();
		c=s.popCustom();

		for (Slot s : container.getSlots()){
			while(c!= container){

			s.containers.moveToSafePlace(); // remove upper and place safe

			if (s.isSafe != null){
				//NIET SAFE
				boolean safe = false
			}
		}
		}




		//zet een op lege plek wel

	}
	public void moveToSafeSlot(Container c){
		Slot s = yard.findSafe(c
		move(c,s)

		checkAllSlotBelowContainer(); //-->return
	}

	public void move (Container c, Slot s){

	}
}

public ...(){

	for(Row row: rows){
		zoekPlaats(1)
	}
}

public zoekPlaats(int orde){

	List<p> = doorZoekYard(orde)

	for(p in list<p>){
		if(p.isSave){
			return p;
		}
	}
	zoekPlaats(orde++);
}