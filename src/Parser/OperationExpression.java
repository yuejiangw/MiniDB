package Parser;
import file.FileReader;

public class OperationExpression {
    //----------------
    // Attributes
    //----------------
    private String operand1;
    private String operand2;
    private String operator;

    private boolean isOperand1Int;
    private boolean isOperand2Int;

    private int operand1Int;
    private int operand2Int;

    //----------------
    // Constructor(s)
    //----------------
    public OperationExpression() {
        operand1 = null;
        operand2 = null;
        operator = null;
        isOperand1Int = false;
        isOperand2Int = false;
        operand1Int = -1;
        operand2Int = -1;
    }


    //----------------
    // Accessors
    //----------------
    public String getOperand1() {
        return operand1;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isOperand1Int() {
        return isOperand1Int;
    }

    public void setOperand1Int(boolean operand1Int) {
        isOperand1Int = operand1Int;
    }

    public boolean isOperand2Int() {
        return isOperand2Int;
    }

    public void setOperand2Int(boolean operand2Int) {
        isOperand2Int = operand2Int;
    }

    public void setOperand1Int(int operand1Int) {
        this.operand1Int = operand1Int;
    }

    public int getOperand1Int() {
        return operand1Int;
    }

    public int getOperand2Int() {
        return operand2Int;
    }

    public void setOperand2Int(int operand2Int) {
        this.operand2Int = operand2Int;
    }


    //----------------
    // Other Methods.
    //----------------
    public boolean isEqual() {
        return getOperator().equals("=");
    }

    public boolean isGreater() {
        return getOperator().equals(">");
    }

    public boolean isLess() {
        return getOperator().equals("<");
    }

    public boolean isNotEqual() {
        return getOperator().equals("!=");
    }

    public boolean isGreaterEqual() {
        return getOperator().equals(">=");
    }

    public boolean isLessEqual() {
        return getOperator().equals("<=");
    }

    public void formalType() {
        if (FileReader.canParseInt(getOperand1()))
            setOperand1Int(Integer.parseInt(getOperand1()));
        if (FileReader.canParseInt(getOperand2()))
            setOperand2Int(Integer.parseInt(getOperand2()));
    }
}
