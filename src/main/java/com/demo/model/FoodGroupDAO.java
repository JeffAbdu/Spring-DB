package com.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository("foodGroupDAO")
public class FoodGroupDAO {

	private NamedParameterJdbcTemplate myJdbcTemplate; 

	@Autowired
	public void setMyJdbcTemplate(DataSource ds) {
		this.myJdbcTemplate = new NamedParameterJdbcTemplate(ds);
	} 
	
	public NamedParameterJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}

	public List<FoodGroup> getFoodGroups(){

		MapSqlParameterSource myMap = new MapSqlParameterSource();
		myMap.addValue("groupName", "Fruits");
		
		return myJdbcTemplate.query("select * from foodgroups where name=:groupName ", myMap, new RowMapper<FoodGroup>(){

			public FoodGroup mapRow(ResultSet rs, int rowNum) throws SQLException {

              FoodGroup fg = new FoodGroup();
              
              fg.setId(rs.getInt("id"));
              fg.setName(rs.getString("name"));
              fg.setDescription(rs.getString("description"));
              
              return fg;
				 
			}
			
		});
	}

	
	public FoodGroup getFoodGroup(int id){
		
		MapSqlParameterSource myMap = new MapSqlParameterSource();
		myMap.addValue("id", id);
		
		String sql = "select * from foodgroups where id = :id";
		return myJdbcTemplate.queryForObject(sql, myMap, new RowMapper<FoodGroup>(){

	 		public FoodGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
    
	 			FoodGroup fg = new FoodGroup(); 
                fg.setId(rs.getInt("id"));
                fg.setName(rs.getString("name"));
                fg.setDescription(rs.getString("description"));
				return fg;
				
			}} );	
	}

	
	public Boolean addFoodGroup(String name, String description){
		
		Boolean res = false;
		
		MapSqlParameterSource params = new  MapSqlParameterSource();
		params.addValue("name", name);
		params.addValue("description", description);
		
		String sql = "insert into foodGroups (name, description) values (:name, :description)";
		int numOfRowsAffected = myJdbcTemplate.update(sql, params);
		
		if (numOfRowsAffected==1){
			System.out.println("One row added to table foodGroups successfully");
			res=true;
		}
		return res;
	}
	

	// Same method as addFoodGroup, However, here Bean Properties is used as SQL Paramaters:
	public Boolean create(FoodGroup fg){
		
		Boolean res = false;
		
		// Adding the following line: Spring sets up the params in this parameter source object based on the properties of my FoodGroup:
		BeanPropertySqlParameterSource paramsBean = new BeanPropertySqlParameterSource(fg); 
		
		String sql = "insert into foodGroups (name, description) values (:name, :description)";
		int numOfRowsAffected = myJdbcTemplate.update(sql, paramsBean);
		
		if (numOfRowsAffected==1){
			System.out.println("One row added to table foodGroups successfully");
			res=true;
		}else{
			System.out.println("There was a problem adding to table foodGroup!!");
		}
		
		return res;		
	}
	
}
