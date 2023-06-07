package kz.iitu.tynda.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private String email;
	private String img_link;
	private boolean subscribed = false;
	private List<String> roles = new ArrayList<>();

	public UserDTO() {}

	public UserDTO(Long id, String username, String email, String img_link, boolean subscribed) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.img_link = img_link;
		this.subscribed = subscribed;
	}
}
