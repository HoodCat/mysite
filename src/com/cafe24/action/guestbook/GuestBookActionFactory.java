package com.cafe24.action.guestbook;

import com.cafe24.action.common.RedirectAction;
import com.cafe24.mvc.action.AbstractActionFactory;
import com.cafe24.mvc.action.Action;

public class GuestBookActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("list".equals(actionName)) {
			action = new ListAction();
		} else if("insert".equals(actionName)){
			action = new InsertAction();
		} else if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else {
			action = new RedirectAction();
		}
		return action;
	}

}
