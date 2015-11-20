package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple = child.next();
		boolean end = false;
		if (tuple != null)
			attributeList = tuple.getAttributeList();
		while (!end){
			if (tuple !=null){
				for(int i = 0; i < attributeList.size(); i++){
					if ((tuple.getAttributeName(i).equals(whereAttributePredicate) && child.from.equals(whereTablePredicate) && tuple.getAttributeValue(i).equals(whereValuePredicate))||(!child.from.equals(whereTablePredicate))){
						tuple =new Tuple(attributeList) ;
						return tuple;
					}
				}
				tuple = child.next();
				attributeList = tuple.getAttributeList();
			}
			else
				end = true;
		}
		return null;			
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}