package seep.buffer;

import java.io.Serializable;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import seep.comm.serialization.BatchDataTuple;
import seep.comm.serialization.controlhelpers.BackupState;
import seep.comm.serialization.controlhelpers.StateI;

/**
* Buffer class models the buffers for the connections between operators in our system
*/

public class Buffer implements Serializable{

	private static final long serialVersionUID = 1L;

	private Deque<BatchDataTuple> buff = new LinkedBlockingDeque<BatchDataTuple>();
	
	private BackupState bs = null;

	public Iterator<BatchDataTuple> iterator() { return buff.iterator(); }

	public Buffer(){
		//state cannot be null. before backuping it would be null and this provokes bugs
//\bug The constructor in Buffer is operator dependant, this must be fixed by means of interfaces that make it independent.
		BackupState initState = new BackupState();
		initState.setOpId(0);
		initState.setTs_e(0);
		StateI state = null;
		initState.setState(state);
		bs = initState;
	}
	
	public int size(){
		return buff.size();
	}

	public BackupState getBackupState(){
		return bs;
	}

	public void saveStateAndTrim(BackupState bs){
		//Save state
		this.bs = bs;
		//Trim buffer, eliminating those tuples that are represented by this state
		trim(bs.getTs_e());
	}
	
	public void replaceBackupState(BackupState bs) {
		this.bs = bs;
	}

	public void save(BatchDataTuple batch){
		buff.add(batch);
	}
	
/// \test trim() should be tested
	public void trim(long ts){
//System.out.println("TO TRIM");
		Iterator<BatchDataTuple> iter = buff.iterator();
		int numOfTuplesPerBatch = 0;
		while (iter.hasNext()) {
			BatchDataTuple next = iter.next();
			long timeStamp = 0;
			numOfTuplesPerBatch = next.getBatchSize();
			//Accessing last index cause that is the newest tuple in the batch
			timeStamp = next.getTuple(numOfTuplesPerBatch-1).getTs();
//System.out.println("#events: "+numOfTuplesPerBatch+" timeStamp: "+timeStamp+" ts: "+ts);
			if (timeStamp <= ts) iter.remove();
			else break;
		}
	}
}