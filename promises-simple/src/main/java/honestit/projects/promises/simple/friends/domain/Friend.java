package honestit.projects.promises.simple.friends.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import honestit.projects.promises.simple.users.domain.User;

import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Entity
@Table(name="friends")
@Getter @Setter @ToString
@EqualsAndHashCode(of = "id")
public class Friend {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Relation relation;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name="userId")
	private User owner;
	//if exists in database, then have a username
	private String username;
}