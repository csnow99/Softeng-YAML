package edu.wpi.cs.yaml.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.yaml.demo.db.AlternativeDAO;
import edu.wpi.cs.yaml.demo.db.ChoiceDAO;
import edu.wpi.cs.yaml.demo.db.DatabaseUtil;
import edu.wpi.cs.yaml.demo.model.Alternative;
import edu.wpi.cs.yaml.demo.model.Choice;

public class AlternativeDAOTest {
	
	@Test 
	public void testChoiceDAOBasics() {
		java.sql.Connection conn;
		
		try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}

		Choice choice1 = new Choice("001", "ChoiceDAOTest1",  5, "ChoiceDAOTest1Description", 1507019912630l, false, 0, 0);
	
		/*addChoice1*/
		ChoiceDAO choiceDao = new ChoiceDAO();
		try {
			choiceDao.addChoice(choice1);
		} catch (Exception e) {
			Assert.fail();
		}
		
		Alternative alt1 = new Alternative("001", "alt1_name", "alt1_description");
    	Alternative alt2 = new Alternative("001", "alt2_name", "alt2_description");
    	
    	/*test addAlternative and generateAlternative*/
    	AlternativeDAO altDao = new AlternativeDAO();
		try {
			altDao.addAlternative(alt1);
			altDao.addAlternative(alt2);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Alternatives WHERE choice_ID=001;");
			ResultSet rs = ps.executeQuery();
			/*Test generateChoice*/
			List<Alternative> result = new ArrayList<Alternative> ();
			while (rs.next()) {result.add(altDao.generateAlternative(rs));}

			Assert.assertEquals("001", result.get(0).getChoiceID());
			Assert.assertEquals("alt1_name", result.get(0).getTitle());
			Assert.assertEquals("alt1_description", result.get(0).getDescription());
			
			Assert.assertEquals("001", result.get(1).getChoiceID());
			Assert.assertEquals("alt2_name", result.get(1).getTitle());
			Assert.assertEquals("alt2_description", result.get(1).getDescription());
			
		} catch (Exception e) {
			Assert.fail();
		}
		
		/*Test getAlternatives getAltTitleFromAltID, getAltIDfromChoiceIDandAltTitle and getChoiceIDA*/
		try {
			List<Alternative> result = altDao.getAlternatives(choice1.getChoiceID());
			Assert.assertEquals("001", result.get(0).getChoiceID());
			Assert.assertEquals("alt1_name", result.get(0).getTitle());
			Assert.assertEquals("alt1_description", result.get(0).getDescription());
			
			Assert.assertEquals("001", result.get(1).getChoiceID());
			Assert.assertEquals("alt2_name", result.get(1).getTitle());
			Assert.assertEquals("alt2_description", result.get(1).getDescription());
			
			Assert.assertEquals("alt1_name", altDao.getAlternativeTitleFromAlternativeID(result.get(0).getAlternativeID()));
			Assert.assertEquals("alt2_name", altDao.getAlternativeTitleFromAlternativeID(result.get(1).getAlternativeID()));
			
			Assert.assertEquals(result.get(0).getAlternativeID(), altDao.getAlternativeIDFromChoiceIDandTitle("001", "alt1_name"));
			Assert.assertEquals(result.get(1).getAlternativeID(), altDao.getAlternativeIDFromChoiceIDandTitle("001", "alt2_name"));
			
			Assert.assertEquals("001", altDao.getChoiceIDA(result.get(0).getAlternativeID()));
			Assert.assertEquals("001", altDao.getChoiceIDA(result.get(1).getAlternativeID()));
		} catch (Exception e) {
			Assert.fail();
		}
		
		
		/*Remove choice*/
		try {
			choiceDao.deleteChoice(choice1.getChoiceID());
		} catch (Exception e) {
			Assert.fail();
		}
		
	}
	
}


