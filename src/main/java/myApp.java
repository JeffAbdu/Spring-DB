
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.PermissionDeniedDataAccessException;

import com.demo.model.FoodGroup;
import com.demo.model.FoodGroupDAO;

public class myApp {

	public static void main(String[] args) {
		
		ApplicationContext appContext = new FileSystemXmlApplicationContext("appContext.xml");
		
		try {
		
			FoodGroupDAO myFoodGroupDAO = appContext.getBean("foodGroupDAO", FoodGroupDAO.class);
            
			myFoodGroupDAO.demoMethod();
		
		} catch (PermissionDeniedDataAccessException e) {
		
			// Handle as you see fit
		
		} catch (DataAccessException e) {
			
			System.out.println(e.getMessage());
			System.out.println(e.getClass());
		
		}
		((FileSystemXmlApplicationContext) appContext).close();
	}

}
