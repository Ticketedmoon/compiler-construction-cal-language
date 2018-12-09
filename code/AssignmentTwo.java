/* AssignmentTwo.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. AssignmentTwo.java */
 import java.io.*;
 import java.util.*;

 // Main class for passing in CAL code.
 class AssignmentTwo/*@bgen(jjtree)*/implements AssignmentTwoTreeConstants, AssignmentTwoConstants {/*@bgen(jjtree)*/
  protected JJTAssignmentTwoState jjtree = new JJTAssignmentTwoState();
  public static Hashtable ST = new Hashtable();

  public static void main(String[] args) throws ParseException, TokenMgrError, FileNotFoundException {

   String temp;
   STC temp2;

   if(args.length < 1) {
    System.out.println("Please pass in the filename.");
    System.exit(1);
   }

   // parser initialisation
   AssignmentTwo parser = new AssignmentTwo(new FileInputStream(args[0]));

   SimpleNode root = parser.program();
   System.out.println("Abstract Syntax Tree:");
   root.dump(" ");

   System.out.println();
   System.out.println("Symbol Table:");

   Enumeration t = ST.keys();

   while (t.hasMoreElements())
   {
     temp = (String)t.nextElement();
     temp2 = (STC)ST.get(temp);
     System.out.println(temp);
     if (temp2.type != null)
     {
    System.out.println(" type = " + temp2.type);
     }
     if (temp2.value != null)
     {
    System.out.println(" value = " + temp2.value);
     }
   }

   System.out.println();
   System.out.println("Program:");
   PrintVisitor pv = new PrintVisitor();
   root.jjtAccept(pv, null);

   System.out.println();
   System.out.println("Type Checking:");
   TypeCheckVisitor tc = new TypeCheckVisitor();
   root.jjtAccept(tc, ST);

  }

// Start production rule.
// 0 or 1 declaration lists, followed by
// 0 or 1 function lists, followed by
// our main function.
  final public SimpleNode program() throws ParseException {/*@bgen(jjtree) program */
  ASTprogram jjtn000 = new ASTprogram(JJTPROGRAM);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case VARIABLE:
      case CONSTANT:{
        dec_list();
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INTEGER:
      case BOOLEAN:
      case VOID:{
ASTFunction_list jjtn001 = new ASTFunction_list(JJTFUNCTION_LIST);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
        try {
          function_list();
        } catch (Throwable jjte001) {
if (jjtc001) {
      jjtree.clearNodeScope(jjtn001);
      jjtc001 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte001 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte001;}
    }
    if (jjte001 instanceof ParseException) {
      {if (true) throw (ParseException)jjte001;}
    }
    {if (true) throw (Error)jjte001;}
        } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001, true);
    }
        }
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        ;
      }
      main();
      jj_consume_token(0);
