public enum DataType
{
	// Program level 
    Program,

	// Function Data - Main included
	Function,
	FunctionReturn,
	Main,

	// Declarations of both Var/Const types.
    VariableDeclaration,
	ConstantDeclaration,

	// Expressional Data / Statements
	ParameterList,
	Statement,
	Assignment,
	Expression,

	// Lowest Level types
    TypeUnknown,
    TypeInteger,
    TypeBoolean,
	TypeIdentifier
}
