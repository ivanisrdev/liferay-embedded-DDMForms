package com.sample.form.embedded.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.sample.form.embedded.web.constants.FormEmbeddedWebPortletKeys;
import javax.portlet.Portlet;
import org.osgi.service.component.annotations.Component;

/**
 * @author Ivan Sánchez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=" + FormEmbeddedWebPortletKeys.WIDGET_DISPLAY_CATEGORY,
		"com.liferay.portlet.header-portlet-css=" + FormEmbeddedWebPortletKeys.WIDGET_CSS_FILE,
		"com.liferay.portlet.instanceable=" + FormEmbeddedWebPortletKeys.WIDGET_INSTANCEABLE,
		"javax.portlet.display-name=" + FormEmbeddedWebPortletKeys.WIDGET_DISPLAY_NAME,
		"javax.portlet.init-param.template-path=" + FormEmbeddedWebPortletKeys.TEMPLATE_JSP,
		"javax.portlet.init-param.view-template=" + FormEmbeddedWebPortletKeys.VIEW_JSP,
		"javax.portlet.name=" + FormEmbeddedWebPortletKeys.WIDGET_NAME,
		"javax.portlet.resource-bundle=" + FormEmbeddedWebPortletKeys.WIDGET_RESOURCE_LANGUAGE,
		"javax.portlet.security-role-ref=" + FormEmbeddedWebPortletKeys.WIDGET_SECURITY_ROLE
	},
	service = Portlet.class
)
public class FormEmbeddedWebPortlet extends MVCPortlet {
}