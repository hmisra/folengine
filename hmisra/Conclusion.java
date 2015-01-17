import java.util.ArrayList;


public class Conclusion {
	ArrayList<AtomicSentence> conclusionSentences=new ArrayList<AtomicSentence>();

	public ArrayList<AtomicSentence> getConclusionSentences() {
		return conclusionSentences;
	}
	
	public AtomicSentence getConclusionSentences(int i)
	{
		if(this.conclusionSentences.size()>i+1 && i>=0)
		{
			return this.conclusionSentences.get(i);
		}
		return null;
	}

	public void setConclusionSentences(AtomicSentence conclusionSentences) {
		this.conclusionSentences.add(conclusionSentences);
	}
	

}
