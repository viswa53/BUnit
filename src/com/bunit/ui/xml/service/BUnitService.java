package com.bunit.ui.xml.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bunit.ui.xml.to.Action;
import com.bunit.ui.xml.to.Source;
import com.bunit.ui.xml.util.BuintUtil;

public class BUnitService {

	public Map<String, List<Source>> getActions() throws Exception {

		String tomcatHome = System.getProperty("catalina.base");
		String path = tomcatHome + "\\BUNIT\\ACTION_LIBRARY";

		System.out.println("Getting actoins from path : " + path);

		File directory = new File(path);
		//get all the files from a directory
		File[] fList = directory.listFiles();
		BuintUtil buintUtil = new BuintUtil();

		Map<String, List<Source>> map =  new HashMap<String, List<Source>>();

		for(File file : fList) {

			Action action = buintUtil.convertXmlToObject(file);

			Source source = new Source();
			source.setId(action.DATA.ID);
			source.setDesc(action.DATA.DESCRIPTION);
			source.setScenarioId(action.DATA.SCENARIOID);

			if(map.containsKey(action.DATA.GROUP)) {
				List<Source> sources = map.get(action.DATA.GROUP);
				sources.add(source);
			} else {
				List<Source> newSource = new ArrayList<Source>();
				newSource.add(source);

				map.put(action.DATA.GROUP, newSource);
			}

		}
		return map;
	}

}
