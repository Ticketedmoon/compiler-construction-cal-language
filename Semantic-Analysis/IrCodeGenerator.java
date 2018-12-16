// Stuent: Shane Creedon
// Student ID: 15337356
// Compiler Construction - Assignment #2

import java.util.*;

public class IrCodeGenerator implements AssignmentTwoVisitor {

	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}
		
	public Object visit(ASTProgram node, Object data) {
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return DataType.Program;
	}

	// Print 'MAIN:' IR code here.
	// Followed by 'begin'.
	// After all children have been rendered, finish the 'end'.
	public Object visit(ASTMain node, Object data) {
		System.out.println("MAIN:" + '\n' + "\t\tbegin ");
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		System.out.println("\t\tend");
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
		String [] returnTemp = new String [0];

		// How many parameters are there?
		int paramCount = node.jjtGetChild(2).jjtGetNumChildren();
		// Function stack space (4 bytes per item / 32 bits)
		int functionSpaceBytes = paramCount * 4;

		// Extracts actual return value
		System.out.println(functionName.toUpperCase() + ":");
		System.out.println("\tbegin");
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			if(node.jjtGetChild(i).toString().equals("FuncReturn"))
				returnTemp = node.jjtGetChild(i).jjtAccept(this, data).toString().split(" ");
			else
				node.jjtGetChild(i).jjtAccept(this, data);
		}
	
		// Generate appropriate 3-address code for real value.
		// Temporary registers here for binary operators.
		int count = 1;
		String tCount = "";
		for (int i = 0; i < returnTemp.length-2; i+=2) {
			tCount = "t" + count;
			System.out.println("\t\t" + tCount + " = " + returnTemp[i] + " " + returnTemp[i+1] + " " + returnTemp[i+2]);
			returnTemp[i+2] = tCount;
			count++;
		}
		
		// Print out the return value as 3-address code
		// All previous declarations/statements in function 
		// should already be acknowledged.
		System.out.println(returnValue + tCount);
		System.out.println("\t\tpop " + functionSpaceBytes);
		System.out.println("\tend");
		
		return DataType.Function;
	}

	public Object visit(ASTVarDecl node, Object data) {
		String ID = node.jjtGetChild(0).jjtAccept(this, data).toString();
		// We only want the variable hugging the left edge if it's a declaration
		// at the beginning of the program.
		if (node.jjtGetParent().toString().equals("Program"))
			System.out.println("variable\t" + ID);
		return DataType.VariableDeclaration;
	}

	// Constants can have a type, value and identifier.
	public Object visit(ASTConstDecl node, Object data) {
		String ID = (String) node.jjtGetChild(0).jjtAccept(this, data).toString();
		String type = (String) node.jjtGetChild(1).jjtAccept(this, data).toString();
		String const_val = node.jjtGetChild(2).jjtAccept(this, data).toString();
		System.out.println("CONST\t" + ID + " = " + const_val);
		return DataType.ConstantDeclaration;
	}

	/* Statement Node */
	public Object visit(ASTStatement node, Object data) {
		int shift = 1;
		if (node.value != null) {
			// Do Stuff
		}
		else {
			int numChildren = node.jjtGetNumChildren();
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
		return node.value;
	}

	// Return function
	public Object visit(ASTFuncReturn node, Object data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
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