jjtree.closeNodeScope(jjtn000, true);
   jjtc000 = false;
{if ("" != null) return jjtn000;}
    } catch (Throwable jjte000) {
if (jjtc000) {
     jjtree.clearNodeScope(jjtn000);
     jjtc000 = false;
   } else {
     jjtree.popNode();
   }
   if (jjte000 instanceof RuntimeException) {
     {if (true) throw (RuntimeException)jjte000;}
   }
   if (jjte000 instanceof ParseException) {
     {if (true) throw (ParseException)jjte000;}
   }
   {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
    throw new Error("Missing return statement in function");
}

/* Declaration Rules */
// A declaration list has one or more declarations.
  final public void dec_list() throws ParseException {/*@bgen(jjtree) Dec_list */
  ASTDec_list jjtn000 = new ASTDec_list(JJTDEC_LIST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      label_1:
      while (true) {
ASTDecl jjtn001 = new ASTDecl(JJTDECL);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
        try {
          decl();
        } catch (Throwable jjte001) {
if (jjtc001) {
      jjtree.clearNodeScope(jjtn001);
      jjtc001 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte001 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte001;}
    }
    if (jjte001 instanceof ParseException) {
      {if (true) throw (ParseException)jjte001;}
    }
    {if (true) throw (Error)jjte001;}
        } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001, true);
    }
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case VARIABLE:
        case CONSTANT:{
          ;
          break;
          }
        default:
          jj_la1[2] = jj_gen;
          break label_1;
        }
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
     jjtree.clearNodeScope(jjtn000);
     jjtc000 = false;
   } else {
     jjtree.popNode();
   }
   if (jjte000 instanceof RuntimeException) {
     {if (true) throw (RuntimeException)jjte000;}
   }
   if (jjte000 instanceof ParseException) {
     {if (true) throw (ParseException)jjte000;}
   }
   {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

/* A declaration is comprised of either:
 * 1) A Variable declaration
 * 2) A Constant declaration
 */
  final public void decl() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VARIABLE:{
      var_decl();
      break;
      }
    case CONSTANT:{
      const_decl();
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

// Variable structure construct
  final public void var_decl() throws ParseException {Token t; String name;
    jj_consume_token(VARIABLE);
    name = identifier();
    jj_consume_token(COLON);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INTEGER:{
      typeInteger();
      jj_consume_token(SEMICOLON);
ASTVar_decl jjtn001 = new ASTVar_decl(JJTVAR_DECL);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
      try {
jjtree.closeNodeScope(jjtn001,  2);
    jjtc001 = false;
ST.put(name, new STC("Integer", name));
      } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001,  2);
    }
      }
      break;
      }
    case BOOLEAN:{
      typeBoolean();
      jj_consume_token(SEMICOLON);
ASTVar_decl jjtn002 = new ASTVar_decl(JJTVAR_DECL);
    boolean jjtc002 = true;
    jjtree.openNodeScope(jjtn002);
      try {
jjtree.closeNodeScope(jjtn002,  2);
    jjtc002 = false;
ST.put(name, new STC("Boolean", name));
      } finally {
if (jjtc002) {
      jjtree.closeNodeScope(jjtn002,  2);
    }
      }
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

// Constant structure construct
  final public void const_decl() throws ParseException {Token t; String name;
    jj_consume_token(CONSTANT);
    name = identifier();
    jj_consume_token(COLON);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INTEGER:{
      typeInteger();
      assignmentExpression();
ASTConst_decl jjtn001 = new ASTConst_decl(JJTCONST_DECL);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
      try {
jjtree.closeNodeScope(jjtn001,  3);
    jjtc001 = false;
ST.put(name, new STC("Integer", name));
      } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001,  3);
    }
      }
      break;
      }
    case BOOLEAN:{
      typeBoolean();
      assignmentExpression();
ASTConst_decl jjtn002 = new ASTConst_decl(JJTCONST_DECL);
    boolean jjtc002 = true;
    jjtree.openNodeScope(jjtn002);
      try {
jjtree.closeNodeScope(jjtn002,  3);
    jjtc002 = false;
ST.put(name, new STC("Boolean", name));
      } finally {
if (jjtc002) {
      jjtree.closeNodeScope(jjtn002,  3);
    }
      }
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

/* Function Rules */
// A function_list has one or more functions
  final public void function_list() throws ParseException {
ASTFunction jjtn001 = new ASTFunction(JJTFUNCTION);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
    try {
      function();
    } catch (Throwable jjte001) {
if (jjtc001) {
      jjtree.clearNodeScope(jjtn001);
      jjtc001 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte001 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte001;}
    }
    if (jjte001 instanceof ParseException) {
      {if (true) throw (ParseException)jjte001;}
    }
    {if (true) throw (Error)jjte001;}
    } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001, true);
    }
    }
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INTEGER:
    case BOOLEAN:
    case VOID:{
      function_list();
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      ;
    }
}

