package in.org.bangles.api.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import in.org.bangles.api.Exception.ResourceNotFoundException;


@RestController
@RequestMapping("api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List<Category>> getAllCategory(@RequestParam(defaultValue="0") int page) {
	    int size =10;
		Pageable pageable = PageRequest.of(page, size );
		
		Page<Category> categoryPage = categoryRepository.findAll(pageable);
		
	    return ResponseEntity.ok(categoryPage.getContent());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getAllCategoryById(@PathVariable(value = "id") Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("category not found"));
		return ResponseEntity.of(Optional.of(category));
	}
	
	@PostMapping
	public Category createCategory(@RequestBody Category category) throws Exception {
		categoryRepository.save(category);
		throw new Exception("Data Inserted Successfully !!!");
	}

	
	@PutMapping("{id}")
	public ResponseEntity<Category> updateCategory(@RequestBody Category r, @PathVariable("id") Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Not found category with id = " + id));
		category.setTitle(r.getTitle());
		
		return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) throws Exception {
		Optional<Category> category = categoryRepository.findById(id);
         categoryRepository.deleteById(id);
			throw new Exception("Deleted Successfully !!!");
		
		}

	}
	

