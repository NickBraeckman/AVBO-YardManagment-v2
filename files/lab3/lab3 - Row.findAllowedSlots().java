

gewicht = c.getGc()

findAllowedPlace(C){
	// conrtoleer op fouten in stack (en plaats er virtuee op)
	if (gevonden plaats. get upper > c.getGc){
		gebruik plaats niet
	}
}

moveSafe()











// number of support slots are enough
		minLength = slotIDs.size() > (container.getLc() / Yard.Ls);
		if (minLength){
		//TODO kijk verder indien grotere container ?
		largeOnSmallConstraint = false;
		int temp=-1;
		for (Integer i : slotIDs){

		temp=i;
		if (temp)
		}
		}












public class Row {

    private int id;
    private List<Slot> slots;
    private int yCenter;
	
	private List<Container> list;  //upperAndFreeContainers
	

	public List<Integer> findAllowedSlots(Container container) {  //TODO convert to list of slots ???
		
		
		//Find suitable containers to stack on
		for (Slot slot: slots){
			Container c = slot.getUpperContainer;
			
			boolean inList = list.contains(c);
			boolean isFree = c.getIsFree(); // voor alle slots waar c op staat, is C upper van dat slot ?
			boolean taggedSafe = c.getTag(); // TODO tag kijken voor alle slots onder deze container op not safe
			
			if( !inList && isFree && taggedSafe){
				list.add(c); //behalve alst eigen container is TODO
			}
		}
		
		
		//Find consecutive containers wich lengths sum eqauls container.lengths();
		List<Container> suitable = findModuloSequence(container); //grijs, blauw
		
		if(suitable != null){
			List<Integer> allowedSlots = new ArrayList<>();
			for (Container c: suitable){
				allowedSlots.addAll(c.getSlotIds());
			}
			return allowedSlots;
		}
		// no suitable place was found in current row
		else{
			return null;
		} 
	}
	
	public List<Container> findModuloSequence (Container container){
		int requiredLength = container.getLC();
		int counter =0;

		List<Container> suitable = new ArrayList<>();
		
		for (Container c: list){
			//opnieuw beginnen bij hoogtesprong
				counter=0;
				suitable=new ArrayList<>();

			int cLenght = c.getLc();  //2
			
			counter+=cLenght;       //4
			suitable.add(c);		//grijs, blauw
			
			if (counter%requiredLength == 0){
				return suitable;
			}else if (counter > requiredLength){
				counter=0;
				suitable=new ArrayList<>();
			}

		}
		
		//ONLY ARRIVE HERE WHEN NO SUITABLE PLACE IS FOUND
		return null;
	
	}
