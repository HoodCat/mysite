package com.cafe24.action.board;

import com.cafe24.mvc.action.AbstractActionFactory;
import com.cafe24.mvc.action.Action;

public class BoardActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if(actionName == null) {
			action = new ListAction();
			return action;
		}
		
		if("delete".equals(actionName)) {
			action = new DeleteAction(); 
		} else if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("view".equals(actionName)) {
			action = new ViewAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyFormAction();
		} else {
			action = new ListAction();
		}
		return action;
	}

}
