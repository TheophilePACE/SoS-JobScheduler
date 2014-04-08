package org.jobscheduler.dashboard.web.jsonview;

public interface DataView {
	boolean hasView();

	Class<? extends BaseView> getView();

	Object getData();
}
