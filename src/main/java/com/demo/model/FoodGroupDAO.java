package com.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
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
	 
	 
	 public int[]  createFoodGroup(List<FoodGroup> groups){
		
//		ArrayList<MapSqlParameterSource> paramsArrayList = new ArrayList<MapSqlParameterSource>();
//		
//		for(FoodGroup g : groups){
//			
//			MapSqlParameterSource tempParam = new MapSqlParameterSource(); 
//			
//			tempParam.addValue("name", g.getName());
//			tempParam.addValue("description", g.getDescription());
//			
//			paramsArrayList.add(tempParam);
//		}
//		 
//		// batchParams must be an array not an arrayList to be accepted by batchUpdate()
//		// Therefore: we would create "batchParams" as an array and transfer in it the content of the arrayList "paramsArrayList":
//		 
//		SqlParameterSource[] batchParams = new MapSqlParameterSource[paramsArrayList.size()];
//		paramsArrayList.toArray(batchParams);
		 
		 
		 // The following line (provided by Spring) replace the whole previous commented code:
		 SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(groups.toArray());
		 
		String sql = "insert into foodgroups(name, description) values (:name, :description)"; 
		int[] numOfRowsAffectedArray = myJdbcTemplate.batchUpdate(sql, batchParams);
		
		 
		return numOfRowsAffectedArray;
		 
	 }
	 
}
