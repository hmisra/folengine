import java.util.ArrayList;


public class AtomicSentence {
	String sentence;
	int sentenceNumber;
	String predicateName;
	int numberOfArguments;
	ArrayList<String> values;
	
	public AtomicSentence(String sentence,int sentenceNumber, String predicateName, int numberOfArguments, ArrayList<String> values) {
		this.sentence=sentence;
		this.sentenceNumber=sentenceNumber;
		this.predicateName=predicateName;
		this.numberOfArguments=numberOfArguments;
		this.values=values;
		// TODO Auto-generated constructor stub
	}
	
	
	public String getSentence() {
		return sentence;
	}


	public void setSentence(String sentence) {
		this.sentence = sentence;
	}


	public int getSentenceNumber() {
		return sentenceNumber;
	}


	public void setSentenceNumber(int sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}


	public String getPredicateName() {
		return predicateName;
	}


	public void setPredicateName(String predicateName) {
		this.predicateName = predicateName;
	}


	public int getNumberOfArguments() {
		return numberOfArguments;
	}


	public void setNumberOfArguments(int numberOfArguments) {
		this.numberOfArguments = numberOfArguments;
	}


	public ArrayList<String> getValues() {
		return values;
	}


	public void setValues(ArrayList<String> values) {
		this.values = values;
	}


	public void printAtomicSentence()
	{
		System.out.println("Sentence : "+ sentence);
		System.out.println("Sentence Number : "+ sentenceNumber);
		System.out.println("Predicate : "+predicateName);
		System.out.println("Number Of Arguments : "+ numberOfArguments);
		System.out.println(" Values : ");
		for(String value: values)
		{
			System.out.print (value+ "  " );
		}
		System.out.println();
	}	
	
}
