package com.cafe24.action.main;

import com.cafe24.mvc.action.AbstractActionFactory;
import com.cafe24.mvc.action.Action;

public class MainActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
		return new IndexAction();
	}

}
