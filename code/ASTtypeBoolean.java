/* Generated By:JJTree: Do not edit this line. ASTtypeBoolean.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTtypeBoolean extends SimpleNode {
  public ASTtypeBoolean(int id) {
    super(id);
  }

  public ASTtypeBoolean(AssignmentTwo p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AssignmentTwoVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=899a59f5c33192691c7d25a4af5c2261 (do not edit this line) */
