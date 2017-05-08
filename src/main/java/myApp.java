import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.demo.model.FoodGroup;
import com.demo.model.FoodGroupDAO;

public class myApp {

	public static void main(String[] args) {
		
		ApplicationContext appContext = new FileSystemXmlApplicationContext("appContext.xml");
		
		FoodGroupDAO myFoodGroupDAO = appContext.getBean("foodGroupDAO", FoodGroupDAO.class);
		
		List<FoodGroup> myFoodGroupList = myFoodGroupDAO.getFoodGroups();		
				

		for(FoodGroup fg: myFoodGroupList){
			System.out.println(fg.talkAboutYourself());
		}
		
		((FileSystemXmlApplicationContext)appContext).close();
		
	}

}