// Function definition
// Takes a parameter list with 0 or more parameters.
// Takes 0 or 1 declaration lists.
// Within the function begin, we define a statement block 0 or 1 times.
// Return 0 or 1 expressions. 
  final public void function() throws ParseException {
    anyType();
    identifier();
    jj_consume_token(LBRACE);
    parameter_list();
    jj_consume_token(RBRACE);
    jj_consume_token(IS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VARIABLE:
    case CONSTANT:{
      dec_list();
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      ;
    }
    jj_consume_token(BEGIN);
ASTStatement_block jjtn001 = new ASTStatement_block(JJTSTATEMENT_BLOCK);
     boolean jjtc001 = true;
     jjtree.openNodeScope(jjtn001);
    try {
      statement_block();
    } catch (Throwable jjte001) {
if (jjtc001) {
       jjtree.clearNodeScope(jjtn001);
       jjtc001 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte001 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte001;}
     }
     if (jjte001 instanceof ParseException) {
       {if (true) throw (ParseException)jjte001;}
     }
     {if (true) throw (Error)jjte001;}
    } finally {
if (jjtc001) {
       jjtree.closeNodeScope(jjtn001, true);
     }
    }
    function_return();
    jj_consume_token(END);
}

  final public void function_return() throws ParseException {
ASTReturn jjtn001 = new ASTReturn(JJTRETURN);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
    try {
      jj_consume_token(RETURN);
      jj_consume_token(LBRACE);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACE:
      case MINUS:
      case TRUE:
      case FALSE:
      case DIGIT:
      case ID:{
        expression();
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      jj_consume_token(RBRACE);
      jj_consume_token(SEMICOLON);
    } catch (Throwable jjte001) {
if (jjtc001) {
      jjtree.clearNodeScope(jjtn001);
      jjtc001 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte001 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte001;}
    }
    if (jjte001 instanceof ParseException) {
      {if (true) throw (ParseException)jjte001;}
    }
    {if (true) throw (Error)jjte001;}
    } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001, true);
    }
    }
}

// Defines parameter list.
// Either is a non-empty parameter list or an empty one (0 or 1)
  final public void parameter_list() throws ParseException {/*@bgen(jjtree) Parameter_list */
  ASTParameter_list jjtn000 = new ASTParameter_list(JJTPARAMETER_LIST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        nemp_parameter_list();
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        ;
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
     jjtree.clearNodeScope(jjtn000);
     jjtc000 = false;
   } else {
     jjtree.popNode();
   }
   if (jjte000 instanceof RuntimeException) {
     {if (true) throw (RuntimeException)jjte000;}
   }
   if (jjte000 instanceof ParseException) {
     {if (true) throw (ParseException)jjte000;}
   }
   {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

// Non-empty parameter list structure.
  final public void nemp_parameter_list() throws ParseException {/*@bgen(jjtree) #Nemp_parameter_list( 2) */
  ASTNemp_parameter_list jjtn000 = new ASTNemp_parameter_list(JJTNEMP_PARAMETER_LIST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      identifier();
      jj_consume_token(COLON);
      anyType();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        jj_consume_token(COMMA);
        nemp_parameter_list();
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        ;
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
     jjtree.clearNodeScope(jjtn000);
     jjtc000 = false;
   } else {
     jjtree.popNode();
   }
   if (jjte000 instanceof RuntimeException) {
     {if (true) throw (RuntimeException)jjte000;}
   }
   if (jjte000 instanceof ParseException) {
     {if (true) throw (ParseException)jjte000;}
   }
   {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000,  2);
   }
    }
}

// A statement block is simply 0 or more statements.
  final public void statement_block() throws ParseException {
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case ELSE:
      case WHILE:
      case BEGIN:
      case SKIP_TOKEN:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[11] = jj_gen;
        break label_2;
      }
      statement();
    }
}

