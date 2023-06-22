package in.org.bangles.api.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import in.org.bangles.api.Category.CategoryRepository;
import in.org.bangles.api.Exception.ResourceNotFoundException;


@RestController
@RequestMapping("api/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Product>> getAllProduct(@RequestParam(defaultValue="0") int page) {
	    int size =10;
		Pageable pageable = PageRequest.of(page, size );
		
		Page<Product> productPage = productRepository.findAll(pageable);
		
	    return ResponseEntity.ok(productPage.getContent());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getAllProductById(@PathVariable(value = "id") Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("product id not found"));
		return ResponseEntity.of(Optional.of(product));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product r, @PathVariable("id") Long id) {
		
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Not found product with id = " + id));
		product.setTitle(r.getTitle());
		
		return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
	}
	
	
		@PostMapping("/{id}")
		public Product createProduct(@PathVariable(value = "id") Long id, @RequestBody Product product) throws Exception {
			categoryRepository.findById(id).map(r -> {
				if (product == null) {
					throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "NULL");
				}
			
				product.setCategory(r);
				return productRepository.save(product);
	
			}).orElseThrow(
					() -> new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Not found category with id = " + id));
			throw new Exception("Data Inserted Successfully !!!");
	}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws Exception {
			Optional<Product> product = productRepository.findById(id);
			productRepository.deleteById(id);
		    throw new Exception("Deleted Successfully !!!");
			
			}
	

}
