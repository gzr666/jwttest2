package com.example.helpers;

import java.util.ArrayList;
import java.util.List;

import scala.annotation.meta.setter;

import com.example.dao.BookModel;
import com.example.dao.ReviewModel;
import com.example.dao.RoleModel;
import com.example.entity.Book;
import com.example.entity.Review;
import com.example.entity.Role;

public class ModelFactory 
{
	public static List<BookModel> CreateBooks(List<Book> books)
	{
		List<BookModel> bookModels = new ArrayList<BookModel>();
		for (Book b:books)
		{
			bookModels.add(Create(b));
			
		}
		return bookModels;
		
		
	}
	public static BookModel Create(Book book)
	{
		BookModel bookModel = new BookModel();
		bookModel.setId(book.getId());
		bookModel.setName(book.getName());
		List<ReviewModel> revlist = new ArrayList<ReviewModel>();
		
		for(Review review:book.getReviews())
		{
			
			revlist.add(Create(review));
			
			
		}
		bookModel.setReviews(revlist);
		
		return bookModel;
	
	}

	public static ReviewModel Create(Review review)
	{
		ReviewModel reviewModel = new ReviewModel();
		reviewModel.setContent(review.getContent());
		reviewModel.setId(review.getId());
		return reviewModel;
	}
	
	public static RoleModel Create(Role role)
	{
		RoleModel roleModel = new RoleModel();
		roleModel.setRoleName(role.getName());
		return roleModel;
	}
	public static List<RoleModel> Create(List<Role> roles)
	{
		List<RoleModel> roleModels = new ArrayList<RoleModel>();
		for(Role role : roles)
		{
			roleModels.add(Create(role));
		}
		
		return roleModels;
	}
	
	public static List<Role> CreateRoles(List<Role> roles)
	{
		List<Role> roleModels = new ArrayList<Role>();
		for(Role role : roles)
		{
			roleModels.add(role);
		}
		
		return roleModels;
	}
	
	
}
