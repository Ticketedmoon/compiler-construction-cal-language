// Name:STC.java
// Author: Shane Creedon
// I began with a basic symbol table implementation
// and improved it from the ground up.
// I used the hashtable/LL approach.

import java.util.*;

public class STC extends Object
{
	// Primary symbol table which handles scope.
    Hashtable<String, LinkedList<String>> symbolTable;

	// Type table for every scope
    Hashtable<String, String> typeTable;

	// Handles functions & parameters
    Hashtable<String, String> infoTable;

    public STC() {
		this.symbolTable = new Hashtable<>();
		this.typeTable = new Hashtable<>();
		this.infoTable = new Hashtable<>();

		symbolTable.put("global", new LinkedList<>());
    }

	public LinkedList<String> get(String scope) {
		return symbolTable.get(scope);
    }

	public String getType(String id, String scope) {
        String type = typeTable.get(id + "/" + scope);
        if(type != null) 
            return type;
        else {
			type = typeTable.get(id + "/" + "global");
           	if(type != null) {
               	return type;
           	}
        }
        return null;
	}

	public void put(String id, String type, String info, String scope) {
		LinkedList<String> list = symbolTable.get(scope);
		if (list == null) {
			list = new LinkedList<>();
			// Generate new scope below
			list.add(id);
			// Add to list of scopes available in global scope.
			symbolTable.put(scope, list);
		}
	
		else {
			// Ensure the most current id is the first paramater.
			// This is essential for later semantic checks and such.
			list.addFirst(id);
		}
		typeTable.put(id + "/" + scope, type);
		infoTable.put(id + "/" + scope, info);
	}

	public void print() {
        Enumeration e = symbolTable.keys();
        while(e.hasMoreElements()) {
            // get id for each scope
            String scope = (String) e.nextElement();
            System.out.println("Scope: " + "[" + scope + "]");
            // get contents associated with that scope
            LinkedList<String> idList = symbolTable.get(scope);
            for(String id : idList) {
                String type = typeTable.get(id + "/" + scope);
                String info = infoTable.get(id + "/" + scope);
                System.out.println(" - (" + id + ", " + type + ", " + info + ")");
            }
            System.out.println();
        }
    }

	public boolean isFunction(String id) {
        LinkedList<String> list = symbolTable.get("global");
        ArrayList<String> functions = new ArrayList<String>();
        for(int i = 0; i < list.size(); i++) {
                String details = infoTable.get(list.get(i) + "/" + "global");
                if(details.equals("function")  && list.get(i).equals(id)) {
                    return true;
                }
        }
        return false;
    }

    public ArrayList<String> getFunctionList() {
        LinkedList<String> list = symbolTable.get("global");
        ArrayList<String> functions = new ArrayList<String>();
        for(int i = 0; i < list.size(); i++) {
                String details = infoTable.get(list.get(i)+ "/" + "global");
                if(details.equals("function"))
                    functions.add(list.get(i));
        }
        return functions;
    }

	public int getParams(String id) {
        LinkedList<String> list = symbolTable.get(id);
        int counter = 0;
		if (list != null) {
        	for(int i = 0; i < list.size(); i++) {
            	String details = infoTable.get(list.get(i) + "/" + id);
            	if(details.equals("parameter")) {
                	counter++;
            	}
        	}
    	}
        return counter;
	}

	public String getParamType(int index, String scope) {
        int count = 0;
        LinkedList<String> identifiers = symbolTable.get(scope);
            for(String id : identifiers) {
                String type = typeTable.get(id + "/" + scope);
                String details = infoTable.get(id + "/" + scope);
                if(details.equals("parameter")) {
                    count++;
                    if(count == index) {
                        return type;
                    }
                }
            }
            return null;
    }
    
	// Check if ID is in current scope first
	// Then check if in global scope.
    public String getDescription(String id, String scope) {
        String details = infoTable.get(id + "/" + scope);

        if(details != null) 
            return details;
        else 
			return infoTable.get(id + "/" + "global");	
    }

	// Check for identical declarations in global / current scope.
	public boolean hasNoIdenticalDeclarations(String id, String scope) {
		LinkedList<String> current = this.symbolTable.get(scope);
		// Check if the id has only occured once in the current scope
		return ((current.indexOf(id) == current.lastIndexOf(id)));
	}

}
