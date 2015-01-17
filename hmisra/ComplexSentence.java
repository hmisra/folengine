

public class ComplexSentence {
	Premise premise;
	Conclusion conclusion;
	public Premise getPremise() {
		return premise;
	}
	public void setPremise(Premise premise) {
		this.premise = premise;
	}
	public Conclusion getConclusion() {
		return conclusion;
	}
	public void setConclusion(Conclusion conclusion) {
		this.conclusion = conclusion;
	}
	
	
	public ComplexSentence(Premise premise, Conclusion conclusion)
	{
		this.premise=premise;
		this.conclusion=conclusion;
		
	}
}
