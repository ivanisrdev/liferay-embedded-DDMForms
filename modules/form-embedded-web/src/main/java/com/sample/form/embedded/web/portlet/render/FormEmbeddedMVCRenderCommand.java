package com.sample.form.embedded.web.portlet.render;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.sample.form.embedded.web.config.FormEmbeddedWebConfiguration;
import com.sample.form.embedded.web.constants.FormEmbeddedWebPortletKeys;
import org.osgi.service.component.annotations.*;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + FormEmbeddedWebPortletKeys.WIDGET_NAME,
                "mvc.command.name=" + FormEmbeddedWebPortletKeys.TEMPLATE_JSP
        },
        service = MVCRenderCommand.class
)

public class FormEmbeddedMVCRenderCommand implements MVCRenderCommand {

    private static final Log LOG = LogFactoryUtil.getLog(FormEmbeddedMVCRenderCommand.class);

    @Reference(policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
    private volatile DDMStructureLocalService _ddmStructureLocalService;

    @Reference(policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
    private volatile DDMFormInstanceLocalService _ddmFormInstanceLocalService;

    private volatile FormEmbeddedWebConfiguration _configuration;

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {

        LOG.info("Inside FormEmbeddedMVCRenderCommand");

        String formName = _configuration.formEmbeddedName();

        DynamicQuery dynamicQuery = _ddmStructureLocalService.dynamicQuery();
        dynamicQuery.add(PropertyFactoryUtil.forName("name").like("%<Name language-id=\"" + LocaleUtil.getDefault() + "\">" + formName + "</Name>%"));
        List<DDMStructure> ddmStructureList = _ddmStructureLocalService.dynamicQuery(dynamicQuery);

        if (!ddmStructureList.isEmpty()) {
            DDMStructure ddmStructure = ddmStructureList.get(0);
            dynamicQuery = _ddmFormInstanceLocalService.dynamicQuery();
            dynamicQuery.add(PropertyFactoryUtil.forName("structureId").eq(ddmStructure.getStructureId()));
            List<DDMFormInstance> formInstances = _ddmFormInstanceLocalService.dynamicQuery(dynamicQuery);
            DDMFormInstance ddmFormInstance = formInstances.get(0);

            renderRequest.setAttribute("formInstanceId", ddmFormInstance.getFormInstanceId());
        }

        return FormEmbeddedWebPortletKeys.VIEW_JSP;
    }

    @Activate
    @Modified
    protected void active(Map<String, Object> properties) {
        _configuration = ConfigurableUtil.createConfigurable(FormEmbeddedWebConfiguration.class, properties);
    }


}