// Statement structure - has many possible routes.
// Skip token is used to skip the expection of anything else inside statement.
  final public void statement() throws ParseException {/*@bgen(jjtree) Statement */
  ASTStatement jjtn000 = new ASTStatement(JJTSTATEMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        identifier();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ASSIGNMENT:{
          assignmentExpression();
          break;
          }
        case LBRACE:{
          functionCallStatement();
          break;
          }
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
        }
      case BEGIN:{
        jj_consume_token(BEGIN);
        statement_block();
        jj_consume_token(END);
        break;
        }
      case IF:{
        jj_consume_token(IF);
        condition();
        jj_consume_token(BEGIN);
        statement_block();
        jj_consume_token(END);
        break;
        }
      case ELSE:{
        jj_consume_token(ELSE);
        jj_consume_token(BEGIN);
        statement_block();
        jj_consume_token(END);
        break;
        }
      case WHILE:{
        jj_consume_token(WHILE);
        condition();
        jj_consume_token(BEGIN);
        statement_block();
        jj_consume_token(END);
        break;
        }
      case SKIP_TOKEN:{
        jj_consume_token(SKIP_TOKEN);
        jj_consume_token(SEMICOLON);
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
     jjtree.clearNodeScope(jjtn000);
     jjtc000 = false;
   } else {
     jjtree.popNode();
   }
   if (jjte000 instanceof RuntimeException) {
     {if (true) throw (RuntimeException)jjte000;}
   }
   if (jjte000 instanceof ParseException) {
     {if (true) throw (ParseException)jjte000;}
   }
   {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

// Fragment definition - tied to expression definition.
// An expression can evaluate to our list of primitive tokens - 
// Minus Digit, Minus ID, Digit, True, False.
  final public void fragment() throws ParseException {Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MINUS:{
      t = jj_consume_token(MINUS);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case DIGIT:{
        number();
        break;
        }
      case ID:{
        identifier();
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
ASTMinus_sign jjtn001 = new ASTMinus_sign(JJTMINUS_SIGN);
                                               boolean jjtc001 = true;
                                               jjtree.openNodeScope(jjtn001);
      try {
jjtree.closeNodeScope(jjtn001,  1);
                                               jjtc001 = false;
jjtn001.value = t.image;
      } finally {
if (jjtc001) {
                                                 jjtree.closeNodeScope(jjtn001,  1);
                                               }
      }
      break;
      }
    case DIGIT:{
      number();
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

/* Expression can be a simple_expression() followed by 0 or 1 more
 * Arithmetic operators and an expression.
 */
  final public void expression() throws ParseException {Token t;
    simple_expression();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PLUS:
    case MINUS:
    case MULT:
    case DIVIDE:{
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:{
        t = jj_consume_token(PLUS);
        break;
        }
      case MINUS:{
        t = jj_consume_token(MINUS);
        break;
        }
      case MULT:{
        t = jj_consume_token(MULT);
        break;
        }
      case DIVIDE:{
        t = jj_consume_token(DIVIDE);
        break;
        }
      default:
        jj_la1[16] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      expression();
ASTBinary_arith_op jjtn001 = new ASTBinary_arith_op(JJTBINARY_ARITH_OP);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
      try {
jjtree.closeNodeScope(jjtn001,  2);
    jjtc001 = false;
jjtn001.value = t.image;
      } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001,  2);
    }
      }
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      ;
    }
}

/* A simple expression defines either:
 * 1) A fragment as previously defined.
 * 2) An expression with a Left parenthese and Right parenthese surrounding.
 * 3) An ID followed by 0 or 1 Left parenthese + argument list + right parenthese.
 */
  final public void simple_expression() throws ParseException {/*@bgen(jjtree) Exp */
  ASTExp jjtn000 = new ASTExp(JJTEXP);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACE:{
        jj_consume_token(LBRACE);
        expression();
        jj_consume_token(RBRACE);
        break;
        }
      case ID:{
        identifier();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case LBRACE:{
          jj_consume_token(LBRACE);
          arg_list();
          jj_consume_token(RBRACE);
          break;
          }
        default:
          jj_la1[18] = jj_gen;
          ;
        }
        break;
        }
      case MINUS:
      case TRUE:
      case FALSE:
      case DIGIT:{
        fragment();
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

// A condition firstly can be instantiated with either a not token (~ tilde) or not.
// Following is a simple condition with 0 or 1 logical operators followed by a condition.
// Structure is very similar to expression() above but with the NOT token.
  final public void condition() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      ;
    }
    simple_condition();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BOOL_OP:{
      jj_consume_token(BOOL_OP);
      condition();
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      ;
    }
}

/* A simple condition takes a left bracket, condition, right bracket or
 * It takes a special_expression followed by 0 or 1 comparison operators and another
 * expression.
 * The special_expression is necessary for allowing the parser to have no difficulty,
 * when deciding what choice to make in our parser.
*/
  final public void simple_condition() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACE:{
      jj_consume_token(LBRACE);
      condition();
      jj_consume_token(RBRACE);
      break;
      }
    case MINUS:
    case TRUE:
    case FALSE:
    case DIGIT:
    case ID:{
      special_expression();
      comp_op();
      expression();
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

// Special Expression structure - similar to expression / condition.
  final public void special_expression() throws ParseException {Token t;
    simple_special_expression();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PLUS:
    case MINUS:
    case MULT:
    case DIVIDE:{
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:{
        t = jj_consume_token(PLUS);
        break;
        }
      case MINUS:{
        t = jj_consume_token(MINUS);
        break;
        }
      case MULT:{
        t = jj_consume_token(MULT);
        break;
        }
      case DIVIDE:{
        t = jj_consume_token(DIVIDE);
        break;
        }
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      special_expression();
ASTBinary_arith_op jjtn001 = new ASTBinary_arith_op(JJTBINARY_ARITH_OP);
    boolean jjtc001 = true;
    jjtree.openNodeScope(jjtn001);
      try {
jjtree.closeNodeScope(jjtn001,  2);
    jjtc001 = false;
jjtn001.value = t.image;
      } finally {
if (jjtc001) {
      jjtree.closeNodeScope(jjtn001,  2);
    }
      }
      break;
      }
    default:
      jj_la1[24] = jj_gen;
      ;
    }
}

// Similar to simple_expression, but without the <LBRACE> choice conflict.
  final public void simple_special_expression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACE:{
        jj_consume_token(LBRACE);
        arg_list();
        jj_consume_token(RBRACE);
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        ;
      }
      break;
      }
    case MINUS:
    case TRUE:
    case FALSE:
    case DIGIT:{
      fragment();
      break;
      }
    default:
      jj_la1[26] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

