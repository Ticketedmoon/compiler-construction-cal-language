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

	public Object visit(ASTProgram node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		return DataType.Program;
	}

	public Object visit(ASTMain node, Object data) {
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
		System.out.println("declaration list built.");
		return data;
	}

	public Object visit(ASTArg_list node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTNemp_arg_list node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTFunction node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return DataType.Function;
	}

	public Object visit(ASTVarDecl node, Object data) {
		return DataType.Declaration;
	}

	public Object visit(ASTConstDecl node, Object data) {
		System.out.println("Declaration information: " + node.value);
		
		if (((String)node.jjtGetChild(1).jjtAccept(this, data)).equals("integer") && 
		    node.jjtGetChild(2).jjtAccept(this, data) == DataType.TypeInteger) 
		{	
			return DataType.Declaration;
		}
		System.out.println("Error: Constant with ID (" + node.jjtGetChild(0).jjtAccept(this, data) + ") type mismatch with assigned value.");
		return DataType.TypeUnknown;

	}

	public Object visit(ASTStatement node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
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
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger))
		return DataType.TypeInteger;
		else
		return DataType.TypeUnknown;
	}

	public Object visit(ASTMinus_Op node, Object data) {
		if ((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger)
			return DataType.TypeInteger;
		else
			return DataType.TypeUnknown;
	}

	public Object visit(ASTMult_Op node, Object data) {
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger))
		return DataType.TypeInteger;
		else
		return DataType.TypeUnknown;
	}

	public Object visit(ASTDiv_Op node, Object data) {
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger))
		return DataType.TypeInteger;
		else
		return DataType.TypeUnknown;
	}

	public Object visit(ASTLogical_Conjunction node, Object data) {
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeBoolean) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeBoolean))
		return DataType.TypeBoolean;
		else
		return DataType.TypeUnknown;
	}

	public Object visit(ASTLogical_Disjunction node, Object data) {
		System.out.print(node.jjtGetChild(0) + " - ");
		System.out.println(node.jjtGetChild(1));
		if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeBoolean) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeBoolean))
		return DataType.TypeBoolean;
		else
		return DataType.TypeUnknown;
	}

	public Object visit(ASTNot_op node, Object data) {
		if ((DataType)node.jjtGetChild(0).jjtAccept(this, data) != DataType.TypeBoolean)
		return DataType.TypeUnknown;
		else
		return DataType.TypeBoolean;
	}

	public Object visit(ASTExp node, Object data) {
		System.out.println("Expression");
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTBracket_Expression node, Object data) {
		System.out.println("Bracket Expression");
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTCondition node, Object data) {
		System.out.println("Condition");
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTEquals node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTNot_Equals node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTLess_Than node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTLess_Than_Or_Equal node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTGreater_Than node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTGreater_Than_Or_Equal node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTSimple_condition node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTsimple_special_expression node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTBracket_Condition node, Object data) {
		System.out.println("Bracketed Condition");
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTIdentifier node, Object data) {
		return node.value;
	}

	public Object visit(ASTNumber node, Object data) {
		return DataType.TypeInteger;
	}

	public Object visit(ASTMinus_sign node, Object data) {
		return DataType.TypeUnknown;
	}

	public Object visit(ASTReturn node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTType node, Object data) {
		System.out.println("Type: " + node.value);
		return node.value;
	}

	public Object visit(ASTAssignment_structure node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTFunction_call_structure node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTStatement_Begin_Structure node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTStatement_block node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		return data;
	}

}
