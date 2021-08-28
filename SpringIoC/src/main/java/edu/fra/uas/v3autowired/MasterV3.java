package edu.fra.uas.v3autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MasterV3 {

	@Autowired
	Journeyman journeyman;
	
	public void delegateWork() {
		journeyman.performWork();
	}
	
}
