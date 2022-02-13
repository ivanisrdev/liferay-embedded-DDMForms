package com.sample.form.embedded.web.config;

import aQute.bnd.annotation.ProviderType;
import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.sample.form.embedded.web.constants.FormEmbeddedWebPortletKeys;

@ProviderType
@ExtendedObjectClassDefinition(
        category = FormEmbeddedWebPortletKeys.CONFIGURATION_CATEGORY,
        scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
        id = FormEmbeddedWebPortletKeys.WIDGET_CONFIGURATION_NAME,
        localization = FormEmbeddedWebPortletKeys.CONFIGURATION_LOCALIZATION
)
public interface FormEmbeddedWebConfiguration {

    @Meta.AD(
            deflt = "Form custom",
            required = false
    )
    String formEmbeddedName();

}