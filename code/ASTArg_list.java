/* Generated By:JJTree: Do not edit this line. ASTArg_list.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTArg_list extends SimpleNode {
  public ASTArg_list(int id) {
    super(id);
  }

  public ASTArg_list(AssignmentTwo p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AssignmentTwoVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5284c06f67abbb2bf1066871fa247ce1 (do not edit this line) */
