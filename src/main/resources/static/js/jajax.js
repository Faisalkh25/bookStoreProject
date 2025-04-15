
$(document).ready(() => {
	
	   loadBooks();
	
	       //AddBookFormSubmission	   
		   $("#bookForm").submit(function(e) {
			   e.preventDefault();
			   
			 
			   
			let formData = new FormData(this);
			
			const book_id = $("#book_id").val();
			const url = book_id ? '/updateBook' : '/saveBook';
			const method = book_id ? 'PUT' : 'POST';
			   
			    $.ajax({
					url: url,
					type: method,
					data: formData,
					contentType: false,
					processData: false,
					success: function(response) {
                     showAlert("Book saved successfully", 'success');
					 resetForm();
					 loadBooks();
								
					 		},
							error:function(xhr, status, error) {
								showAlert('error saving book:' + error, 'danger');
							}
					
				});
			
		   });
		   
		   $(".cancelBtn").click(function(e) {
			   e.preventDefault();
			   resetForm();
		   }) 
		   
		   //load book table		   
		   function loadBooks() {
			   $.ajax({
				  url: '/list',
				  type: 'GET',
				  success: function(books) {
					   let tableBody = $("#booksTable");
					   tableBody.empty();
					   
					   books.forEach(book => {
						     let row = `
							     <tr>
								    <td>${book.book_id}</td>
									<td>${book.book_name}</td>
									<td>${book.author}</td>
									<td>${book.price.toFixed(2)}</td>
									<td>
									   ${book.imagePath ? `<img src="/uploads/${book.imagePath}" width="50">` :  "no image"}
									</td>
									
									<td>
									   <button class="btn btn-sm btn-primary editBtn" data-id="${book.book_id}">Edit</button>
									   <button class="btn btn-sm btn-danger deleteBtn" data-id="${book.book_id}">Delete</button>
									   </td>
								 </tr>
							 `;
							 tableBody.append(row);
					   });
					   
					   //edit btn event handlers
					   $('.editBtn').click(function() {
					                       const bookId = $(this).data('id');
					                       editBook(bookId);
					                   });
									   
									   
									   //delete btn event handlers
									   $('.deleteBtn').click(function() {
									                      const bookId = $(this).data('id');
									                      deleteBook(bookId);
									                  });

				  },
				  error: function(xhr, status, error) {
				                  showAlert('Error loading books: ' + error, 'danger');
				              }
			   });
		   }
			
		   //edit book function	   
		   function editBook(bookId) {
			   $.ajax({
				  url: '/book/' + bookId,
				  type: 'GET',
				  success: function(book) {
					$('#book_id').val(book.book_id),
					$('#book_name').val(book.book_name),
					$('#author').val(book.author),
					$('#price').val(book.price);
					
					if(book.imagePath) {
						$('#imagePreview').html(`<img src="/uploads/${book.imagePath}" width="100">`);
					}
					else {
						$('#imagePreview').empty();
					}
					
					$('#saveBtn').text('Update Book');
				    $('html, body').animate({scrollTop: 0}, 'slow');
				  },
				  
				  error: function(xhr, status, error) {
				                 showAlert('Error loading book: ' + error, 'danger');
				             }
			   });
		   }
 		    
		   //delete Book function
		   
		   function deleteBook(bookId) {
			      if(confirm('Are you sure you want to delete a book?')) {
					   $.ajax({
						  url: '/deleteBook/' + bookId,
						  type: 'DELETE',
						  success: function(response) {
							  showAlert("Book deleted successfully!", "success");
							  loadBooks();
						  },
						  error: function(xhr, status, error) {
                            showAlert('Error deleting book: ' + error, 'danger');
	                      }
					   });
				  }
		   }
		   
		   //reset Form	   
		   function resetForm() {
			   $('#bookForm')[0].reset();
			   $('#book_id').val('');
			   $('#imagePreview').empty();
			   $('#saveBtn').text('Add Book');
			   
			   $('#bookPhoto').val('');
		   }
		   
		   //showing alert messages
		   function showAlert(message, type) {
		          const alertDiv = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">
		                              ${message}
		                              <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		                            </div>`;
		          $('#alertContainer').html(alertDiv);
		          
		          // Auto dismiss after 3 seconds
		          setTimeout(() => {
		              $('.alert').alert('close');
		          }, 3000);
		      }
			  
			//showing all the featured books dynamically		
			if($('#featuredBookContainer').length)  {
				  loadFeaturedBooks();
				  
				         $(document).on('click', '.add-to-cart', function() {
				             const bookId = $(this).data('book-id');
				             addToCart(bookId);
				         });
			}
			
			function loadFeaturedBooks() {
				    $.ajax({
						url: '/list',
						type: 'GET',
						success: function(books) {
							renderFeaturedBooks(books);
						},
						error: function(xhr, status, error) {
						showAlert('Error deleting book: ' + error, 'danger');
					  }
					});
			}
			  
		  //render featured books
		  
		  function renderFeaturedBooks(books) {
		 const container = 	$('#featuredBookContainer');
		      container.empty();
			  
			 const featuredBooks =  books.slice(0,4);
			 
			   featuredBooks.forEach(book => {
				    const bookHtml = `
					      
					      <div class="col-md-3">
						       <div class="product-item">
							     <figure class="product-style">
								      ${book.imagePath ? 
									  `<img src="/uploads/${book.imagePath}" alt="${book.book_name}" 
									  class="product-item">` : `<img src="/images/default-book.png" alt="no image" class="product-item">`} 
									  <button type="button" class="add-to-cart" data-book-id="${book.book_id}">Add to
									  											Cart</button>
								 </figure>
								 <figcaption>
								   <h3>${book.book_name}</h3>
								   <span>${book.author}</span>
								   <div class="item-price">${book.price}</div>
								 </figcaption>
							   </div>
						  </div> 
					 
					`;
					container.append(bookHtml);
			   });
		  }
		  
		 
		 
});	