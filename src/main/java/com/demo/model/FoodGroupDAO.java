package com.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	
}
