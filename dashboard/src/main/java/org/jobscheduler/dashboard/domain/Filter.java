package org.jobscheduler.dashboard.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jobscheduler.dashboard.web.jsonview.BaseView;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name="filter")
public class Filter {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	long id;
	
	@Column(name="NAME")
	String name;
	
	@OneToMany(mappedBy="filter", fetch=FetchType.EAGER)
	@JsonManagedReference()
	@JsonView(NormalView.class)
	Set<Field> fields = null;
	
	public static interface NormalView extends BaseView {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Field> getFields() {
		return fields;
	}
	
	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}
	
	public void addField(Field field) {
		if (fields == null)
			fields = new HashSet<Field>();
		if (fields.contains(field)) {
			fields.remove(field);
		}
		this.fields.add(field);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filter other = (Filter) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Filter [id=" + id + ", name=" + name + ", fields=" + fields
				+ "]";
	}
	

}
