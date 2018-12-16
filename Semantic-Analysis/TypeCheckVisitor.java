import java.util.*;

public class TypeCheckVisitor implements AssignmentTwoVisitor
{
	// Scope and Symbol Table declarations.
	private static String scope = "global";

	// Our Symbol Table Class (!Important)
	private static STC ST;

	// function calls / same declaration ID overlap sets.
	private static HashSet<String> functionCalls = new HashSet<>();

	// Hashtable for declarations that have already been announced in the same scope.
	private static Hashtable<String, LinkedHashSet<String>> identicalDeclarations = new Hashtable<>();
	
	// Method which determines if an ID has been declared in our SymbolTable. 
	// @return: Boolean value representing whether or not declartion already exists.
	private static boolean isDeclared(String id, String scope) {
		// Get the declarations in the scope currently active in the program.
		LinkedList<String> list = ST.get(scope);
		// Get the global scope declarations
		LinkedList<String> global_list = ST.get("global");
		if(list == null || (!list.contains(id))) {
			if (!global_list.contains(id))
				return false;
		}

		return true;
	}

	// Find out how many arguments were passed.
	// @return: int.
	private static int getPassedFuncArgs(ASTStatement node) {
		// Case where function call is direct and not assigned: E.G. func(argX)
		if (node.jjtGetNumChildren() > 2)
			return node.jjtGetChild(3).jjtGetNumChildren();
		// case where function call is assigned to a variable E.G. x := getMax(argY)
		else if (node.jjtGetNumChildren() == 2)
			return node.jjtGetChild(1).jjtGetNumChildren();
		// case where no arguments have been passed in function call. Just return 0.
		else
			return 0;
	}

	// Method checks for any identical declarations in the same scope
	private static void scanForIdenticalDeclarations() {
		if (!identicalDeclarations.isEmpty()) {
			Enumeration keys = identicalDeclarations.keys();
			System.out.println("\nDuplicate Declaration Checking...");
			while(keys.hasMoreElements()) {
				String scope = keys.nextElement().toString();
				LinkedHashSet<String> matches = identicalDeclarations.get(scope);
				Iterator iterator = matches.iterator();
				while(iterator.hasNext()) {
					System.out.println("Error: Declaration with ID (" + iterator.next() + ") has been declared more than once in scope [" +  scope  + "]");
				}
			}
		}
	}

	/* Method checks for any any function calls in program and prints them. */
	private static void scanForFunctionErrors() {
		// Print out all invoked functions for clarity
		System.out.println("\nChecking All Invoked Functions...");
		for (String s : functionCalls) 
			System.out.println("> Function with ID (" + s + ") was invoked.");

		// Print out all defined functions that have never been invoked as a warning.
		System.out.println("\nChecking For Uninvoked Functions...");
		for (String s : ST.getFunctionList()) {
			if (!functionCalls.contains(s))
				System.out.println("> Warning: Function with ID (" + s + ") has been defined but never invoked.");
		}
	}

	// Standard SimpleNode visit method
	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	// Program visitor method
	// We initalise our Symbol Table here.
	// We perform a for-loop iteration among the children of program 
	// (DeclarationList, FunctionList, Main)
	// After we can scan for duplicate/identical declarations
	// Followed by any errors in functions.
	public Object visit(ASTProgram node, Object data) {
		// Preprocess & setup
		ST = (STC) data;
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		// Print matching Declarations in same scope errors.
		scanForIdenticalDeclarations();
		// Print functional errors we encounter.
		scanForFunctionErrors();
		return DataType.Program;
	}

