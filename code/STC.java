// Name:STC.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Very basic Symbol Table implementation
//

import java.util.*;

public class STC extends Object
{
	// Primary symbol table which handles scope.
    Hashtable<String, LinkedList<String>> symbolTable;

	// Type table for every scope
    Hashtable<String, String> typeTable;

	// Handles functions & parameters
    Hashtable<String, String> descriptionTable;

    public STC() {
		this.symbolTable = new Hashtable<>();
		this.typeTable = new Hashtable<>();
		this.descriptionTable = new Hashtable<>();
    }

	public void isInScope(String id, String type, String scope) {
        LinkedList<String> list = symbolTable.get(scope);
        if(list == null) {
            System.out.println("Variable " + id + " not declared in " + scope);
        }     
    }

	public String getType(String id, String scope) {
        String type = typeTable.get(id+scope);
        if(type != null) 
            return type;
        else {
            type = typeTable.get(id+"global");
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
		typeTable.put(id+scope, type);
		descriptionTable.put(id+scope, info);
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
                String type = typeTable.get(id+scope);
                String description = descriptionTable.get(id+scope);
                System.out.println("(" + id + ", " + type + ", " + description + ")");
            }
            System.out.println();
        }
    }
}
