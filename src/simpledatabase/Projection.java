package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		newAttributeList = new ArrayList<Attribute>();
		Tuple tuple = child.next();
		if (tuple!=null){
			ArrayList<Attribute> childAttributeList = tuple.getAttributeList();
			for (int i = 0; i < childAttributeList.size(); i++){
				if (tuple.getAttributeName(i).equals(attributePredicate)){
					newAttributeList.add(childAttributeList.get(i));
				}
			}
		}
		if (newAttributeList.isEmpty())
			return null;
		else
			return new Tuple(newAttributeList);
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}