	// Update the scope to 'main' for the main function.
	// Each function should have it's own unique scope.
	public Object visit(ASTMain node, Object data) {
		this.scope = "main";
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	// Parameter node
	public Object visit(ASTParameter node, Object data) {
		return (node.jjtGetChild(1).jjtAccept(this, data));
	}

	// Parameter list - Cycle through all child parameters.
	public Object visit(ASTParameter_list node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	// Argument list node - Cycle through all child arguments.
	public Object visit(ASTArg_list node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	// New function! - update scope
	// Functions may have many children (type, ID, parameter_list, declarationList, StatementBlock)
	public Object visit(ASTFunction node, Object data) {
		this.scope = node.jjtGetChild(1).jjtAccept(this, data).toString();
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return DataType.Function;
	}

	// Method which builds the hashtable declared above checking for duplicate
	// declarations.
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

	// Constants can have a type, value and identifier.
	public Object visit(ASTConstDecl node, Object data) {
		String type = (String) node.jjtGetChild(1).jjtAccept(this, data).toString();
		String const_val = node.jjtGetChild(2).jjtAccept(this, data).toString();

		// Check numeric edge cases
		if (type.equals("integer") && const_val.equals("TypeInteger")) {
			return DataType.TypeInteger;
		}

		// Check Boolean edge cases
		else if (type.equals("boolean") && (const_val.equals("TypeBoolean") )) {
			return DataType.TypeBoolean;
		}

		else {
			System.out.println("Error: Constant with ID (" + node.jjtGetChild(0).jjtAccept(this, data) + ") type mismatch with assigned value, expected (" + type +  ").");
			return DataType.TypeUnknown;
		}

	}

	/* Statement Node *
 	 * This node contains most of our logic.
 	 * Will handle many different edge cases around type and semantic structure.
 	 */
	public Object visit(ASTStatement node, Object data) {
		if(node.jjtGetNumChildren() > 0 && !node.jjtGetChild(0).jjtAccept(this, data).toString().equals("TypeUnknown")) {
			String id = node.jjtGetChild(0).jjtAccept(this, data).toString();
		
			// function calls directly invoked / no assignment to it.
			if(ST.isFunction(id)) {
				isFunctionAvailable(id, node, data);
				return data;
        	}

			if (id.equals("TypeBoolean")) {
				return data;
			}
	
			// Check integer/boolean/function calls
        	if(isDeclared(id, scope)) {
            	String type = ST.getType(id, scope);
            	String description = ST.getDescription(id, scope);
            	if(description.equals("const")) {
                	System.out.println("Error: ID (" + id + ") is a constant and cannot be redeclared");
            	}
            	else {
            		String rval = node.jjtGetChild(2).jjtAccept(this, data).toString();
            		if(type.equals("integer")) {
						if (rval.equals("TypeInteger")) {
                    		node.jjtGetChild(1).jjtAccept(this, data);
						}
                		else if(rval.equals("TypeBoolean")) {
                    		System.out.println("Error: Expected assignment of type integer for ID (" + id + ") instead got boolean");
                		}
                		else if(ST.isFunction(rval)) {
                    		String func_name = node.jjtGetChild(2).jjtAccept(this, data).toString();
                    		// check if function is declared in global scope
                    		if(!isDeclared(func_name, "global") && !isDeclared(func_name, scope)) {
                        		System.out.println("Error: " + func_name + " is not declared");
                    		}     
                    		else if(ST.isFunction(func_name)) {
                        		// get return type of function
                                String func_return = ST.getType(func_name, "global");
								if(!func_return.equals("integer")) { 
									System.out.println("Error: Expected return type of integer instead got " + func_return);
								}
								isFunctionAvailable(func_name, node, data);
                			}
						}
						else {
							System.out.println("Error: Expected integer assignment for ID (" + id + ")");
						}
            		}
            		else if(type.equals("boolean")) {
              			if(rval.equals("TypeBoolean")) {
                    		node.jjtGetChild(1).jjtAccept(this, data);
                		}
                		else if(rval.equals("TypeInteger")) {
                    		System.out.println("Error: Expected type boolean for ID (" + id + ") instead got integer");
                		}
                		else if(ST.isFunction(rval)) {
                    		
							// check if function is declared in global scope
                    		if(!isDeclared(rval, "global")) {
                        		System.out.println(rval + " is not declared");
                    		}
                    		else {
                        		// get return type of function
                        		String func_return = ST.getType(rval, "global");
                        		if(!func_return.equals("boolean")) {
                            		System.out.println("Error: Expected return type of boolean instead got " + func_return + " for ID (" + id + ")");
                        		}

                    			else if(ST.isFunction(rval)) {
									isFunctionAvailable(rval, node, data);
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
			else if (isDeclared(childOne, scope) && ST.getType(childOne, scope).equals("integer") 
						&& isDeclared(childTwo, scope) && ST.getType(childTwo, scope).equals("integer")) {
				return DataType.TypeInteger;
			 }
		}
		return DataType.TypeUnknown;
	}

	// AND operation
	// Has two children always.
	public Object visit(ASTLogicalAND node, Object data) {
		return DataType.TypeBoolean;
	}

	// OR operation
	// Has two children always.
	public Object visit(ASTLogicalOR node, Object data) {
		return DataType.TypeBoolean;
	}

	// Equals operation
	// Has two children always.
	public Object visit(ASTEquals node, Object data) {
		return DataType.TypeBoolean;
	}

	// Not Equals operation
	// Has two children always.
	public Object visit(ASTNot_Equals node, Object data) {
		return DataType.TypeBoolean;
	}

	// This function checks the eligability of a function structure and invoked function calls.
	private void isFunctionAvailable(String func_name, ASTStatement node, Object data) {

		// Add function to invoked function list to check for later.
		functionCalls.add(func_name);		
		// Total No. Correct arguments expected
		int correct_args = ST.getParams(func_name);
		// Passed number of arguments from input file.
		int passed_args = this.getPassedFuncArgs(node);
		// check that the correct number of args is used
		if(correct_args != passed_args) 
			System.out.println("Error: Expected " + correct_args + " parameters instead got " + passed_args + " for invoked function: " + func_name);
		else if(correct_args == passed_args) {

			// check that the arguments are of the correct type
			if (node.jjtGetChild(1).jjtGetNumChildren() > 0) {
				Node arg_list;
				if (node.jjtGetChild(1).toString().equals("Arg_list"))
					arg_list = node.jjtGetChild(1);
				else
					arg_list = node.jjtGetChild(1).jjtGetChild(1);

				for(int i = 0; i < arg_list.jjtGetNumChildren(); i++) {
					String arg = (String)arg_list.jjtGetChild(i).jjtAccept(this, data);
		
					// check if argument in arglist is actually declared 
					if(isDeclared(arg, scope)) {
						String arg_type = ST.getType(arg, scope);
						String type_expected = ST.getParamType(i+1, func_name);
						if(!arg_type.equals(type_expected)) {
							System.out.println("Error: Argument with ID (" + arg + ") is of type " + arg_type + " expected type of " + type_expected);
						}
					}
					else if(isDeclared(arg, "global")) {
						String arg_type = ST.getType(arg, "global");
						String type_expected = ST.getParamType(i+1, func_name);
						if(!arg_type.equals(type_expected)) {
							System.out.println("Error: Argument with ID (" + arg + ") is of type " + arg_type + " expected type of " + type_expected);
						}
					}
					else {
						System.out.println("Error: Argument with ID (" + arg + ") is not declared in this scope");
					}
				}
			}
		}
	}

	// Less-Than Node
	// Has two children always.
	public Object visit(ASTLess_Than node, Object data) {
		return DataType.TypeBoolean;
	}

	// Less-Than-Or-Equal Node
	// Has two children always.
	public Object visit(ASTLess_Than_Or_Equal node, Object data) {
		return DataType.TypeBoolean;
	}

	// Greater-Than Node
	// Has two children always.
	public Object visit(ASTGreater_Than node, Object data) {
		return DataType.TypeBoolean;
	}

	// Greater-Than-Or-Equal Node
	// Has two children always.
	public Object visit(ASTGreater_Than_Or_Equal node, Object data) {
		return DataType.TypeBoolean;
	}

	// Identifier node - just return back the value.
	public Object visit(ASTIdentifier node, Object data) {
		return node.value;
	}

	// Integer node - all integers perform in the same way
	// unlike identifiers which can be translated to variables, function calls etc...
	// In the case of Integers, we return back the DataType.TypeInteger from our DataType.java class.
	public Object visit(ASTNumber node, Object data) {
		return DataType.TypeInteger;
	}

	// Same with boolean - return back the boolean type.
	public Object visit(ASTBoolean node, Object data) {
		return DataType.TypeBoolean;
	}

	// Minus symbol return back its innate value.
	public Object visit(ASTMinus_sign node, Object data) {
		return node.value;
	}

	// Return function - allows return statements to be smarter 
	// and more semantically correct.
	public Object visit(ASTFuncReturn node, Object data) {
		String functionType = ST.getType(scope, "global");
		if (node.jjtGetNumChildren() > 0) {
			String returnVal = node.jjtGetChild(0).jjtAccept(this, data).toString();
			if (!returnVal.equals("TypeInteger") && !ST.getType(returnVal, scope).equals(functionType)) {
				System.out.println("Error: Return value type mismatch with function declaration type");
			}
			else {
				if (ST.isFunction(returnVal)) {
					functionCalls.add(returnVal);	
				}
			}
		}
		else if (!functionType.equals("void")) {
			System.out.println("Error: Expected return value of type " + functionType + ", but got empty return.");
			return DataType.TypeUnknown;
		}
		return DataType.FunctionReturn;
	}

	// Type node - Integer, Boolean, Void
	public Object visit(ASTType node, Object data) {
		return node.value;
	}

	// Assignment - '='
	public Object visit(ASTAssignment node, Object data) {
		return node.value;
	}
}
