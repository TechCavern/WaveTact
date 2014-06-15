package com.techcavern.wavetact.utils;

import java.util.Arrays;
import java.util.List;

public class PermRegistry {
	public boolean isController (String x){
		String[] Controllers = new String[]{"*!jztech101@techcavern.com","*!jztech101@crabhost.org"};
		List <String> Controllerlist = Arrays.asList(Controllers);
		boolean y = Controllerlist.contains(x);
		return y;
	}
}
