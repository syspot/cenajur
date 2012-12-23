package br.com.cenajur.model;

public class ColumnModel {

	 private String header;
     private String property; 

     public ColumnModel(String header, String property) {  
         this.header = header;
         this.property = property;  
     }

     public String getHeader() {  
         return header;  
     }

     public String getProperty() {  
         return property;  
     }
}
