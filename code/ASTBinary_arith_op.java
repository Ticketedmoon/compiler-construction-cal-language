/* Generated By:JJTree: Do not edit this line. ASTBinary_arith_op.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTBinary_arith_op extends SimpleNode {
  public ASTBinary_arith_op(int id) {
    super(id);
  }

  public ASTBinary_arith_op(AssignmentTwo p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AssignmentTwoVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=11ea14d8e189a311cd967e9e189fd556 (do not edit this line) */
