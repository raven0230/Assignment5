package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;

	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		String dataLine = null;
		Tuple tuple1 = null;
		try{
			if (!getAttribute){
				String attributeLine = br.readLine();
				String dataTypeLine = br.readLine();
				tuple = new Tuple(attributeLine, dataTypeLine, "");
				tuple.setAttributeName();
				tuple.setAttributeType();
				getAttribute = true;
			}				
			dataLine = br.readLine();
			if (dataLine!=null){
				tuple1 = new Tuple("","",dataLine);
				tuple1.col = tuple.col;
				tuple1.col1 = tuple.col1;
				tuple1.setAttributeName();
				tuple1.setAttributeType();
				tuple1.setAttributeValue();
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (dataLine!=null)
			return tuple1;
		else
			return null;
	}

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}