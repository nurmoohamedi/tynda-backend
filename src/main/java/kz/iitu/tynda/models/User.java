package kz.iitu.tynda.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Column(name = "img_link")
	private String img_link;

	@Column(name = "subscribed")
	private boolean subscribed = false;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_playlists",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "playlist_id"))
	private List<Playlists> playlists = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_artists",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private List<Artist> artists = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_audiobooks",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "audiobook_id"))
	private List<Audiobook> audiobooks = new ArrayList<>();

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public List<String> getRolesAsList() {
		return roles.stream().map(item -> item.getName().toString()).collect(Collectors.toList());
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Playlists> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlists> playlists) {
		this.playlists = playlists;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public String getImg_link() {
		return img_link;
	}

	public void setImg_link(String img_link) {
		this.img_link = img_link;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}
}
