package com.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("foodGroupDAO")
public class FoodGroupDAO {

	private NamedParameterJdbcTemplate myJdbcTemplate; 
	
	private SimpleJdbcInsert insertFoodGroup;

	@Autowired
	public void setMyJdbcTemplate(DataSource ds) {
		this.myJdbcTemplate = new NamedParameterJdbcTemplate(ds);
		this.insertFoodGroup = new SimpleJdbcInsert(ds).withTableName("foodGroups").usingGeneratedKeyColumns("id");
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
	
	
	 public Boolean update(FoodGroup fg){
		 
		 Boolean res = false;
		 
		 BeanPropertySqlParameterSource paramsBean = new BeanPropertySqlParameterSource(fg);
		 
		 String sql = "update foodGroups set name = :name, description= :description where id=:id";
		 
		 int numOfRowsAffected = myJdbcTemplate.update(sql, paramsBean);
		 
			if (numOfRowsAffected==1){
				System.out.println("One row has been updated in table foodGroups successfully");
				res=true;
			}else{
				System.out.println("There was a problem adding to table foodGroup!!");
			}
		 
		 
		 return res;
	 }
	
	
	 public Boolean delete(int id){
		 
		 Boolean res = false;
		 
		 MapSqlParameterSource params = new MapSqlParameterSource();
		 params.addValue("id", id);
		 
		 String sql = "delete from foodGroups where id = :id";
		 int numOfRowsAffected = myJdbcTemplate.update(sql, params);

			if (numOfRowsAffected==1){
				System.out.println("One row has been deleted from table foodGroups successfully");
				res=true;
			}else{
				System.out.println("There was a problem adding to table foodGroup!!");
			}
			
		 return res;
	 }
	 
	 @Transactional("myTransactionManager")
	 public int[]  createFoodGroup(List<FoodGroup> groups){
		
		 SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(groups.toArray());
		 
		String sql = "insert into foodgroups(name, description) values (:name, :description)"; 
		int[] numOfRowsAffectedArray = myJdbcTemplate.batchUpdate(sql, batchParams);
		
		 
		return numOfRowsAffectedArray;
		 
	 }

	public int create_si(FoodGroup fg){
		
		// Spring muches object properties with parameters:
		SqlParameterSource params = new BeanPropertySqlParameterSource(fg);
		
		// Spring insert these parameters into table and return the Id that is inserted:
		Number insertID = insertFoodGroup.executeAndReturnKey(params);
		
		return insertID.intValue();
		
	}
	 
	 
	 
}
