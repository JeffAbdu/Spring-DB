
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.demo.model.FoodGroup;
import com.demo.model.FoodGroupDAO;

public class myApp {

	public static void main(String[] args) {
		
		ApplicationContext appContext = new FileSystemXmlApplicationContext("appContext.xml");
		
		FoodGroupDAO myFoodGroupDAO = appContext.getBean("foodGroupDAO", FoodGroupDAO.class);
				
		FoodGroup myFoodGroup = new FoodGroup(8,"aRandomGroupNameUpdated","aRandomGroupDescriptionUpdated");
		
		myFoodGroupDAO.update(myFoodGroup);
		
		((FileSystemXmlApplicationContext)appContext).close();
		
	}

}
