package edu.fra.uas.v2setter;

public class Journeyman {

	Work work;
	
	public Journeyman setWork(Work work) {
		this.work = work;
		return this;
	}
	
	public void performWork() {
		work.doWork();
	}

}
