
package com.NotSoFree.dto;

import java.util.List;
import lombok.Data;

@Data
public class PageDto<T> {
    
    private List<T> content;
    private int totalPages;
    private Long totalElements;
    
    public PageDto(){
        
    }
    
    public PageDto(List<T> content,int totalPages, Long totalElements){
        this.content= content;
        this.totalPages= totalPages;
        this.totalElements=totalElements;
    }
}
