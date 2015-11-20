package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		Tuple leftTuple = leftChild.next();		
		while (leftTuple != null){
			tuples1.add(new Tuple(leftTuple.getAttributeList()));
			leftTuple = leftChild.next();
		}
		Tuple rightTuple = rightChild.next();
		while (rightTuple != null){
			ArrayList<Attribute> rightAttributeList = rightTuple.getAttributeList();
			for (int i=0; i<tuples1.size();i++){
				ArrayList<Attribute> leftAttributeList = tuples1.get(i).getAttributeList();
				for (int j=0;j<leftAttributeList.size();j++){
					String leftAttributeName = leftAttributeList.get(j).getAttributeName();
					Object leftAttributeValue = leftAttributeList.get(j).getAttributeValue();
					for (int k=0; k<rightAttributeList.size();k++){
						String rightAttributeName = rightAttributeList.get(k).getAttributeName();
						Object rightAttributeValue = rightAttributeList.get(k).getAttributeValue();
						if (leftAttributeName.equals(rightAttributeName) && leftAttributeValue.equals(rightAttributeValue)){
							newAttributeList = new ArrayList<Attribute>();
							for (int x=0;x<rightAttributeList.size();x++){
								Attribute temp = new Attribute();
								temp.attributeName=rightAttributeList.get(x).getAttributeName();
								temp.attributeType=rightAttributeList.get(x).getAttributeType();
								temp.attributeValue=rightAttributeList.get(x).getAttributeValue();
								newAttributeList.add(temp);
							}
							for (int y=0;y<leftAttributeList.size();y++){
								if (!leftAttributeList.get(y).getAttributeName().equals(rightAttributeName)){
									Attribute temp = new Attribute();
									temp.attributeName=leftAttributeList.get(y).getAttributeName();
									temp.attributeType=leftAttributeList.get(y).getAttributeType();
									temp.attributeValue=leftAttributeList.get(y).getAttributeValue();
									newAttributeList.add(temp);									
								}
							}
							return new Tuple(newAttributeList);
						}
					}
				}
			}
			rightTuple = rightChild.next();	
		}
		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}