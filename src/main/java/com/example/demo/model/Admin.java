package com.example.demo.model;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config")
public class Admin {
		@Id
		int id;
		String uname;
		String upass;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
		public String getUpass() {
			return upass;
		}
		public void setUpass(String upass) {
			this.upass = upass;
		}
	
	}