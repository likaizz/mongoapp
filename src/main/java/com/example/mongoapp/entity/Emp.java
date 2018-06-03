package com.example.mongoapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
public class Emp implements Serializable{

	private static final long serialVersionUID = 5777628310443550308L;
	public Emp() {
		super();
		System.out.println("Emp()");
	}
	
	private String ename;
	private String job;
	private Integer mgr;
	private Timestamp hiredate;
	private Double sal;
	private Double comm;

	

	public Integer getMgr() {
		return mgr;
	}


	public void setMgr(Integer mgr) {
		this.mgr = mgr;
	}


	public Timestamp getHiredate() {
		return hiredate;
	}


	public void setHiredate(Timestamp hiredate) {
		this.hiredate = hiredate;
	}


	public Double getSal() {
		return sal;
	}


	public void setSal(Double sal) {
		this.sal = sal;
	}


	public Double getComm() {
		return comm;
	}


	public void setComm(Double comm) {
		this.comm = comm;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emp other = (Emp) obj;
		if (ename != other.ename)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "{" +
				"ename:'" + ename + '\'' +
				", job:'" + job + '\'' +
				", mgr:" + mgr +
				", hiredate:" + hiredate +
				", sal:" + sal +
				", comm:" + comm +
				'}';
	}
}