// Argument list is 0 or 1 non-empty argument lists.
  final public void arg_list() throws ParseException {/*@bgen(jjtree) Arg_list */
  ASTArg_list jjtn000 = new ASTArg_list(JJTARG_LIST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        nemp_arg_list();
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        ;
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

// Non-empty argument list, is a list of IDs with COMMAs separating them.
  final public void nemp_arg_list() throws ParseException {
    identifier();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      nemp_arg_list();
      break;
      }
    default:
      jj_la1[28] = jj_gen;
      ;
    }
}

  final public void assignmentExpression() throws ParseException {/*@bgen(jjtree) Assignment_exp */
  ASTAssignment_exp jjtn000 = new ASTAssignment_exp(JJTASSIGNMENT_EXP);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(ASSIGNMENT);
      expression();
      jj_consume_token(SEMICOLON);
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  final public void functionCallStatement() throws ParseException {/*@bgen(jjtree) Function_call_statement */
  ASTFunction_call_statement jjtn000 = new ASTFunction_call_statement(JJTFUNCTION_CALL_STATEMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(LBRACE);
      arg_list();
      jj_consume_token(RBRACE);
      jj_consume_token(SEMICOLON);
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

// List of our comparison operator tokens.
  final public void comp_op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQUALS:{
      jj_consume_token(EQUALS);
      break;
      }
    case NOT_EQUALS:{
      jj_consume_token(NOT_EQUALS);
      break;
      }
    case LESS_THAN:{
      jj_consume_token(LESS_THAN);
      break;
      }
    case LESS_THAN_OR_EQUAL:{
      jj_consume_token(LESS_THAN_OR_EQUAL);
      break;
      }
    case GREATER_THAN:{
      jj_consume_token(GREATER_THAN);
      break;
      }
    case GREATER_THAN_OR_EQUAL:{
      jj_consume_token(GREATER_THAN_OR_EQUAL);
      break;
      }
    default:
      jj_la1[29] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void number() throws ParseException {/*@bgen(jjtree) number */
 ASTnumber jjtn000 = new ASTnumber(JJTNUMBER);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(DIGIT);
jjtree.closeNodeScope(jjtn000, true);
               jjtc000 = false;
jjtn000.value = t.image;
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

  final public String identifier() throws ParseException {/*@bgen(jjtree) identifier */
 ASTidentifier jjtn000 = new ASTidentifier(JJTIDENTIFIER);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(ID);
jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
jjtn000.value = t.image; {if ("" != null) return t.image;}
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
    throw new Error("Missing return statement in function");
}

// Defines the type for AST matching.
  final public void anyType() throws ParseException {/*@bgen(jjtree) anyType */
 ASTanyType jjtn000 = new ASTanyType(JJTANYTYPE);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case VOID:{
        t = jj_consume_token(VOID);
        break;
        }
      case INTEGER:{
        t = jj_consume_token(INTEGER);
        break;
        }
      case BOOLEAN:{
        t = jj_consume_token(BOOLEAN);
        break;
        }
      default:
        jj_la1[30] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
jjtree.closeNodeScope(jjtn000, true);
                                                jjtc000 = false;
jjtn000.value = t.image;
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

  final public void typeInteger() throws ParseException {/*@bgen(jjtree) typeInteger */
 ASTtypeInteger jjtn000 = new ASTtypeInteger(JJTTYPEINTEGER);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(INTEGER);
jjtree.closeNodeScope(jjtn000, true);
                 jjtc000 = false;
jjtn000.value = t.image;
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

  final public void typeBoolean() throws ParseException {/*@bgen(jjtree) typeBoolean */
 ASTtypeBoolean jjtn000 = new ASTtypeBoolean(JJTTYPEBOOLEAN);
 boolean jjtc000 = true;
 jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(BOOLEAN);
jjtree.closeNodeScope(jjtn000, true);
                 jjtc000 = false;
jjtn000.value = t.image;
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

// Our main function definition.
  final public void main() throws ParseException {/*@bgen(jjtree) Main */
  ASTMain jjtn000 = new ASTMain(JJTMAIN);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(MAIN);
      jj_consume_token(BEGIN);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case VARIABLE:
      case CONSTANT:{
        dec_list();
        break;
        }
      default:
        jj_la1[31] = jj_gen;
        ;
      }
ASTStatement_block jjtn001 = new ASTStatement_block(JJTSTATEMENT_BLOCK);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
      try {
        statement_block();
      } catch (Throwable jjte001) {
if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte001;}
      }
      if (jjte001 instanceof ParseException) {
        {if (true) throw (ParseException)jjte001;}
      }
      {if (true) throw (Error)jjte001;}
      } finally {
if (jjtc001) {
        jjtree.closeNodeScope(jjtn001, true);
      }
      }
      jj_consume_token(END);
    } catch (Throwable jjte000) {
if (jjtc000) {
     jjtree.clearNodeScope(jjtn000);
     jjtc000 = false;
   } else {
     jjtree.popNode();
   }
   if (jjte000 instanceof RuntimeException) {
     {if (true) throw (RuntimeException)jjte000;}
   }
   if (jjte000 instanceof ParseException) {
     {if (true) throw (ParseException)jjte000;}
   }
   {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
     jjtree.closeNodeScope(jjtn000, true);
   }
    }
}

  /** Generated Token Manager. */
  public AssignmentTwoTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[32];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0xc00000,0xe000000,0xc00000,0xc00000,0x6000000,0x6000000,0xe000000,0xc00000,0x80000900,0x0,0x10,0x60000000,0x180,0x60000000,0x0,0x80000800,0x3c00,0x3c00,0x100,0x80000900,0x4000,0x8000,0x80000900,0x3c00,0x3c00,0x100,0x80000800,0x0,0x10,0x3f0000,0xe000000,0xc00000,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0xc1,0x80,0x0,0xa6,0x0,0xa6,0xc0,0x41,0x0,0x0,0x0,0xc1,0x0,0x0,0xc1,0x0,0x0,0x0,0xc1,0x80,0x0,0x0,0x0,0x0,};
	}

  /** Constructor with InputStream. */
  public AssignmentTwo(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public AssignmentTwo(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new AssignmentTwoTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public AssignmentTwo(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new AssignmentTwoTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new AssignmentTwoTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public AssignmentTwo(AssignmentTwoTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(AssignmentTwoTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 32; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[42];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 32; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 42; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private int trace_indent = 0;
  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

 }
