package com.example.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

import com.example.uitl.PersonalProfile;

public class ProfileService {
	public static List<PersonalProfile> getPersonalProfile(InputStream is) throws Exception{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is,"gb2312");
		List<PersonalProfile> personalProfiles = null;
		PersonalProfile personalProfile = null;
		int type = parser.getEventType();
		while(type!=XmlPullParser.END_DOCUMENT){
			switch (type) {
			case XmlPullParser.START_TAG:
				if("employee".equals(parser.getName())){
					personalProfiles = new ArrayList<PersonalProfile>();
				}else if("evaluationPeople".equals(parser.getName())){
					personalProfile = new PersonalProfile();
				}else if("agentid".equals(parser.getName())){
					String agentid = parser.nextText();
					personalProfile.setAgentid(agentid);
				}else if("name".equals(parser.getName())){
					String name = parser.nextText();
					personalProfile.setName(name);
				}else if("department".equals(parser.getName())){
					String department = parser.nextText();
					personalProfile.setDepartment(department);
				}else if("rating".equals(parser.getName())){
					String rating = parser.nextText();
					personalProfile.setRating(rating);
				}else if("introduction".equals(parser.getName())){
					String introduction = parser.nextText();
					personalProfile.setIntroduction(introduction);
				}
				
				break;
			case XmlPullParser.END_TAG:
				if("evaluationPeople".equals(parser.getName())){
					personalProfiles.add(personalProfile);
					personalProfile=null;
				}

			default:
				break;
			}
			type = parser.next();
		}
		return personalProfiles;
		
	}

}
