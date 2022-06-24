package ll1;

public interface Configuration {

	//interfaces

	Configuration getNext();

	void doStep();

	boolean isAccepted();

	boolean isFitting();

	boolean canStep();

	boolean hasNext();
}
