package tree;

import java.util.EventObject;

public class TreeEvent extends EventObject{

private static final long serialVersionUID = 1L;
	
	private String data, type;
	
	public TreeEvent(Object source, String data, String type) {
		super(source);
		
		this.data = data;
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public String getType() {
		return type;
	}	
}
