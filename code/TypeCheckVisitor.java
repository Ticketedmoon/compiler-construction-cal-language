import java.util.*;

public class TypeCheckVisitor implements AssignmentTwoVisitor
{
	// Scope and Symbol Table declarations.
	private static String scope = "global";
	private static STC ST;

	// function calls / same declaration ID overlap sets.
	private static HashSet<String> functionCalls = new HashSet<>();
	private static Hashtable<String, LinkedHashSet<String>> identicalDeclarations = new Hashtable<>();
	
	private static boolean isDeclared(String id, String scope) {
		// Get the declarations in the scope currently active in the program.
		LinkedList<String> list = ST.get(scope);

		// Get the global scope declarations
		LinkedList<String> global_list = ST.get("global");
		if(!list.contains(id)) {
			return false;
		}
		return true;
	}

	/* Method for checking if a string is numeric.
	 * Useful for constant declarations, 
	 * ensuring integer type constants are assigned an integer value */
	public static boolean isNumeric(String str) {  
		try {  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe) {  
			return false;  
		}  
		return true;  
	}

	/* Method checks for any identical declarations in the same scope*/
	private static void scanForIdenticalDeclarations() {
		if (!identicalDeclarations.isEmpty()) {
			Enumeration keys = identicalDeclarations.keys();
			System.out.println("\nDuplicate Declaration Checking: ");
			while(keys.hasMoreElements()) {
				String scope = keys.nextElement().toString();
				LinkedHashSet<String> matches = identicalDeclarations.get(scope);
				Iterator iterator = matches.iterator();
				while(iterator.hasNext()) {
					System.out.println("Error: Declaration with ID (" + iterator.next() + ") has been declared more than once.");
				}
			}
		}
	}

	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	public Object visit(ASTProgram node, Object data) {
		// Preprocess & setup
		ST = (STC) data;

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}

