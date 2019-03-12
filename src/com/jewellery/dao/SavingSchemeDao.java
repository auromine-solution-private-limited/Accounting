package com.jewellery.dao;

import java.util.List;
import com.jewellery.entity.SavingScheme;;

public interface SavingSchemeDao {
		public List<SavingScheme> listSavingScheme();
		public SavingScheme getSavingScheme(Integer savingSchemeId);
		public List<SavingScheme> searchSavingScheme(String schemeName);
		public void insertSavingScheme(SavingScheme savingscheme);
		public void updateSavingScheme(SavingScheme savingscheme);
		
}
