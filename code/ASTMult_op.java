/* Generated By:JJTree: Do not edit this line. ASTMult_op.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTMult_op extends SimpleNode {
  public ASTMult_op(int id) {
    super(id);
  }

  public ASTMult_op(AssignmentTwo p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AssignmentTwoVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3b9b6916a843015d576d04b3d39a80f0 (do not edit this line) */
