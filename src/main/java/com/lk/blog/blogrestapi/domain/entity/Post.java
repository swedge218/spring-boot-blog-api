package com.lk.blog.blogrestapi.domain.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy.Strategy;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 2, message = "Post title must have at least 2 characters")
	@Column(unique = true, nullable = false)
	private String title;
	
	@NotEmpty
	@Size(min = 2, message = "Post description must have at least 5 characters")
	@Column(nullable = false)
	private String description;
	
	@NotEmpty
	@Column(nullable = false)
	private String content;
	
	@OneToMany(
			mappedBy = "post", 
			fetch = FetchType.LAZY,  
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();

	@Override
	public int hashCode() {
		return Objects.hash(content, description, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(content, other.content) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content + "]";
	}

	
	
	
}
