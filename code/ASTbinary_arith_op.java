/* Generated By:JJTree: Do not edit this line. ASTbinary_arith_op.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTbinary_arith_op extends SimpleNode {
  public ASTbinary_arith_op(int id) {
    super(id);
  }

  public ASTbinary_arith_op(AssignmentTwo p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AssignmentTwoVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=83de240766e8cf4f9ba3297eb5e21c12 (do not edit this line) */
