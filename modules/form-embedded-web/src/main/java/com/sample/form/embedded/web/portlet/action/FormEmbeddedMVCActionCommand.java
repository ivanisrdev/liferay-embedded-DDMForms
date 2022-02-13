package com.sample.form.embedded.web.portlet.action;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.sample.form.embedded.web.constants.FormEmbeddedWebPortletKeys;
import com.sample.form.embedded.web.constants.MVCCommandNames;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Ivan Sánchez
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + FormEmbeddedWebPortletKeys.WIDGET_NAME,
                "mvc.command.name=" + MVCCommandNames.ACTION_FORM
        },
        service = MVCActionCommand.class
)
public class FormEmbeddedMVCActionCommand extends BaseMVCActionCommand {

    private static final Log LOG = LogFactoryUtil.getLog(FormEmbeddedMVCActionCommand.class);

    @Reference(policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
    private volatile DDMFormValuesFactory _ddmFormValuesFactory;

    @Reference(policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
    private volatile DDMFormInstanceRecordLocalService _ddmFormInstanceRecordLocalService;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        LOG.info("Inside FormEmbeddedMVCActionCommand");

        try {
            CaptchaUtil.check(actionRequest);
            LOG.info("CAPTCHA verification successful.");

            ServiceContext serviceContext = null;
            long formInstanceRecordId = ParamUtil.getLong(actionRequest, "formInstanceRecordId");

            DDMFormInstance ddmFormInstance =  DDMFormInstanceLocalServiceUtil.fetchDDMFormInstance(34254);
            DDMStructureVersion ddmStructureVersion = null;
            try {
                ddmStructureVersion = ddmFormInstance.getStructure().getLatestStructureVersion();
            } catch (PortalException e) {
                e.printStackTrace();
            }

            DDMForm ddmForm = ddmStructureVersion.getDDMForm();
            DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(actionRequest, ddmForm);

            try {
                serviceContext = ServiceContextFactory.getInstance(DDMFormInstanceRecord.class.getName(), actionRequest);
            } catch (PortalException e) {
                e.printStackTrace();
            }

            if(formInstanceRecordId > 0) {
                try {
                    _ddmFormInstanceRecordLocalService.updateFormInstanceRecord(serviceContext.getUserId(), formInstanceRecordId, Boolean.FALSE, ddmFormValues, serviceContext);
                } catch (PortalException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    _ddmFormInstanceRecordLocalService.addFormInstanceRecord(serviceContext.getUserId(), serviceContext.getScopeGroupId(), ddmFormInstance.getFormInstanceId(), ddmFormValues, serviceContext);
                } catch (PortalException e) {
                    e.printStackTrace();
                }
            }

        } catch (CaptchaException captchaException) {
            SessionErrors.add(actionRequest, "captcha-verification-failed");
            LOG.error("CAPTCHA verification failed.");
        }
    }
}

