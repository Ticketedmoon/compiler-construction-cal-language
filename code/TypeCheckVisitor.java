// Name:TypeCheckVisitor.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Visitor for basic type checking expressions in an abstract syntax tree in the ExprLang language
//

import java.util.*;

public class TypeCheckVisitor implements AssignmentTwoVisitor
{
	private static String scope = "global";
	private static STC ST;
	private static HashSet<String> invokedFunctions = new HashSet<>();
	
	private static boolean isDeclared(String id, String scope) {
		// Get the declarations in the scope currently active in the program.
		LinkedList<String> list = ST.get(scope);

		// Get the globa scope declarations
		LinkedList<String> global_list = ST.get("global");
		if(list != null) {
			if(!global_list.contains(id) && !list.contains(id)) {
				return false;
			}
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

	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	public Object visit(ASTProgram node, Object data) {
		// Preprocess & setup
		ST = (STC) data;

		// begin semantic analysis
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return DataType.Program;
	}

	public Object visit(ASTMain node, Object data) {
		this.scope = "main";
		return DataType.Function;
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
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTNemp_arg_list node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTFunction node, Object data) {
		this.scope = (String) node.jjtGetChild(1).jjtAccept(this, data);
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return DataType.Function;
	}

	public Object visit(ASTVarDecl node, Object data) {
		return DataType.VariableDeclaration;
	}

	public Object visit(ASTConstDecl node, Object data) {
		String type = (String) node.jjtGetChild(1).jjtAccept(this, data);
		String const_val = (String) node.jjtGetChild(2).jjtAccept(this, data);
		if (type.equals("integer") && isNumeric(const_val)) {
			return DataType.ConstantDeclaration;
		}
		System.out.println("Error: Constant with ID (" + node.jjtGetChild(0).jjtAccept(this, data) + ") type mismatch with assigned value.");
		return DataType.TypeUnknown;

	}

	public Object visit(ASTStatement node, Object data) {
		if(node.jjtGetNumChildren() > 0) {
			String id = (String)node.jjtGetChild(0).jjtAccept(this, data);
        
			// functions with no previous statement contain func name as id
			if(ST.isFunction(id)) {
            	invokedFunctions.add(id);
        	}

        	if(isDeclared(id, scope)) {
            	String type = ST.getType(id, scope);
            	String description = ST.getDescription(id, scope);
            	if(description.equals("const")) {
                	System.out.println("Error: " + id + " is a constant and cannot be redeclared");
            	}
            	else {
            		String rhs = node.jjtGetChild(1).toString();
            		if(type.equals("integer")) {
                		if(rhs.equals("Num"))
                		{
                    		node.jjtGetChild(1).jjtAccept(this, data);
                		}
                		else if(rhs.equals("BoolOp")) {
                    		System.out.println("Error: Expected type integer instead got boolean");
                		}
                		else if(rhs.equals("FuncReturn")) {
                    		String func_name = (String) node.jjtGetChild(1).jjtAccept(this, data);
                    		// check if function is declared in global scope
                    		if(!isDeclared(func_name, "global") && !isDeclared(func_name, scope)) {
                        		System.out.println("Error: " + func_name + " is not declared");
                    		}     
                    		else if(ST.isFunction(func_name)) {
                        		invokedFunctions.add(func_name);
                        		// get return type of function
                        		String func_return = ST.getType(func_name, "global");
                        		if(!func_return.equals("integer")) {
                            		System.out.println("Error: Expected return type of integer instead got " + func_return);
                        		}
                
                    			int num_args = ST.getParams(func_name);
                    			// Statement -> FuncReturn -> ArgList -> children of arglist
                    			int actual_args = node.jjtGetChild(1).jjtGetChild(0).jjtGetNumChildren();
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
            		}
            	else if(type.equals("boolean")) {
              		if(rhs.equals("BoolOp"))
                	{
                    	node.jjtGetChild(1).jjtAccept(this, data);
                	}
                	else if(rhs.equals("Num")) {
                    	System.out.println("Error: Expected type boolean instead got integer");
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
            	}
        		}
        	}
        	else if(!isDeclared(id, scope)) {
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

	public Object visit(ASTAdd_Op node, Object data) {
		System.out.println("here");
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && (DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger) {
			System.out.println("first");
			return DataType.TypeInteger;
		}
		else if ((node.jjtGetChild(1).jjtAccept(this, data) != DataType.TypeInteger 
 && node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && isDeclared((String) node.jjtGetChild(1).jjtAccept(this, data), scope)) {
			System.out.println("helllloo)");
			return data;
		}
			
		else {
			System.out.println("--> " + node.jjtGetChild(1).jjtAccept(this, data));
			return DataType.TypeUnknown;
		}
	}

	public Object visit(ASTMinus_Op node, Object data) {
		if (node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger)
			return DataType.TypeInteger;
		else
			return DataType.TypeUnknown;
	}

	public Object visit(ASTLogical_Conjunction node, Object data) {
		if ((node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeBoolean) && (node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeBoolean))
			return DataType.TypeBoolean;
		else
			return DataType.TypeUnknown;
	}

	public Object visit(ASTLogical_Disjunction node, Object data) {
		return data;
		/*if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeBoolean) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeBoolean))
		return DataType.TypeBoolean;
		else
		return DataType.TypeUnknown;*/
	}

	public Object visit(ASTNot_op node, Object data) {
		if ((DataType)node.jjtGetChild(0).jjtAccept(this, data) != DataType.TypeBoolean)
			return DataType.TypeUnknown;
		else
			return DataType.TypeBoolean;
	}

	public Object visit(ASTExp node, Object data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
		// return DataType.Expression;
	}

	public Object visit(ASTBracket_Expression node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTCondition node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTEquals node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTNot_Equals node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTLess_Than node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
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

	public Object visit(ASTSimple_condition node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTsimple_special_expression node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTBracket_Condition node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	public Object visit(ASTIdentifier node, Object data) {
		return node.value;
	}

	public Object visit(ASTNumber node, Object data) {
		return node.value;
	}

	public Object visit(ASTMinus_sign node, Object data) {
		return DataType.TypeUnknown;
	}

	public Object visit(ASTReturn node, Object data) {
		return DataType.FunctionReturn;
	}

	public Object visit(ASTType node, Object data) {
		return node.value;
	}

	public Object visit(ASTAssignment_structure node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
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
