// Stuent: Shane Creedon
// Student ID: 15337356
// Compiler Construction - Assignment #2

import java.util.*;

public class IrCodeGenerator implements AssignmentTwoVisitor {

	private static int procedureToken = 1;

	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}
		
	public Object visit(ASTProgram node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	// Print 'MAIN:' IR code here.
	// Followed by 'begin'.
	// After all children have been rendered, finish the 'end'.
	public Object visit(ASTMain node, Object data) {
		System.out.println("MAIN:" + '\n' + "\tbegin ");
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		System.out.println("\tend");
		return data;
	}

	// Parameter node
	public Object visit(ASTParameter node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

	// Parameter list - Cycle through all child parameters.
	public Object visit(ASTParameter_list node, Object data) {
		if (node.jjtGetNumChildren() > 0)
			node.jjtGetChild(0).jjtAccept(this, data);
		return data;
	}

	// Argument list node - Cycle through all child arguments.
	public Object visit(ASTArg_list node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return node.value;
	}

	public Object visit(ASTFunction node, Object data) {
		String functionName = node.jjtGetChild(1).jjtAccept(this, data).toString();
		String returnValue = "\t\treturn ";

		// Function stack space (4 bytes per item / 32 bits)
		// Multiply by *2 to account for the paraemter ID + the parameter type
		int functionSpaceBytes = node.jjtGetChild(2).jjtGetNumChildren() * 4 * 2;

		// Extracts actual return value
		System.out.println(functionName.toUpperCase() + ":");
		System.out.println("\tbegin");
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			if(node.jjtGetChild(i).toString().equals("ReturnValue")) {
					returnValue += node.jjtGetChild(i).jjtAccept(this, data);
			}
			else
				node.jjtGetChild(i).jjtAccept(this, data);
		}
	
		// Generate appropriate 3-address code for real value.
		// Temporary registers here for binary operators.
		// Print out the return value as 3-address code
		// All previous declarations/statements in function 
		// should already be acknowledged.
		System.out.println(returnValue);
		System.out.println("\t\tpop " + functionSpaceBytes);
		System.out.println("\tend");
		
		return data;
	}

	public Object visit(ASTVarDecl node, Object data) {
		String ID = node.jjtGetChild(0).jjtAccept(this, data).toString();
		// We only want the variable hugging the left edge if it's a declaration
		// at the beginning of the program.
		if (node.jjtGetParent().toString().equals("Program"))
			System.out.println("variable\t" + ID);
		return data;
	}

	// Constants can have a type, value and identifier.
	public Object visit(ASTConstDecl node, Object data) {
		String ID = (String) node.jjtGetChild(0).jjtAccept(this, data).toString();
		String type = (String) node.jjtGetChild(1).jjtAccept(this, data).toString();
		String const_val = node.jjtGetChild(2).jjtAccept(this, data).toString();
		if (node.jjtGetParent().toString().equals("Program"))
			System.out.println("CONST\t" + ID + " = " + const_val);
		return data;
	}

	/* Statement Node */
	public Object visit(ASTStatement node, Object data) {
		
		// Store conditional semantics while generating IR.
		ArrayList<String> comparisonNodes = new ArrayList<>();
		ArrayList<String> identifiers = new ArrayList<>();
		ArrayList<String> special = new ArrayList<>();

		// Shift variable
		int shift = 0;

		// This condition is for the case of
		// IF/ELSE/WHILE statements.
		if (node.value != null) {
			if (node.value.equals("while") || node.value.equals("if")) {
				for (int i = 0; i < node.jjtGetNumChildren(); i++) {
					String n = node.jjtGetChild(i).toString();

					// Check for function returns
					if (n.equals("FunctionCall")) {
						identifiers.add(node.jjtGetChild(i).jjtAccept(this, data).toString());
						shift++;
					}

					// Check for Comparison Operators
					else if (n.equals("Compare")) {
						String number = node.jjtGetChild(i).jjtGetChild(0).jjtAccept(this, data).toString();
						String operator = node.jjtGetChild(i).jjtGetChild(1).jjtAccept(this, data).toString();
						String comparison = operator + " " + number;
						comparisonNodes.add(comparison);
						shift++;
					}

					// Check for Binary Conditionals
					else if(n.equals("LogicalAND") || n.equals("LogicalOR")) {
						special.add(node.jjtGetChild(i).jjtAccept(this, data).toString());
						shift++;	
					}
				}

 				String irPart = "";
				for(int i = 0; i < identifiers.size(); i++) {
					irPart += identifiers.get(i) + " ";
					if(comparisonNodes.size() > i)
						irPart += comparisonNodes.get(i);
					if(special.size() > i) {
						irPart +=  " " +  special.get(special.size()-i-1) + " ";
					}
				}

				System.out.println("\t" + node.value + "\t" + "(" + irPart.trim() + ")" + " goto L" + procedureToken);
				System.out.println("\tL" + procedureToken + ":");
				procedureToken++;
			}
		}

		// Non-conditional statements.
		int numChildren = node.jjtGetNumChildren();
		if (numChildren > 0) {
			// ID + CHILD
        	String id = node.jjtGetChild(shift).jjtAccept(this, data).toString();
			String childNode = node.jjtGetChild(shift+1).toString();        	

			if(childNode.equals("FunctionCall")) {
          		int n = node.jjtGetChild(shift+1).jjtGetNumChildren();
           		if(n > 0) {
               		String func_name = node.jjtGetChild(shift+1).jjtAccept(this, data).toString(); 
               		int children = node.jjtGetChild(shift+1).jjtGetChild(shift).jjtGetNumChildren();
               		Node child = node.jjtGetChild(shift+1).jjtGetChild(shift);
               		int param_count = 0;

               		for(int i = 0; i < children; i++) {
                 		String param = child.jjtGetChild(i).jjtAccept(this, data).toString();
                   		System.out.println("\t\tparam\t" + param);
                   		param_count++;
               		}
               		System.out.println("\t\t" + id + " = call " + func_name + ", " + param_count);
           		}
			}

			else if(childNode.equals("Arg_list")) {
            	int children = node.jjtGetChild(shift).jjtGetNumChildren();
            	for(int i = 0; i < children; i++) {
                	String param = node.jjtGetChild(shift).jjtGetChild(i).jjtAccept(this, data).toString();
                	System.out.println("\t\tpush\t" + param);
            	}
				System.out.println("\t\tgoto\t" +  id);
        	}

        	else {
				String assignmentValue = node.jjtGetChild(shift+1).jjtAccept(this, data).toString();
				if (assignmentValue != null && id != null) {
					System.out.println("\t\t" + id + " = " + assignmentValue);
				}
			}
		}
		return node.value;
	}
	/* Arithmetic operations are checked here
 	 * Valid operations involve DataType.TypeIntegers. */
	public Object visit(ASTAdd_Op node, Object data) {
		return node.jjtGetChild(0).jjtAccept(this, data) + " " + node.value + " " + node.jjtGetChild(1).jjtAccept(this, data);
	}

	// AND operation
	// Has two children always.
	public Object visit(ASTLogicalAND node, Object data) {
		return node.value;
	}

	// OR operation
	// Has two children always.
	public Object visit(ASTLogicalOR node, Object data) {
		return node.value;
	}

	// Equals operation
	// Has two children always.
	public Object visit(ASTEquals node, Object data) {
		return node.value;
	}

	// Not Equals operation
	// Has two children always.
	public Object visit(ASTNot_Equals node, Object data) {
		return node.value;
	}

	// Less-Than Node
	// Has two children always.
	public Object visit(ASTLess_Than node, Object data) {
		return node.value;
	}

	// Less-Than-Or-Equal Node
	// Has two children always.
	public Object visit(ASTLess_Than_Or_Equal node, Object data) {
		return node.value;
	}

	// Greater-Than Node
	// Has two children always.
	public Object visit(ASTGreater_Than node, Object data) {
		return node.value;
	}

	// Greater-Than-Or-Equal Node
	// Has two children always.
	public Object visit(ASTGreater_Than_Or_Equal node, Object data) {
		return node.value;
	}	

	// Identifier node - just return back the value.
	public Object visit(ASTIdentifier node, Object data) {
		return node.value;
	}

	public Object visit(ASTNumber node, Object data) {
		return node.value;
	}

	// Same with boolean - return back the boolean type.
	public Object visit(ASTBoolean node, Object data) {
		return node.value;
	}

	// Minus symbol return back its innate value.
	public Object visit(ASTMinus_sign node, Object data) {
		return "-" + node.jjtGetChild(0).jjtAccept(this, data);
	}

	// Return function
	public Object visit(ASTReturnValue node, Object data) {
		return "";
	}

	// Type node - Integer, Boolean, Void
	public Object visit(ASTType node, Object data) {
		return node.value;
	}

	// Assignment - '='
	public Object visit(ASTAssignment node, Object data) {
		return data;
	}

	public Object visit(ASTCompare node, Object data) {
		return node.value;
	}

	public Object visit(ASTFunctionCall node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return node.value;
	}
}
