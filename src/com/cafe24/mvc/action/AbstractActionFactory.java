package com.cafe24.mvc.action;

public abstract class AbstractActionFactory {
	abstract public Action getAction(String actionName);
}
