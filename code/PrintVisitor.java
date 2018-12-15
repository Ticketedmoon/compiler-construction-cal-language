// Name:PrintVisitor.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Visitor for "pretty printing" an abstract syntax tree in the ExprLang language
//

public class PrintVisitor implements AssignmentTwoVisitor
{
    public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
    }

    public Object visit(ASTProgram node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++)
    		node.jjtGetChild(i).jjtAccept(this, data);
		return data;
    }
    
    public Object visit(ASTDeclarationList node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++)
    		node.jjtGetChild(i).jjtAccept(this, data);
		return data;
    }
    
    public Object visit(ASTVarDecl node, Object data) {	
		System.out.print("variable ");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" : ");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println(";");
		return data;
    }

	public Object visit(ASTConstDecl node, Object data) {
        System.out.print("constant ");
        node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" : ");
        node.jjtGetChild(1).jjtAccept(this, data);	
		System.out.print(" := ");	
        node.jjtGetChild(2).jjtAccept(this, data);
		System.out.println(";");
        return data;
    }

	public Object visit(ASTFunction node, Object data) {
		System.out.println();
        node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" ");
        node.jjtGetChild(1).jjtAccept(this, data);
		System.out.print("(");
		node.jjtGetChild(2).jjtAccept(this, data);

		System.out.println(") is");
		for (int i = 0; i < node.jjtGetChild(3).jjtGetNumChildren(); i++) {
			System.out.print("    ");
			node.jjtGetChild(3).jjtGetChild(i).jjtAccept(this, data);
		}

		System.out.println("    begin");
		for(int i = 4; i < node.jjtGetNumChildren(); i++) {
        	node.jjtGetChild(i).jjtAccept(this, data);
		}

		System.out.println("\n    end");
        return data;
    }
    
    public Object visit(ASTStatement node, Object data) {		
		if (node.value != null)
			System.out.print(node.value + " ");
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
    		node.jjtGetChild(i).jjtAccept(this, data);
		}
		System.out.println();
		return data;
    }

    public Object visit(ASTType node, Object data) {		
		System.out.print(node.value);
		return data;
    }

    public Object visit(ASTAssignment_structure node, Object data) {		
		System.out.print(" := ");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(";");
		return data;
    }

	
    public Object visit(ASTStatement_block node, Object data) {		
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
    		node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
    }

    public Object visit(ASTStatement_Begin_Structure node, Object data) {		
		System.out.println("\nbegin");
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
    		node.jjtGetChild(i).jjtAccept(this, data);
		}
		System.out.print("end");
		return data;
    }

    public Object visit(ASTFunction_call_structure node, Object data) {		
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTParameter node, Object data) {
    	node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(": ");
    	node.jjtGetChild(1).jjtAccept(this, data);

		if (node.jjtGetNumChildren() > 2) {
			System.out.print(", ");
			node.jjtGetChild(2).jjtAccept(this, data);
		}
		return data;
    }

    public Object visit(ASTFunctionList node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++)
    		node.jjtGetChild(i).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTParameter_list node, Object data) {
		if (node.jjtGetNumChildren() > 0)
			node.jjtGetChild(0).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTArg_list node, Object data) {
		System.out.print("(");
		if (node.jjtGetNumChildren() > 0)
    		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(")");	
		return data;
    }

    public Object visit(ASTNemp_arg_list node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
    		node.jjtGetChild(i).jjtAccept(this, data);
			if (i < node.jjtGetNumChildren()-1)
				System.out.print(", ");
		}
		return data;
    }

    public Object visit(ASTAdd_Op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTMinus_Op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTMult_Op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTDiv_Op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTLogical_Conjunction node, Object data) {
		node.jjtGetChild(0).jjtAccept(this,data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTLogical_Disjunction node, Object data) {
		node.jjtGetChild(0).jjtAccept(this,data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTNot_op node, Object data) {
		System.out.print("~");
		node.jjtGetChild(0).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTEquals node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTNot_Equals node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTLess_Than node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTLess_Than_Or_Equal node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTGreater_Than node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTGreater_Than_Or_Equal node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTSimple_condition node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++)
    		node.jjtGetChild(i).jjtAccept(this, data);
		return(data);
	}

    public Object visit(ASTExp node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++)
    		node.jjtGetChild(i).jjtAccept(this, data);
		return(data);
    }

    public Object visit(ASTCondition node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return(data);
    }

    public Object visit(ASTBracket_Condition node, Object data) {
		System.out.print("(");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(")");
		return(data);
    }

    public Object visit(ASTBracket_Expression node, Object data) {
		System.out.print("(");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(")");
		return(data);
    }

    public Object visit(ASTsimple_special_expression node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++)
    		node.jjtGetChild(i).jjtAccept(this, data);
		return(data);
    }

    public Object visit(ASTIdentifier node, Object data) {
		System.out.print(node.value);
		return data;
    }

    public Object visit(ASTMinus_sign node, Object data) {
		System.out.print(node.value);
    	node.jjtGetChild(0).jjtAccept(this, data);
		return data;
    }

    public Object visit(ASTNumber node, Object data) {
		System.out.print(node.value);
		return data;
    }

    public Object visit(ASTBoolean node, Object data) {
		System.out.print(node.value);
		return data;
    }

	public Object visit(ASTReturn node, Object data) {
		System.out.print("        return ");
		if(node.jjtGetNumChildren() == 0) 
			System.out.print("()");
		else  {
			System.out.print("(");
			node.jjtGetChild(0).jjtAccept(this, data);
			System.out.print(")");
		}

		System.out.print(";");
		return data;
	}

    public Object visit(ASTMain node, Object data) {
		System.out.println();
		System.out.println("main");
		System.out.println("    begin");

		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			if (node.jjtGetChild(i) instanceof ASTDec_list) {
				System.out.print("        ");
    			node.jjtGetChild(i).jjtAccept(this, data);	
			}
			else
    			node.jjtGetChild(i).jjtAccept(this, data);
		}
		System.out.println("    end");
		return data;
    }
}
