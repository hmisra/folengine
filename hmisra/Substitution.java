
public class Substitution {
	String variable;
	String value;
	String variable1;
	String value1;
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Substitution(String variable, String value)
	{
		this.variable=variable;
		this.value=value;
	}
	public Substitution(String variable, String value, String variable1, String value1)
	{
		this.variable=variable;
		this.value=value;
		this.value1=value1;
		this.variable1=variable1;
	}
	
}
