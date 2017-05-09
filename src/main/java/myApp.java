import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.demo.model.FoodGroup;
import com.demo.model.FoodGroupDAO;

public class myApp {

	public static void main(String[] args) {
		
		ApplicationContext appContext = new FileSystemXmlApplicationContext("appContext.xml");
		
		FoodGroupDAO myFoodGroupDAO = appContext.getBean("foodGroupDAO", FoodGroupDAO.class);
		
		FoodGroup meat = myFoodGroupDAO.getFoodGroup(4);		
		System.out.println(meat.talkAboutYourself());
		 
		FoodGroup dairy = myFoodGroupDAO.getFoodGroup(5);		
		System.out.println(dairy.talkAboutYourself());
		
		
		((FileSystemXmlApplicationContext)appContext).close();
		
	}

}
