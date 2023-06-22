package in.org.bangles.api.Category;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import in.org.bangles.api.Product.Product;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@Column(length = 25)
	private String title;
	 
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Set<Product> product = new HashSet<Product>();

	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
	}

	public Category(Long id, String title, Set<Product> product) {
		super();
		this.id = id;
		this.title = title;
		this.product = product;
	}

	
	

	
}
