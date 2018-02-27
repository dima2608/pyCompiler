package ast;

public class Write implements Statement {

	private Expression write;

	private int row = ASTNode.DEFAULT_ROW_COLUMN;
	private int column = ASTNode.DEFAULT_ROW_COLUMN;

	public Write(int i, int j, Expression variable) {
		this.row = i;
		this.column = j;
		this.write = variable;
	}

	@Override
	public int getLine() {
		return this.row;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public String toString() {
		return write.toString();
	}

}