		// Before returning Program DataType --
		// Print matching IDs in same scope errors
		scanForIdenticalDeclarations();
		return DataType.Program;
	}

	public Object visit(ASTMain node, Object data) {
		this.scope = "main";
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	public Object visit(ASTParameter node, Object data) {
		return (node.jjtGetChild(1).jjtAccept(this, data));
	}

	public Object visit(ASTParameter_list node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTDeclarationList node, Object data) {	
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	public Object visit(ASTArg_list node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	public Object visit(ASTNemp_arg_list node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTFunction node, Object data) {
		this.scope = node.jjtGetChild(1).jjtAccept(this, data).toString();
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return DataType.Function;
	}

	private static void checkForMatchingDeclarations(String ID, String scope) {
		if (!ST.hasNoIdenticalDeclarations(ID, scope)) {
			HashSet<String> items = identicalDeclarations.get(scope);
			if (items == null) {
				LinkedHashSet<String> newRow = new LinkedHashSet<>();
				newRow.add(ID);
				identicalDeclarations.put(scope, newRow);
			}
			else
				items.add(ID);
		}
	}

	/* For Const / Variable Declarations
 	 * Be careful of redeclarations with the same ID in the same scope.
 	 * To prevent this, keep track of variable IDs/scope in this class.
 	 * Any clash will report an error to the console */
	public Object visit(ASTVarDecl node, Object data) {
		String ID = node.jjtGetChild(0).jjtAccept(this, data).toString();
		checkForMatchingDeclarations(ID, scope);
		return DataType.VariableDeclaration;
	}

	public Object visit(ASTConstDecl node, Object data) {
		String type = (String) node.jjtGetChild(1).jjtAccept(this, data);
		String const_val = node.jjtGetChild(2).jjtAccept(this, data).toString();
		// Check numeric edge cases
		if (type.equals("integer") && const_val.equals("TypeInteger")) {
			return DataType.TypeInteger;
		}

		// Check Boolean edge cases
		else if (type.equals("boolean") && (const_val.equals("true") || const_val.equals("false"))) {
			return DataType.TypeBoolean;
		}

		else {
			System.out.println("Error: Constant with ID (" + node.jjtGetChild(0).jjtAccept(this, data) + ") type mismatch with assigned value.");
			return DataType.TypeUnknown;
		}

	}

	public Object visit(ASTStatement node, Object data) {
		if(node.jjtGetNumChildren() > 0 && !node.jjtGetChild(0).jjtAccept(this, data).toString().equals("TypeUnknown")) {
			String id = node.jjtGetChild(0).jjtAccept(this, data).toString();
			// function calls directly invoked / no assignment to it.
			if(ST.isFunction(id)) {
				System.out.println("function call added: " + id);
            	functionCalls.add(id);
        	}

			// Check integer/boolean/function calls
        	if(isDeclared(id, scope)) {
            	String type = ST.getType(id, scope);
            	String description = ST.getDescription(id, scope);
            	if(description.equals("const")) {
                	System.out.println("Error: ID (" + id + ") is a constant and cannot be redeclared");
            	}
            	else {
            		String rhs = node.jjtGetChild(1).jjtAccept(this, data).toString();
            		if(type.equals("integer")) {
						if (rhs.equals("TypeInteger") || isNumeric(rhs)) {
                    		node.jjtGetChild(1).jjtAccept(this, data);
						}
                		else if(rhs.equals("TypeBoolean")) {
                    		System.out.println("Error: Expected assignment of type integer for ID (" + id + ") instead got boolean");
                		}
                		else if(ST.isFunction(node.jjtGetChild(1).jjtAccept(this, data).toString())) {
                    		String func_name = node.jjtGetChild(1).jjtAccept(this, data).toString();
                    		// check if function is declared in global scope
                    		if(!isDeclared(func_name, "global") && !isDeclared(func_name, scope)) {
                        		System.out.println("Error: " + func_name + " is not declared");
                    		}     
                    		else if(ST.isFunction(func_name)) {
								isFunctionAvailable(func_name, node, data);
							}
                		}
						else {
							System.out.println("Error: Expected integer assignment for ID (" + id + ")");
							}
            		}
            		else if(type.equals("boolean")) {
              			if(rhs.equals("TypeBoolean")) {
                    		node.jjtGetChild(1).jjtAccept(this, data);
                		}
                		else if(rhs.equals("TypeInteger")) {
                    		System.out.println("Error: Expected type boolean for ID (" + id + ") instead got integer");
                		}
                		else if(rhs.equals("FuncReturn")) {
                    		String func_name = (String) node.jjtGetChild(1).jjtAccept(this, data);
                    		// check if function is declared in global scope
                    		if(!isDeclared(func_name, "global")) {
                        		System.out.println(func_name + " is not declared");
                    		}
                    		else {
                        		// get return type of function
                        		String func_return = ST.getType(func_name, "global");
                        		if(!func_return.equals("boolean")) {
                            		System.out.println("Error: Expected return type of boolean instead got " + func_return);
                        		}
                    		}
                		}
						else {
							System.out.println("Error: Expected boolean assignment but got String for ID (" + id + ")");
						}
					}
					else if(type.equals("void")) {
						System.out.println("void");
					}
        		}
        	}
        	else if(!isDeclared(id, scope)) {
				if (!isDeclared(id, "global"))
            		System.out.println("Error: Token with ID (" + id + ") needs to be declared before use");
        	}
		}
    return data;    
	}	

	public Object visit(ASTFunctionList node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	/* Arithmetic operations are checked here
 	 * Valid operations involve DataType.TypeIntegers. */
	public Object visit(ASTAdd_Op node, Object data) {
		String childOne = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String childTwo = node.jjtGetChild(1).jjtAccept(this, data).toString();
	
		if (childOne.equals("TypeInteger") && childTwo.equals("TypeInteger")) {
			return DataType.TypeInteger;
		}
		else {
			if (childOne.equals("TypeInteger") && isDeclared(childTwo, scope) && ST.getType(childTwo, scope).equals("integer"))
				return DataType.TypeInteger;
			else if (childTwo.equals("TypeInteger") && ST.getType(childOne, scope).equals("integer"))
				return DataType.TypeInteger;
			else if (isDeclared(childOne, scope) && ST.getType(childOne, scope).equals("integer") && isDeclared(childTwo, scope) && ST.getType(childTwo, scope).equals("integer"))
				return DataType.TypeInteger;
		}
		return DataType.TypeUnknown;
	}

	public Object visit(ASTMinus_Op node, Object data) {
		if (node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger)
			return DataType.TypeInteger;
		else
			return DataType.TypeUnknown;
	}

	public Object visit(ASTLogical_Conjunction node, Object data) {
		String childOne = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String childTwo = node.jjtGetChild(1).jjtAccept(this, data).toString();
		
		//System.out.println("One: " + childOne);
		//System.out.println("Two: " + childTwo);
	
		if (childOne.equals("TypeBoolean") && childTwo.equals("TypeBoolean")) {
			return DataType.TypeBoolean;
		}
		else {
			if (childTwo.equals("TypeInteger") && ST.getType(childOne, scope).equals("boolean")) {
				System.out.println("Error: Condition contains Integer Value as boolean operand");
				return DataType.TypeUnknown;
			}
			else if (childOne.equals("TypeInteger") && ST.getType(childTwo, scope).equals("boolean")) {
				System.out.println("Error: Condition contains Integer Value as boolean operand");
				return DataType.TypeUnknown;
			}
			else if (isDeclared(childOne, scope) && ST.getType(childOne, scope).equals("boolean") 
						&& isDeclared(childTwo, scope) && ST.getType(childTwo, scope).equals("boolean")) {
				return DataType.TypeBoolean;
			}
		}
		return DataType.TypeUnknown;
	}

	public Object visit(ASTLogical_Disjunction node, Object data) {
		String childOne = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String childTwo = node.jjtGetChild(1).jjtAccept(this, data).toString();
		if (node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeBoolean && 
			node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeBoolean) {
			return DataType.TypeBoolean;
		}

		if (!ST.getType(childOne, scope).equals("boolean"))
			System.out.println("Error: Expected boolean value but got value of (" + childOne + ") in conditional statement");	
		else if (!ST.getType(childTwo, scope).equals("boolean"))
			System.out.println("Error: Expected boolean value but got value of (" + childTwo + ") in conditional statement");
		else
			return DataType.TypeBoolean;
		return DataType.TypeUnknown;
	}

	public Object visit(ASTExp node, Object data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	public Object visit(ASTEquals node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTNot_Equals node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public void isFunctionAvailable(String func_name, ASTStatement node, Object data) {
		functionCalls.add(func_name);
		System.out.println("function call added: " + func_name);
		// get return type of function
		String func_return = ST.getType(func_name, "global");
		if(!func_return.equals("integer")) {
			System.out.println("Error: Expected return type of integer instead got " + func_return);
		}

		int num_args = ST.getParams(func_name);
		// Statement followed by
		// Exp followed by 
		// Arglist -> Nemp_arg_list -> count children
		boolean nonEmptyParamList = (node.jjtGetChild(1).jjtGetChild(1).jjtGetNumChildren()) > 0;
		if (nonEmptyParamList) {
			int actual_args = node.jjtGetChild(1).jjtGetChild(1).jjtGetChild(0).jjtGetNumChildren();
			// check that the correct number of args is used
			if(num_args != actual_args) 
				System.out.println("Error: Expected " + num_args + " parameters instead got " + actual_args);
			else if(num_args == actual_args) {
				// check that the arguments are of the correct type
				Node arg_list = node.jjtGetChild(1).jjtGetChild(0);
				for(int i = 0; i < arg_list.jjtGetNumChildren(); i++) {
					String arg  = (String)arg_list.jjtGetChild(i).jjtAccept(this, data);
					// check if argument in arglist is actually declared 
					if(isDeclared(arg, scope)) {
						String arg_type = ST.getType(arg, scope);
						String type_expected = ST.getParamType(i+1, func_name);
						if(!arg_type.equals(type_expected)) {
							System.out.println("Error: " + arg + " is of type " + arg_type + " expected type of " + type_expected);
						}
					}
					else {
						System.out.println("Error: " + arg + " is not declared in this scope");
					}
				}
			}
		}
	}

	public Object visit(ASTLess_Than node, Object data) {
		String childOne = node.jjtGetChild(0).jjtAccept(this, data).toString();
		String childTwo = node.jjtGetChild(1).jjtAccept(this, data).toString();
		if (!ST.getType(childOne, scope).equals("boolean"))
			System.out.println("Error: Expected boolean value but got value of (" + childOne + ") in conditional statement");	
		else if (!ST.getType(childTwo, scope).equals("boolean"))
			System.out.println("Error: Expected boolean value but got value of (" + childTwo + ") in conditional statement");
		else
			return DataType.TypeBoolean;
		return DataType.TypeUnknown;
	}

	public Object visit(ASTLess_Than_Or_Equal node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTGreater_Than node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTGreater_Than_Or_Equal node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTIdentifier node, Object data) {
		return node.value;
	}

	public Object visit(ASTNumber node, Object data) {
		return DataType.TypeInteger;
	}

	public Object visit(ASTBoolean node, Object data) {
		return DataType.TypeBoolean;
	}

	public Object visit(ASTMinus_sign node, Object data) {
		return node.value;
	}

	public Object visit(ASTReturn node, Object data) {
		return DataType.FunctionReturn;
	}

	public Object visit(ASTType node, Object data) {
		return node.value;
	}

	public Object visit(ASTFunction_call_structure node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTStatement_Begin_Structure node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTStatement_block node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

}
