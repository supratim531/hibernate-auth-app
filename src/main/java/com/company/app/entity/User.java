package com.company.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "my_users", uniqueConstraints = { @UniqueConstraint(columnNames = "email_address") })
public class User {

	@Id
	private String username;

	private String password;

	@Column(name = "email_address")
	private String emailAddress;

}
