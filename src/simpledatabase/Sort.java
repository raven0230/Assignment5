package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple childTuple = child.next();
		while (childTuple!=null){
			tuplesResult.add(childTuple);
			childTuple = child.next();
		}
		for(int i=0;i<tuplesResult.size();i++){
			for (int j=1;j<(tuplesResult.size()-i);j++){
				boolean search = true;
				ArrayList<Attribute> left, right;
				left = tuplesResult.get(j-1).getAttributeList();
				right = tuplesResult.get(j).getAttributeList();
				for (int x=0; x<left.size();x++){
					for (int y=0; y<right.size();y++){
						boolean b1 = left.get(x).getAttributeName().equals(orderPredicate);
						boolean b2 = left.get(x).getAttributeName().equals(right.get(y).getAttributeName());
						if (b1 && b2){
							Object o1 =left.get(x).getAttributeValue();
							Object o2 =right.get(x).getAttributeValue();
							Type type =left.get(x).getAttributeType();
							boolean b3 = false;
							switch(type.type){
								case INTEGER:
									b3 = (int)o1 > (int)o2;
									break;
								case DOUBLE:
									b3 = (double)o1 > (double)o2;
									break;
								case LONG:
									b3 = (long)o1 > (long)o2;
									break;
								case SHORT:
									b3 = (short)o1 > (short)o2;
									break;
								case FLOAT:
									b3 = (float)o1 > (float)o2;
									break;
								case STRING:
									b3 = ((String) o1).compareTo((String)o2)>0;
									break;
								case BOOLEAN:
									b3 = (boolean)o1 == true;
									break;
								case CHAR:
									b3 = (char)o1 > (char)o2;
									break;
								case BYTE:
									b3 = (byte)o1 > (byte)o2;
									break;
							}
							if (b3){
								Collections.swap(tuplesResult,j-1,j);
							}
							search = false;
							break;
						}
					}
					if (!search) break;
				}
			}
		}
		if (!tuplesResult.isEmpty())
			return tuplesResult.remove(0);
		else return null;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}