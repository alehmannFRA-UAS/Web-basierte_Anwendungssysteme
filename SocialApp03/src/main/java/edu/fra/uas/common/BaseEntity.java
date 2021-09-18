package edu.fra.uas.common;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> {

	@Id
	@GeneratedValue
	private PK id;
	
	public PK getId() {
		return id;
	}
	
	public void setId(PK id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		
		if(!(obj instanceof BaseEntity<?>)) {
			return false;
		}
		
		BaseEntity<?> other = (BaseEntity<?>) obj;
		return id != null && id.equals(other.id);
	}
	
}
