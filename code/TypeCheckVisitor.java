// Name:TypeCheckVisitor.java
// Author: David Sinclair      Date: 29 Aug 2012
//
// Visitor for basic type checking expressions in an abstract syntax tree in the ExprLang language
//

import java.util.*;

public class TypeCheckVisitor implements AssignmentTwoVisitor
{
    public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
    }

    public Object visit(ASTprogram node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return DataType.Program;
    }

    public Object visit(ASTMain node, Object data) {
		return DataType.Function;
    }

    public Object visit(ASTNemp_parameter_list node, Object data) {
		return (node.jjtGetChild(1).jjtAccept(this, data));
    }

    public Object visit(ASTParameter_list node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
    }

    public Object visit(ASTDecl node, Object data) {
		return DataType.Declaration;
    }

    public Object visit(ASTFunction node, Object data) {
		return DataType.Function;
    }

    public Object visit(ASTVar_decl node, Object data) {
		return DataType.Declaration;
    }

    public Object visit(ASTConst_decl node, Object data) {
		return DataType.Declaration;
    }

    public Object visit(ASTStatement_block node, Object data) {
		PrintVisitor pv = new PrintVisitor();
	
		if ((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeUnknown) {
			System.out.print("Type error: ");
			node.jjtGetChild(0).jjtAccept(pv, null);
			System.out.println();
	    }

		return (node.jjtGetChild(1).jjtAccept(this, data));
    }

	public Object visit(ASTStatement node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

    public Object visit(ASTFunction_list node, Object data) {
		PrintVisitor pv = new PrintVisitor();

		if ((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeUnknown) {
			System.out.print("Type error: ");
			node.jjtGetChild(0).jjtAccept(pv, null);
			System.out.println();
	    }

		return (node.jjtGetChild(1).jjtAccept(this, data));
    }

    public Object visit(ASTDec_list node, Object data) {
		PrintVisitor pv = new PrintVisitor();

		if ((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeUnknown) {
			System.out.print("Type error: ");
			node.jjtGetChild(0).jjtAccept(pv, null);
			System.out.println();
	    }
		return (node.jjtGetChild(0).jjtAccept(this, data));
    }

    public Object visit(ASTBinary_arith_op node, Object data) {
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger))
	    	return DataType.TypeInteger;
		else
	    	return DataType.TypeUnknown;
    }

    public Object visit(ASTBool_op node, Object data) {
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeBoolean) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeBoolean))
	    	return DataType.TypeBoolean;
		else
	    	return DataType.TypeUnknown;
    }

    public Object visit(ASTBinary_mult_op node, Object data) {
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger))
	    	return DataType.TypeInteger;
		else
	    	return DataType.TypeUnknown;
    }

	public Object visit(ASTNot_op node, Object data)
    {
		if ((DataType)node.jjtGetChild(0).jjtAccept(this, data) != DataType.TypeBoolean)
	    	return DataType.TypeUnknown;
		else
	    	return DataType.TypeBoolean;
    }

    public Object visit(ASTExp node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
    }

    public Object visit(ASTsimple_special_expression node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
    }

    public Object visit(ASTsimple_condition node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
    }

    public Object visit(ASTcondition node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
    }

    public Object visit(ASTidentifier node, Object data) {
		Hashtable ST = (Hashtable) data;
		STC hashTableEntry;

        hashTableEntry = (STC)ST.get(node.value);
		if (hashTableEntry.type == "Integer") {
			return DataType.TypeInteger;
	    }
		else if (hashTableEntry.type == "Boolean") {
			return DataType.TypeBoolean;
	    }
		else {
			return DataType.TypeUnknown;
	    }
    }

    public Object visit(ASTnumber node, Object data) {
		return DataType.TypeInteger;
    }

    public Object visit(ASTMinus_sign node, Object data) {
		return DataType.TypeUnknown;
    }

	public Object visit(ASTReturn node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTanyType node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTtypeInteger node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTtypeBoolean node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTAssignment_exp node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTFunction_call_statement node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}
}
