package com.sample.form.embedded.web.portlet.resource;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.sample.form.embedded.web.constants.FormEmbeddedWebPortletKeys;
import com.sample.form.embedded.web.constants.MVCCommandNames;
import org.osgi.service.component.annotations.Component;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ivan Sánchez
 */
@Component(
        property = {
                "javax.portlet.name=" + FormEmbeddedWebPortletKeys.WIDGET_NAME,
                "mvc.command.name=" + MVCCommandNames.RESOURCE_CAPTCHA
        },
        service = MVCResourceCommand.class
)
public class CaptchaMVCResourceCommand extends BaseMVCResourceCommand {

    private static final Log LOG = LogFactoryUtil.getLog(CaptchaMVCResourceCommand.class);

    @Override
    protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
        try {
            CaptchaUtil.serveImage(resourceRequest, resourceResponse);
        }
        catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
        }
    }
}