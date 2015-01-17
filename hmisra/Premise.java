import java.util.ArrayList;


public class Premise {
	ArrayList<AtomicSentence> premiseSentences=new ArrayList<AtomicSentence>();

	public ArrayList<AtomicSentence> getPremiseSentences() {
		return premiseSentences;
	}
	
	public AtomicSentence getPremiseSentences(int i) {
	
		if(this.premiseSentences.size()>i+1 && i>=0)
		{
			return this.premiseSentences.get(i);
		}
		return null;
	}

	public void setPremiseSentences(AtomicSentence premiseSentences) {
		this.premiseSentences.add(premiseSentences);
	}
	
	
	
}
