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
		} else if("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("replyform".equals(actionName)) {
			action = new ReplyFormAction();
		} else if("reply".equals(actionName)) {
			action = new ReplyAction();
		} else if("commentInsert".equals(actionName)) {
			action = new CommentInsertAction();
		} else if("commentDelete".equals(actionName)) {
			action = new CommentDeleteAction();
		} else {
			action = new ListAction();
		}
		return action;
	}

}
