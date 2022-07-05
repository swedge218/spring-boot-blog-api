package com.lk.blog.blogrestapi.service.decorator;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.lk.blog.blogrestapi.domain.entity.Comment;

import lombok.Data;

@Data
public class CustomPage<T> {
  List<T> content;
  CustomPageable metadata;

  public CustomPage(Page<T> page) {
    this.content = page.getContent();
    this.metadata = new CustomPageable(page);
  }

  public static CustomPage setup(Page page) {
  	return new CustomPage(page);
  }
  
  @Data
  class CustomPageable {
    int pageNumber;
    int pageSize;
    int numberOfElements;
    int totalPages;
    long totalElements;
    long offset;
    boolean paged;
    boolean first;
    boolean last;
    boolean empty;
    boolean sorted;
    
   
    public CustomPageable(Page<T> page) {
    	Pageable pageable = page.getPageable();
    	
    	this.pageNumber = pageable.getPageNumber();
    	this.pageSize = pageable.getPageSize();
    	this.numberOfElements = page.getNumberOfElements();
    	this.totalPages = page.getTotalPages();
    	this.totalElements = page.getTotalElements();
    	this.offset = pageable.getOffset();
    	this.paged = pageable.isPaged();
    	this.first = page.isFirst();
    	this.last = page.isLast();
    	this.empty = page.isEmpty();
    	this.sorted = page.getSort().isSorted();
    	
    }
   
  }
}